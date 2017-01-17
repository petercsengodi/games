using System;
using System.Drawing;
using System.Runtime.Serialization;

using Microsoft.DirectX;

namespace Model
{
	using Engine;
	using GameLib;

	interface IGameElement : IClipping, IPeriod, IGameObject
	{
		void SetModel(Model model);
	}

	class StartPlace : IGameObject
	{
		protected Vector3 position, direction;

		public StartPlace(Vector3 position)
		{
			this.position = position;
			this.direction = new Vector3(0f, 0f, 1f);
		}

		public StartPlace(Vector3 position, Vector3 direction)
		{
			this.position = position;
			this.direction = direction;
		}

		#region IGameObject Members

		[Serializable]
		public class StartData : GameObjectData
		{
			public Vector3 position, direction;

			public StartData()
			{
				description = "Start Place";
			}

			public override object create()
			{
				return new StartPlace(position, direction);
			}


		}

		public GameObjectData getData()
		{
			StartData ret = new StartData();
			ret.position = position;
			ret.direction = direction;
			return ret;
		}

		public Vector3 Position
		{
			get { return position; }
		}

		public Vector3 Direction
		{
			get { return direction; }
		}

		public void Build(Engine engine)
		{
		}

		public void PreRender()
		{
		}

		public void PostRender()
		{
		}

		#endregion

		#region IRenderObject Members

		public void Render()
		{
		}

		#endregion

		#region IPeriod Members

		public void Period()
		{
		}

		#endregion

	}


	class FinishPlace : Clipper, IGameElement
	{
		protected float radius;

		protected Engine engine;
		protected Element element;
		protected float Angle;
		protected string mesh;

		protected float range;
		protected Light light;
		
		protected Model model;

		protected static float speed = 0.01f, limit = (float)(Math.PI * 2.0);

		public FinishPlace(Vector3 position, float radius) :
			base(position - new Vector3(radius, radius, radius), 
				position + new Vector3(radius, radius, radius))
		{
			this.position = position;
			this.radius = 0.1f;
			this.mesh = "end.x";
			this.Angle = 0f;
			this.range = 10f;
		}

		/// <summary>
		/// Serializable data class for symbol.
		/// </summary>
		[Serializable]
		protected class FinishPlaceData : GameObjectData
		{
			public float angle;
			public Vector3 position;

			public FinishPlaceData()
			{
				description = "Finish Place";
			}

			public override object create()
			{
				return new FinishPlace(this);
			}

		}

		public FinishPlace(GameObjectData data) : 
			base(new Vector3(0f,0f,0f), new Vector3(0f,0f,0f))
		{
			FinishPlaceData d = data as FinishPlaceData;
			this.Angle = d.angle;
			this.position = d.position;
			this.radius = 0.1f;
			this.mesh = "end.x";
			this.Angle = 0f;
			this.range = 10f;
		}

		#region IGameObject Members

		public GameLib.GameObjectData getData()
		{
			FinishPlaceData ret = new FinishPlaceData();
			ret.angle = this.Angle;
			ret.position = this.position;
			return ret;
		}

		public void Build(Engine engine)
		{
			this.engine = engine;
			element = engine.GetMeshElement(mesh, 
				EngineMeshFlags.None, Color.Blue);
			radius = (element as MeshElement).Radius();
			this.corner1 = - new Vector3(radius, radius, radius);
			this.corner2 = - this.corner1;
			light = engine.GetPointLight(range, Color.Blue, position);
		}

		public void PreRender()
		{
			light.Activate();
		}

		public void PostRender()
		{
			light.DeActivate();
		}

		#endregion

		#region IRenderObject Members

		public void Render()
		{
			element.Render(position, 0f, Angle, 0f);
		}

		#endregion

		#region IPeriod Members

		public void Period()
		{
			Angle += 0.005f;
			if(Angle > limit) Angle -= limit;
		}

		#endregion

		public override void PlayerEffect(object player)
		{
			model.FinishGame(new EndOfGame(true));
		}

		public override void Clip(Clipable clipable)
		{
			base.Clip (clipable);
		}


		public void SetModel(Model model)
		{
			this.model = model;
		}

	}

	public struct EndOfGame
	{
		public bool finished;
		public string next_map;

		public EndOfGame(bool finished)
		{
			this.finished = finished;
			this.next_map = null;
		}

		public EndOfGame(bool finished, string next_map)
		{
			this.finished = finished;
			this.next_map = next_map;
		}

	}

//	class StartPlace : Symbol
//	{
//		public StartPlace(Vector3 position) : base(position, 
//			"start.x", Color.LightBlue, 7f)
//		{
//		}
//
//		// Data Class
//		protected class StartPlaceData : GameObjectData
//		{
//			public GameObjectData symbol_data;
//
//			public StartPlaceData()
//			{
//				description = "Start Place Data";
//			}
//
//			public override object create()
//			{
//				return new StartPlace(this);
//			}
//		} // End of Data Class
//
//		public StartPlace(GameObjectData data) : 
//			base( (data as StartPlaceData).symbol_data )
//		{
//		}
//
//		public override GameObjectData getData()
//		{
//			StartPlaceData ret = new StartPlaceData();
//			ret.symbol_data = base.getData();
//			return ret;
//		}
//
//
//		public override void Build(Engine engine)
//		{
//			base.Build(engine);
//		}
//	} // End of class Symbol
	
	class Bullet : DynamicObject, IGameElement
	{
		protected Model model;
		protected float radius;
		protected MeshElement bullet;
		protected static float speed = 0.1f, limit = (float)(Math.PI * 2.0);
		protected static Vector3 vup = new Vector3(0f, 1f, 0f);

		public Bullet(Vector3 position, Vector3 velocity, Model model) :
			base(position, position, position)
		{
			this.radius = 0f;
			this.model = model;
			this.position = position;
			this.velocity = velocity;
		}

		[Serializable]
		public class BulletData : GameObjectData
		{
			public Vector3 position;
			public Vector3 velocity;
			
			public BulletData()
			{
				description = "Bullet Data";
			}

			public override object create()
			{
				return new Bullet(this);
			}
		}

		public Bullet(BulletData data) : 
			base(new Vector3(0f, 0f, 0f), 
			new Vector3(0f, 0f, 0f), new Vector3(0f, 0f, 0f))
		{
			this.position = data.position;
			this.velocity = velocity;
			this.radius = 0f;
		}

		#region IGameElement Members

		public void SetModel(Model model)
		{
			this.model = model;
		}

		#endregion

		#region IPeriod Members

		public override void Period()
		{
			float deltat = 0.04f;
			velocity.Y -= /*10f*/5f * deltat / 2f;
			base.Period();
		} // End of function Period

		#endregion

		#region IGameObject Members

		public override GameObjectData getData()
		{
			BulletData data = new BulletData();
			data.position = position;
			data.velocity = velocity;
			return data;
		}

		public override void Build(Engine engine)
		{
			bullet = (MeshElement)engine.GetMeshElement("bullet.x",
				EngineMeshFlags.Colored | EngineMeshFlags.NoShadow,
				Color.Yellow);
			radius = bullet.Radius();
			corner1 = new Vector3(-radius, -radius, -radius);
			corner2 = new Vector3(radius, radius, radius);
		}

		#endregion

		public override void Render()
		{
			bullet.Render(position, velocity);
		} // End of function Render

		public override void Squash(StaticVectorLibrary.Direction dir, Vector3 box1, Vector3 box2, Vector3 sqpoint)
		{
			model.GEToRemove.Add(this);
		}

		public override void PlayerEffect(object player)
		{
		}

	}

	public enum WeaponStatus
	{
		Ground, Thrown, Grabbed
	}

	public enum WeaponType
	{
		Sword, Torch, Gun
	}

	public class Weapon : Clipable, IGameElement
	{
		protected WeaponStatus status;
		protected WeaponType type;
		protected float Angle;
		protected float anim; // from 0f to 1f
		protected Model model;
		protected Engine engine;

		// Clipper properties
		protected float radius;

		protected MeshElement weapon;
		protected static float speed = 0.1f, limit = (float)(Math.PI * 2.0);
		protected static Vector3 vup = new Vector3(0f, 1f, 0f);

		public WeaponStatus Status 
		{
			get{ return status; }
			set{ status = value; }
		}

		public Weapon(Vector3 position, WeaponType type) :
			base(position, position, position)
		{
			this.status = WeaponStatus.Ground;
			this.type = type;
			this.Angle = 0f;
			this.anim = 1f;
			this.radius = 0f;
		}

		[Serializable]
			public class WeaponData : GameObjectData
		{
			public Vector3 position;
			public WeaponStatus status;
			public WeaponType type;
			public float Angle;
			public float anim;
			
			public WeaponData()
			{
				description = "Weapon Data";
			}

			public override object create()
			{
				return new Weapon(this);
			}
		}

		public Weapon(WeaponData data) : 
			base(new Vector3(0f, 0f, 0f), 
			new Vector3(0f, 0f, 0f), new Vector3(0f, 0f, 0f))
		{
			this.position = data.position;
			this.status = data.status;
			this.type = data.type;
			this.Angle = data.Angle;
			this.anim = data.anim;
			this.radius = 0f;
		}

		#region IPeriod Members

		public void Period()
		{
			switch(status)
			{
				case WeaponStatus.Ground:
					Angle += 0.005f;
					if(Angle > limit) Angle -= limit;
					break;

				default:
					if(anim < 1f) 
					{
						if(anim > -1f) anim -= speed;
						else anim = 1f;
					}
					break;
			}
		} // End of function Period

		#endregion

		#region IGameObject Members

		public GameObjectData getData()
		{
			WeaponData data = new WeaponData();
			data.position = position;
			data.status = status;
			data.type = type;
			data.Angle = Angle;
			data.anim = anim;
			return data;
		}

		public void Build(Engine engine)
		{
			this.engine = engine;
			switch(type)
			{
				case WeaponType.Gun:
					weapon = (MeshElement)engine.GetMeshElement(
						"gun.x", EngineMeshFlags.Colored, Color.White);
					break;

				case WeaponType.Torch:
					weapon = (MeshElement)engine.GetMeshElement(
						"torch.x", EngineMeshFlags.Colored, Color.White);
					break;

				default:
					weapon = (MeshElement)engine.GetMeshElement(
						"sword.x", EngineMeshFlags.Colored, Color.White);
					break;
			}

			radius = weapon.Radius();
			corner1 = new Vector3(-radius, -radius, -radius);
			corner2 = new Vector3(radius, radius, radius);
		}

		public void PreRender()
		{
//			if(status != WeaponStatus.Grabbed)
//			{
//				weapon.Shadow = true;
//			} 
//			else weapon.Shadow = false;
		}

		public void PostRender()
		{
		}

		#endregion

		#region IRenderObject Members

		public void Render(Vector3 position, Vector3 orientation)
		{
			if(status == WeaponStatus.Grabbed)
			{
				Vector3[] res = GetGrabVectors();
				Vector3 po = Vector3.Lerp(
					res[1], res[0], Math.Abs(anim));
				Vector3 or = Vector3.Lerp(
					res[3], res[2], Math.Abs(anim));
				Vector3 vp = Vector3.Lerp(
					res[5], res[4], Math.Abs(anim));
				Matrix w1 = Matrix.LookAtLH(position, 
					orientation + position, vup);
				w1.Invert();
				Matrix w2 = Matrix.LookAtLH(po, or + po, vp);
				w2.Invert();
				weapon.Render(Matrix.Multiply(w2, w1));
			}
		}
		
		public void Render()
		{
			if(status != WeaponStatus.Grabbed)
			{
				weapon.Render(position, 0f, Angle, 0f);
			}
		} // End of function Render

		#endregion

		public override void Squash(StaticVectorLibrary.Direction dir, Vector3 box1, Vector3 box2, Vector3 sqpoint)
		{
			#region Falls Back with Energy Loss
			
			if((dir == StaticVectorLibrary.Left) || (dir == StaticVectorLibrary.Right))
			{ 
				diff.X = 2 * (sqpoint.X - position.X) - diff.X; 
				velocity.X = -0.4f * velocity.X; 
				if(Math.Abs(velocity.X) < 0.01f) velocity.X = 0.00f;
			}
			
			if((dir == StaticVectorLibrary.Top) || (dir == StaticVectorLibrary.Bottom))
			{
				diff.Y = 2 * (sqpoint.Y - position.Y) - diff.Y; 
				velocity.Y = -0.6f * velocity.Y;
				velocity.X = 0.6f * velocity.X;
				velocity.Z = 0.6f * velocity.Z;
				if(Math.Abs(velocity.Y) < 0.01f) velocity.Y = 0.00f;
			}

			if((dir == StaticVectorLibrary.Front) || (dir == StaticVectorLibrary.Back))
			{ 
				diff.Z = 2 * (sqpoint.Z - position.Z) - diff.Z; 
				velocity.Z = -0.4f * velocity.Z; 
				if(Math.Abs(velocity.Z) < 0.01f) velocity.Z = 0.00f;
			}

			#endregion
		}

		public override void PlayerEffect(object player)
		{
			PlayerObject p = player as PlayerObject;
			if(status == WeaponStatus.Ground)
			{
				status = WeaponStatus.Grabbed;
				p.Model.GEToRemove.Add(this);
				p._AddWeapon(this);

			}
		}

		protected Vector3[] GetGrabVectors()
		{
			Vector3[] ret = new Vector3[6];
			
			switch(type)
			{
				case WeaponType.Gun:
					ret[0] = new Vector3(0.15f, -0.15f, 0.2f);
					ret[1] = new Vector3(0.15f, -0.15f, 0.1f);
					ret[2] = new Vector3(0f, 0f, -1f);
					ret[3] = new Vector3(0f, 0f, -1f);
					ret[4] = new Vector3(0f, 1f, 0f);
					ret[5] = new Vector3(0f, 1f, 0f);
					break;

				case WeaponType.Torch:
					ret[0] = new Vector3(0.15f, -0.15f, 0.2f);
					ret[1] = new Vector3(0.15f, -0.15f, 0.1f);
					ret[2] = new Vector3(0f, 0f, -1f);
					ret[3] = new Vector3(0f, 0f, -1f);
					ret[4] = new Vector3(0f, 1f, 0f);
					ret[5] = new Vector3(0f, 1f, 0f);
					break;

				default:
					ret[0] = new Vector3(0.2f, -0.5f, 0f);
					ret[1] = new Vector3(0.2f, -0.25f, 0.5f);
					ret[2] = new Vector3(0f, 1.5f, 1f);
					ret[3] = new Vector3(-2f, 0f, 2f);
					ret[4] = new Vector3(0f, 1f, 0f);
					ret[5] = new Vector3(1f, 1f, 0f);
					break;
			}


			return ret;
		}

		public void Shot(Vector3 position, Vector3 direction)
		{
			if((status == WeaponStatus.Grabbed) && (anim == 1f))
			{
				anim -= speed;

				switch(type)
				{
					case WeaponType.Gun:
						Bullet bullet = new Bullet(
							position + direction,
							Vector3.Normalize(direction) * 15f,
                            model);
						bullet.Build(engine);
						model.GameElements.Add(bullet);
						break;

					default:

						break;
				}
			}
		}

		public void SetModel(Model model)
		{
			this.model = model;
		}

	} // End of class Weapon
}
