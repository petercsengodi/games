using System;
using System.Xml;
using System.Drawing;
using System.Collections;
using System.Windows.Forms;

using Microsoft.DirectX;

namespace Model
{
	using Engine;
	using GameLib;
	using Mathematics;
	using Superstition;

	/// <summary>
	/// Class for Player. Represents a single player with inner view,
	/// also shows other net-players with a single mesh.
	/// </summary>
	class PlayerObject : DynamicObject, IDisposable
	{
		#region Player Constants and Variables

		// For Behaviour
		public bool OnGround = false, 
			OnGroundNextState = false, 
			OnJump = false,
			ShootRight = false, ShootLeft = false;
		
		// For Controlling
		public bool GoForward = false, 
			GoBack = false, 
			TurnLeft = false, 
			TurnRight = false,
			TurnUp = false, 
			TurnDown = false, 
			GoLeft = false, 
			GoRight = false;
		
		// For View
		protected float Angle = 0.0f, 
			ViewAngle = 0.0f;
		
		// For Controlling View
		public static float turnAngle = 0.1f, 
			step = 0.2f, 
			pi2 = (float) (Math.PI * 2.0),
			ViewAngleLimit = (float)(Math.PI / 2.0 - 0.8);
		
		// Animation
		protected Animation actual, player_stand, 
			player_run, player_slide, player_jump;
		protected Animation[] player_fight;
		protected int rotation, scene;
		protected int actual_fight;
		protected const int slow = 3;
		public NamedConnectionResult camera_pos,
			camera_dir, camera_up;
		protected Matrix common;
		protected Vector3 direction;

		#endregion

		#region Shoot Control

		protected DynamicObject TorchControl;
		protected bool isOwned = true;
		protected Weapon[] weapons;
		protected int actual_weapon;

		#endregion

		#region Network Control

		protected Network.PlayClient play_client; // network connection
		protected AllPlayerData all_data; // net-player datas
		protected bool network_play; // enables/disables net-play
		protected int userID; // user id on server
		protected int rate; // making network sending slower than physical happening
		protected int NETRATE; // rate of sending message

		#endregion

		#region Model Control
	
		protected Model model;
		public Model Model
		{
			get{ return model; }
			set
			{ 
				model = value; 
				if(weapons != null)
					for(int i = 0; i < weapons.Length; i++)
					{
						if(weapons[i] != null)
							weapons[i].SetModel(model);
					}
			}
		}

		#endregion

		// Spider test
//		protected Spider spider;

		/// <summary>
		/// Serializable data class for player.
		/// </summary>
		[Serializable]
			protected class PlayerData : GameObjectData
		{
			public Vector3 position, corner1, corner2, velocity, diff;
			public bool torch_owned, alive;
			public GameObjectData torch;
			public GameObjectData[] weapons;
			public float angle, view_angle;

			public PlayerData()
			{
				description = "Player";
			}

			public override object create()
			{
				return new PlayerObject(this);
			}
		}

		public PlayerObject(Vector3 _position, Vector3 _corner1, Vector3 _corner2) 
			: base(_position, _corner1, _corner2)
		{
			TorchControl = new ThrowableTorch(position, new Vector3(0f, 0f, 0f));
			NETRATE = NetworkOptions.UdpRate();
			rate = 0;
			network_play = false;
			initialize_weapons();
		}
		
		public PlayerObject(GameObjectData data)
			: base(new Vector3(), new Vector3(), new Vector3())
		{
			PlayerData d = data as PlayerData;
			this.alive = d.alive;
			this.Angle = d.angle;
			this.corner1 = d.corner1;
			this.corner2 = d.corner2;
			this.diff = d.diff;
			this.isOwned = d.torch_owned;
			this.position = d.position;
			this.velocity = d.velocity;
			this.ViewAngle = d.view_angle;
			this.TorchControl = new ThrowableTorch(d.torch);

			NETRATE = NetworkOptions.UdpRate();
			rate = 0;
			network_play = false;
			initialize_weapons();

			this.weapons = new Weapon[d.weapons.Length];
			for(int i = 0; i < d.weapons.Length; i++)
			{
				if(d.weapons[i] != null)
					this.weapons[i] = new Weapon(
						(Weapon.WeaponData)d.weapons[i]);
			}
		}

		protected void initialize_weapons()
		{
			weapons = new Weapon[3];
			weapons[0] = new Weapon(new Vector3(0f, 0f, 0f), WeaponType.Gun);
			weapons[0].Status = WeaponStatus.Grabbed;
			weapons[1] = new Weapon(new Vector3(0f, 0f, 0f), WeaponType.Sword);
			weapons[1].Status = WeaponStatus.Grabbed;
//			weapons[2] = new Weapon(new Vector3(0f, 0f, 0f), WeaponType.Gun);
//			weapons[2].Status = WeaponStatus.Grabbed;
			actual_weapon = 1;

			direction = new Vector3(
				-(float)Math.Sin(Angle), 0f, 
				-(float)Math.Cos(Angle));
			common = Matrix.LookAtLH(
				position, position + direction, 
				new Vector3(0f, 1f, 0f));
			common.Invert();
			scene = rotation / slow;

			camera_pos = null;
			camera_dir = null;
			camera_up = null;
		}

		public override GameObjectData getData()
		{
			PlayerData ret = new PlayerData();
			ret.alive = this.alive;
			ret.angle = this.Angle;
			ret.corner1 = this.corner1;
			ret.corner2 = this.corner2;
			ret.diff = this.diff;
			ret.position = this.position;
			ret.torch = this.TorchControl.getData();
			ret.torch_owned = this.isOwned;
			ret.velocity = this.velocity;
			ret.view_angle = this.ViewAngle;

			ret.weapons = new GameObjectData[this.weapons.Length];
			for(int i = 0; i < this.weapons.Length; i++)
			{
				if(this.weapons[i] != null)
					ret.weapons[i] = this.weapons[i].getData();
			}

			return ret;
		}

		public override void Build(Engine engine)
		{
			this.engine = engine;

			player_stand = Library.Animations().getAnimation("human_swordstand1");
			player_run = Library.Animations().getAnimation("human_swordrun1");
			player_slide = Library.Animations().getAnimation("human_swordslide1");
			player_jump = Library.Animations().getAnimation("human_swordjump1");

			player_fight = new Animation[3];
			player_fight[0] = Library.Animations().getAnimation("human_fight2");
			player_fight[1] = Library.Animations().getAnimation("human_fight1");
			player_fight[2] = Library.Animations().getAnimation("human_swordfight1");
			actual_fight = 1;

			actual = player_stand;

			TorchControl.Build(engine);

			foreach(Weapon weapon in weapons)
			{
				if(weapon != null) weapon.Build(engine);
			}

			// Spider test 
//			spider = new Spider();
//			spider.CurrentRoom = this.CurrentRoom;
//			spider.position = this.position;
//			spider.Build(engine);
		}
	
		public override void Squash(
			StaticVectorLibrary.Direction dir, 
			Vector3 box1, Vector3 box2, Vector3 sqpoint)
		{
			#region Clipping behaviour

			if(dir == StaticVectorLibrary.Left)
			{
				if(sqpoint.Y > box2.Y - STEP)
				{ diff.Y = box2.Y - position.Y; velocity.Y = 0f; } 
				else { diff.X = sqpoint.X - position.X; velocity.X = 0f; }
			}
			
			if(dir == StaticVectorLibrary.Right)
			{
				if(sqpoint.Y > box2.Y - STEP)
				{ diff.Y = box2.Y - position.Y; velocity.Y = 0f; } 
				else { diff.X = sqpoint.X - position.X; velocity.X = 0f; }
			}
			
			if(dir == StaticVectorLibrary.Top)
			{ diff.Y = sqpoint.Y - position.Y; velocity.Y = 0f;}

			if(dir == StaticVectorLibrary.Bottom)
			{ diff.Y = sqpoint.Y - position.Y; velocity.Y = 0f; OnGroundNextState = true;}

			if(dir == StaticVectorLibrary.Front)
			{ 
				if(sqpoint.Y > box2.Y - STEP) 
				{ diff.Y = box2.Y - position.Y; velocity.Y = 0f; } 
				else { diff.Z = sqpoint.Z - position.Z; velocity.Z = 0f; }
			}

			if(dir == StaticVectorLibrary.Back)
			{
				if(sqpoint.Y > box2.Y - STEP)
				{ diff.Y = box2.Y - position.Y; velocity.Y = 0f; } 
				else { diff.Z = sqpoint.Z - position.Z; velocity.Z = 0f; }
			}

			#endregion
		}

		public override void Squash(Clipper clipper, 
			StaticVectorLibrary.Direction dir, 
			Vector3 box1, Vector3 box2, Vector3 sqpoint)
		{
			Squash(dir, box1, box2, sqpoint);
			clipper.PlayerEffect(this);
		}


		public override void Render()
		{
			engine.LightPosition = TorchControl.position;

			TorchControl.PreRender();
			TorchControl.Render();
			
			// spider test
//			spider.Render();

			// TODO : Shots and player must be rendered in the room they are actually
			if(CurrentRoom != null) CurrentRoom.RenderRoomsInSight();

			// Rendering the player.
//			direction = new Vector3(
//				-(float)Math.Sin(Angle), 0f, 
//				-(float)Math.Cos(Angle));
//			common = Matrix.LookAtLH(
//				position, position + direction, 
//				new Vector3(0f, 1f, 0f));
//			common.Invert();
//			
//			actual.Draw(common, rotation / slow);

			actual.Draw(common, scene);

			// Rendering Model's Game Elements
			foreach(IRenderObject r in model.GameElements)
			{
				r.Render();
			}


			// Rendering Weapons
//			weapons[actual_weapon].Render(position, getDirection());

			if(network_play && (all_data != null))
			{
				for(int i = 0; i < all_data.all_data.Length; i++)
				{
					if(i == userID) continue;
					if(all_data.all_data[i] == null) continue;
//					Matrix m = Matrix.LookAtLH(all_data.all_data[i].position,
//						all_data.all_data[i].position + all_data.all_data[i].difference,
//						new Vector3(0f, 1f, 0f));
//					m.Invert();
					Matrix m = Matrix.Translation(all_data.all_data[i].position);
					player_stand.Draw(m, 0);
				}
			}

			// Rendering Torch
			TorchControl.PostRender();
		}

		#region Control Functions

		/// <summary>
		/// Player turn left.
		/// </summary>
		public void _TurnLeft()
		{ 
			Angle -= turnAngle; 
			if(Angle < 0f) Angle += pi2; 
		}

		/// <summary>
		/// Player turn right.
		/// </summary>
		public void _TurnRight()
		{
			Angle += turnAngle; 
			if(Angle > pi2) Angle -= pi2;
		}

		/// <summary>
		/// Player turn up.
		/// </summary>
		public void _TurnUp()
		{
			ViewAngle += turnAngle; 
			if(ViewAngle > ViewAngleLimit) ViewAngle = ViewAngleLimit;
		}

		/// <summary>
		/// Player turn down.
		/// </summary>
		public void _TurnDown()
		{
			ViewAngle -= turnAngle; 
			if(ViewAngle < -ViewAngleLimit) ViewAngle = -ViewAngleLimit;
		}

		/// <summary>
		/// Gets players orientation.
		/// </summary>
		/// <returns>Orientation.</returns>
		public Vector3 getDirection()
		{
			if(camera_dir == null)
			{
				return new Vector3((float) (Math.Sin(Angle) * Math.Cos(ViewAngle)),
					(float) Math.Sin(ViewAngle), (float) (Math.Cos(Angle) * Math.Cos(ViewAngle)));
			} 
			else 
			{
				Vector3 ret = new Vector3(0f, 0f, 0f);
				Vector3 cdir = camera_dir.position - camera_pos.position;
				Vector3 cup = camera_up.position - camera_pos.position;
				ret.X = (float)(cdir.X * Math.Cos(ViewAngle) + cup.X * Math.Sin(ViewAngle));
				ret.Y = (float)(cdir.Y * Math.Cos(ViewAngle) + cup.Y * Math.Sin(ViewAngle));
				ret.Z = (float)(cdir.Z * Math.Cos(ViewAngle) + cup.Z * Math.Sin(ViewAngle));
				return ret;
			}
		}

		/// <summary>
		/// Player shoot.
		/// </summary>
		public void _OnceShot(bool left)
		{
            if(left && (ShootLeft == false))
			{
				ShootLeft = true;
				rotation = 0;
				actual_fight = 2;
			}

			// weapon
			if(left)
			{
				weapons[actual_weapon].Shot(
					position, getDirection());
				return;
			}

			// torch
			if(isOwned) 
			{
				Vector3 shotsp = 70f * getDirection();
				shotsp.Y += 3f;
				TorchControl.velocity = shotsp;
				((ThrowableTorch)TorchControl).Stand = false;
				isOwned = false;
			} 
			else isOwned = true;
		}

		/// <summary>
		/// Player weapon switch operation.
		/// </summary>
		/// <param name="weapon">Number of selected weapon.</param>
		public void _SwitchWeapon(int weapon)
		{
			if(weapon >= weapons.Length) return;
			if(weapons[weapon] != null)
			{
				actual_weapon = weapon;
			}
		}

		/// <summary>
		/// Player torch operation.
		/// </summary>
		public void _Stand()
		{
			if(isOwned) 
			{
				((ThrowableTorch)TorchControl).Stand = true;
				TorchControl.velocity = new Vector3(0f, 0f, 0f);
				isOwned = false;
			} 
			else if(ShootLeft == false)
			{
				ShootLeft = true;
				rotation = 0;
				actual_fight = (actual_fight + 1) % 2;
			}

		}

		public void _AddWeapon(Weapon weapon)
		{
			weapons[2] = weapon;
		}

		#endregion

		public override void Period()
		{
			// spider test
//			if(spider.CurrentRoom == null)
//				spider.CurrentRoom = this.CurrentRoom;
//			spider.Period();
//			spider.CurrentRoom.FollowPlayer(spider);

			float deltat = 0.04f; 

			#region Pre-Functionalities

			OnGround = OnGroundNextState;
			OnGroundNextState = false;
			if(OnJump) velocity.Y += 5f;
			velocity.Y += -10f * deltat;
			OnJump = false;

			#endregion

			#region Shot Lyfe Cycle

			foreach(Weapon weapon in weapons)
			{
				if(weapon != null) weapon.Period();
			}

			#endregion

			base.Period();

			#region Torch Life Cycle

			TorchControl.Period();
			if(isOwned)
			{
				TorchControl.position = Vector3.Add(position, new Vector3(
					(float)(Math.Sin(Angle - Math.PI / 4.0) * 0.3), 0f, 
					(float)(Math.Cos(Angle - Math.PI / 4.0) * 0.3)));
			}

			#endregion

			#region Post-Functionalities

			if(GoForward) diff = Vector3.Add(diff, new Vector3(
							  (float) (Math.Sin(Angle) * step),
							  0f, (float) (Math.Cos(Angle) * step)));
			if(GoBack) diff = Vector3.Add(diff, new Vector3(
						   (float) (-Math.Sin(Angle) * step),
						   0f, (float) (-Math.Cos(Angle) * step)));
			if(GoLeft) diff = Vector3.Add(diff, new Vector3(
						   (float) (-Math.Cos(Angle) * step),
						   0f, (float) (Math.Sin(Angle) * step)));
			if(GoRight) diff = Vector3.Add(diff, new Vector3(
							(float) (Math.Cos(Angle) * step),
							0f, (float) (-Math.Sin(Angle) * step)));

			if(TurnLeft) _TurnLeft();
			if(TurnRight) _TurnRight();
			if(TurnUp) _TurnUp();
			if(TurnDown) _TurnDown();

			#endregion

			#region Animation
		
			if(ShootRight) rotation++;
			else if(!OnGround) 
			{
				if(rotation < player_jump.MaxScenes * slow - 1)
					rotation += 2;
			}
			else if(GoBack) rotation--;
			else rotation++;

			if(ShootLeft)
			{
				actual = player_fight[actual_fight];
				if(rotation >= actual.MaxScenes * slow)
					ShootLeft = false;
			} 
			else 
			{
				if(!OnGround) actual = player_jump;
				else if(GoForward || GoBack) actual = player_run;
				else if(GoLeft || GoRight) actual = player_slide;
				else actual = player_stand;
			}

			if(rotation < 0) rotation += actual.MaxScenes * slow;
			rotation = rotation % (actual.MaxScenes * slow);

			// Render matrices, again
			direction = new Vector3(
				-(float)Math.Sin(Angle), 0f, 
				-(float)Math.Cos(Angle));
			common = Matrix.LookAtLH(
				position, position + direction, 
				new Vector3(0f, 1f, 0f));
			common.Invert();
			scene = rotation / slow;

			camera_pos = actual.GetNamedConnection(
				"camera_pos", common, scene);
			camera_dir = actual.GetNamedConnection(
				"camera_dir", common, scene);
			camera_up = actual.GetNamedConnection(
				"camera_up", common, scene);


			#endregion

			#region Network Things

			if(network_play)
			{ // if net-play is enabled
				if(rate < NETRATE) rate++; // check rate
				else 
				{
					rate = 0;
					play_client.SendNetworkPlayer(position, diff);

					// use network data
					// diff = old_diff; // TODO: Should be here or not?
					if((all_data != null) && (position == all_data.all_data[userID].position))
					{ // if even exists data
						position = all_data.all_data[userID].position;
						diff = all_data.all_data[userID].difference;
					}
				}
			}

			#endregion
		}
			
		/// <summary>
		/// Iterating function for Rooms to clip this player.
		/// </summary>
		/// <param name="theme"></param>
		public void ClipRooms(object theme)
		{
			IClipping t = ((IClipping) theme);
			t.Clip(this);
			t.Clip(TorchControl);
		}

		/// <summary>
		/// Starts network play mode.
		/// </summary>
		/// <param name="play_client">The udp network connection to server.</param>
		/// <param name="userID">User ID on server.</param>
		public void StartNetworkPlay(Network.PlayClient play_client, int userID)
		{
			if(this.play_client != null)
			{ // if already int net-play, then disconnect previous connection first.
				StopNetworkPlay();
			}

			this.userID = userID;
			this.play_client = play_client;
			this.play_client.UdpConnect(); // make network connection
			this.play_client.StartListenThread( // give processing function
				new Network.ReceiveData(ProcessData));
			this.network_play = true; // enable net-play
		}

		/// <summary>
		/// Stops network play mode. Disconnects from server.
		/// </summary>
		public void StopNetworkPlay()
		{
			play_client.UdpDisconnect(); // disconnect
			network_play = false; // disable net-play
			play_client = null;
		}

		/// <summary>
		/// Handle function for incoming udp-game packages.
		/// </summary>
		/// <param name="data">Incoming data object.</param>
		public void ProcessData(GameObjectData data)
		{
			if(data == null) return; // no usable data

			if(data.Description.Equals("Quit Game"))
			{ // server disconnects
				network_play = false; // disable net-play
				Console.WriteLine("Disconnect Message.");
				return;
			}

			if(data.Description.Equals("AllNetPlayer"))
			{ // player datas reeived
				all_data = (AllPlayerData)data;
//				position = all_data.all_data[userID].position;
//				diff = all_data.all_data[userID].difference;
			}
		}

		#region IDisposable Members

		/// <summary>
		/// Dispose functionality for Player Objects.
		/// Does only network closing, yet.
		/// </summary>
		public void Dispose()
		{
			if(play_client != null) StopNetworkPlay();
		}

		#endregion

	} // End of class Player Object

}