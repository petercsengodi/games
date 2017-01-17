using System;
using System.Xml;
using System.Drawing;
using System.Collections;
using Superstition;
using Mathematics;
using Microsoft.DirectX;
using System.Windows.Forms;
using System.Runtime.Serialization;

namespace Model
{
	using Engine;
	using GameLib;

	enum RoomPostType{ None = 0, CenterPost = 1, Four_Post = 2, Wall_Groups = 4}

	/// <summary>
	/// Class for Rooms on the map
	/// </summary>
	class Room : TWLNode, IClipping, IDisposable, IGameObject, IRenderObject
	{
		#region Direction Constants

		public const int WALL_LEFT = 1, 
			WALL_RIGHT = 2, 
			WALL_FRONT = 4, 
			WALL_BACK = 8,
			WALL_FLOOR = 16, 
			WALL_CEILING = 32, 
			WALL_SUM = 63;
	
		public const int DG_LEFT = 0, 
			DG_RIGHT = 180, 
			DG_FRONT = 90, 
			DG_BACK = 270;

		#endregion

		#region Variables, constants
		
		protected int walls;
		protected Vector3 corner1, corner2;
		protected string wall_face, floor_face;
		protected ArrayList Renders, // This needed to be disposed
			Clips, Objects; // They don't need any care
		public ArrayList RoomsInSight, turn_add, turn_remove;
		protected RoomPostType post;
		protected Vector3[] roomids;

		public Vector3 Lower{ get{ return corner1;} set {corner1 = value;}}
		public Vector3 Upper{ get{ return corner2;} set {corner2 = value;} }
		public string Wall_face{ get{ return wall_face; } set{ wall_face = value; } }
		public string Floor_face{ get{ return floor_face; } set{ floor_face = value; } }
		public RoomPostType Post{ get{ return post; } set{ post = value; } }

		#endregion

		/// <summary>
		/// Serializable data class for rooms
		/// </summary>
		[Serializable]
		protected class RoomData : GameObjectData
		{
			public Vector3 corner1, corner2;
			public string wall_face, floor_face;
			public RoomPostType post;
			public GameObjectData[] objects;
			public Vector3[] rooms_in_sight;

			public RoomData()
			{
				description = "Room";
			}
		}

		public Room(Vector3 _corner1, Vector3 _corner2, string wall_face, string floor_face)
		{
			#region Initializations
		
			walls = WALL_SUM;
			corner1 = Vector3.Minimize(_corner1, _corner2);
			corner2 = Vector3.Maximize(_corner1, _corner2);
			this.floor_face = floor_face;
			this.wall_face = wall_face;
			
			Renders = new ArrayList();
			Clips = new ArrayList();
			Objects = new ArrayList();

			RoomsInSight = new ArrayList();

			post = RoomPostType.None;

			turn_add = new ArrayList();
			turn_remove = new ArrayList();

			#endregion


		}

		public Room(GameObjectData data)
		{
			RoomData d = data as RoomData;
			this.corner1 = d.corner1;
			this.corner2 = d.corner2;
			this.floor_face = d.floor_face;
			this.post = d.post;
			this.wall_face = d.wall_face;

			this.Objects = new ArrayList();
			for(int i = 0; i < d.objects.Length; i++)
			{
				this.Objects.Add(d.objects[i].create());
			}

			walls = WALL_SUM;
			Renders = new ArrayList();
			Clips = new ArrayList();
			RoomsInSight = new ArrayList();
			turn_add = new ArrayList();
			turn_remove = new ArrayList();
		}

		public void ExtractRoomSightInformation(GameObjectData data, TwoWayLinkedGraph Map)
		{
			RoomData d = data as RoomData;
			roomids = d.rooms_in_sight;
			Map.DoForAllNodes(new TWLFunc(CheckId));
			roomids = null;
		}

		public void CheckId(object theme)
		{
			Room room = theme as Room;
			Vector3 id = room.Lower;
			for(int i = 0; i < roomids.Length; i++)
			{
				if(id == roomids[i])
				{
					RoomsInSight.Add(theme);
					break;
				}
			}
		}

		public Vector3 CenterOnFloor
		{
			get 
			{ 
				Vector3 average = StaticMathLibrary.Center(corner1, corner2);
				average.Y = corner1.Y + 1.0f;
				return average;
			}
		}

		public void AddRoomToSight(Room room)
		{
			RoomsInSight.Add(room);
		}

		public void AddObject(IGameObject obj)
		{
			Objects.Add(obj);
		}

		public void RemoveObject(IGameObject obj)
		{
			Objects.Remove(obj);
		}

		public void AddObjectTurn(IGameObject obj)
		{
			turn_add.Add(obj);
		}

		public void RemoveObjectTurn(IGameObject obj)
		{
			turn_remove.Add(obj);
		}

		public void RenderRoomsInSight()
		{
			RenderWithEntrances();
			foreach(object o in RoomsInSight) ((Room)o).RenderWithEntrances();
		}

		public void IdentifyCurrent(DynamicObject player)
		{
			if(StaticMathLibrary.InBox(player.position, corner1, corner2))
				player.CurrentRoom = this;
		}

		public void FollowPlayer(DynamicObject player)
		{
			if(!StaticMathLibrary.InBox(player.position, corner1, corner2))
			{
				foreach(object o in links) ((Room)((TWLLink)o).GetTo()).IdentifyCurrent(player);
				foreach(object o in linkends) ((Room)((TWLLink)o).GetFrom()).IdentifyCurrent(player);
			}
		}

		public void RePlaceObjects()
		{
			foreach(object o in Objects)
			{
				(o as Clipper).Position = CenterOnFloor;
			}
		}
		
		public void AddLighsToAllWalls()
		{
			#region For all walls
			
			DecoratedLight lamp;
			
			if((walls & WALL_LEFT) > 0)
			{
				Vector3 c = new Vector3(corner1.X, corner2.Y, corner2.Z);
				Vector3 center = StaticMathLibrary.Center(corner1, c);
				lamp = new DecoratedLight(center, Vector3.Empty, Vector3.Empty, 0f);
				Objects.Add(lamp);
			}

			if((walls & WALL_RIGHT) > 0)
			{
				Vector3 c = new Vector3(corner2.X, corner1.Y, corner1.Z);
				Vector3 center = StaticMathLibrary.Center(c, corner2);
				lamp = new DecoratedLight(center, Vector3.Empty, Vector3.Empty, (float)Math.PI);
				Objects.Add(lamp);
			}

			if((walls & WALL_BACK) > 0)
			{
				Vector3 c = new Vector3(corner2.X, corner2.Y, corner1.Z);
				Vector3 center = StaticMathLibrary.Center(corner1, c);
				lamp = new DecoratedLight(center, Vector3.Empty, Vector3.Empty, (float)(Math.PI / 2));
				Objects.Add(lamp);
			}

			if((walls & WALL_FRONT) > 0)
			{
				Vector3 c = new Vector3(corner1.X, corner1.Y, corner2.Z);
				Vector3 center = StaticMathLibrary.Center(c, corner2);
				lamp = new DecoratedLight(center, Vector3.Empty, Vector3.Empty, (float)(3 * Math.PI / 2));
				Objects.Add(lamp);
			}

			#endregion
		}
		
		public void Build(Engine engine)
		{
			#region For all walls

			Primitive p;

			if((walls & WALL_LEFT) > 0)
			{
				Vector3 c = new Vector3(corner1.X, corner2.Y, corner2.Z);
				Renders.Add(new RenderPrimitive(p = engine.Pr_Plane(corner1, c, StaticVectorLibrary.Right, wall_face)));
				Clips.Add(new Clipper(corner1, c));
			}

			if((walls & WALL_RIGHT) > 0)
			{
				Vector3 c = new Vector3(corner2.X, corner1.Y, corner1.Z);
				Renders.Add(new RenderPrimitive(p = engine.Pr_Plane(c, corner2, StaticVectorLibrary.Left, wall_face)));
				Clips.Add(new Clipper(c, corner2));
			}

			if((walls & WALL_BACK) > 0)
			{
				Vector3 c = new Vector3(corner2.X, corner2.Y, corner1.Z);
				Renders.Add(new RenderPrimitive(p = engine.Pr_Plane(corner1, c, StaticVectorLibrary.Front, wall_face)));
				Clips.Add(new Clipper(corner1, c));
			}

			if((walls & WALL_FRONT) > 0)
			{
				Vector3 c = new Vector3(corner1.X, corner1.Y, corner2.Z);
				Renders.Add(new RenderPrimitive(p = engine.Pr_Plane(c, corner2, StaticVectorLibrary.Back, wall_face)));
				Clips.Add(new Clipper(c, corner2));
			}

			if((walls & WALL_CEILING) > 0)
			{
				Vector3 c = new Vector3(corner1.X, corner2.Y, corner1.Z);
				Renders.Add(new RenderPrimitive(p = engine.Pr_Plane(c, corner2, StaticVectorLibrary.Bottom, floor_face)));
				Clips.Add(new Clipper(c, corner2));
			}

			if((walls & WALL_FLOOR) > 0)
			{
				Vector3 c = new Vector3(corner2.X, corner1.Y, corner2.Z);
				Renders.Add(new RenderPrimitive(p = engine.Pr_Plane(corner1, c, StaticVectorLibrary.Top, floor_face)));
				Clips.Add(new Clipper(corner1, c));
			}

			if((post & RoomPostType.CenterPost) > 0)
			{
				Vector3 c = CenterOnFloor, 
					c1 = new Vector3(c.X - 0.3f, corner1.Y, c.Z - 0.3f),
					c2 = new Vector3(c.X + 0.3f, corner2.Y, c.Z + 0.3f);
				StaticBox b = new StaticBox(c1, c2, wall_face);
				b.Build(engine);
				Renders.Add(b);
				Clips.Add(b);
			}

			if((post & RoomPostType.Four_Post) > 0)
			{			
				
				StaticBox b;
				Vector3 c1, c2; 
				c1 = new Vector3(corner1.X + 0.4f, corner1.Y, corner1.Z + 0.4f);
				c2 = new Vector3(corner1.X + 0.8f, corner2.Y, corner1.Z + 0.8f);
				b = new StaticBox(c1, c2, floor_face);
				b.Build(engine);
				Renders.Add(b);
				Clips.Add(b);

				c1 = new Vector3(corner1.X + 0.4f, corner1.Y, corner2.Z - 0.8f);
				c2 = new Vector3(corner1.X + 0.8f, corner2.Y, corner2.Z - 0.4f);
				b = new StaticBox(c1, c2, floor_face);
				b.Build(engine);
				Renders.Add(b);
				Clips.Add(b);

				c1 = new Vector3(corner2.X - 0.8f, corner1.Y, corner1.Z + 0.4f);
				c2 = new Vector3(corner2.X - 0.4f, corner2.Y, corner1.Z + 0.8f);
				b = new StaticBox(c1, c2, floor_face);
				b.Build(engine);
				Renders.Add(b);
				Clips.Add(b);

				c1 = new Vector3(corner2.X - 0.8f, corner1.Y, corner2.Z - 0.8f);
				c2 = new Vector3(corner2.X - 0.4f, corner2.Y, corner2.Z - 0.4f);
				b = new StaticBox(c1, c2, floor_face);
				b.Build(engine);
				Renders.Add(b);
				Clips.Add(b);
			}

			#endregion

			foreach(object o in Objects)
			{
				(o as IGameObject).Build(engine);
				Enemy enemy = o as Enemy;
				if(enemy != null) enemy.CurrentRoom = this;
			}
		}

		public void SubWall(int wall_dif)
		{
			walls = walls & (WALL_SUM ^ wall_dif);
		}

		#region IClipping Members

		public void Clip(Clipable clipable)
		{
			foreach(object o in Clips) ((IClipping)o).Clip(clipable);
			foreach(object o in Objects) ((IClipping)o).Clip(clipable);
		}

		public void ClipRoomsInSight(Clipable clipable)
		{
			Clip(clipable);
			foreach(Entrance entrance in links)
			{
				entrance.Clip(clipable);
			}
			foreach(Entrance entrance in linkends)
			{
				entrance.Clip(clipable);
			}
		}

		#endregion

		#region IRenderObject Members

		public void Render()
		{
			foreach(object o in Renders) ((IRenderObject)o).Render();
			foreach(object o in Objects) ((IRenderObject)o).Render();
		}

		#endregion

		public void RenderWithEntrances()
		{
			PreRender();
			Render();
			foreach(object o in links) ((IRenderObject)o).Render();
			PostRender();
		}

		#region IDisposable Members

		public void Dispose()
		{
			foreach(object o in Renders)
			{
				(o as IDisposable).Dispose();
			}
		}

		#endregion

		#region IGameObject Members

		public GameObjectData getData()
		{
			RoomData ret = new RoomData();
			ret.corner1 = this.corner1;
			ret.corner2 = this.corner2;
			ret.floor_face = this.floor_face;
			ret.post = this.post;
			ret.wall_face = this.wall_face;

			ret.objects = new GameObjectData[this.Objects.Count];
			for(int i = 0; i < this.Objects.Count; i++)
			{
				ret.objects[i] = (this.Objects[i] as IGameObject).getData();
			}

			ret.rooms_in_sight = new Vector3[this.RoomsInSight.Count];
			for(int i = 0; i < this.RoomsInSight.Count; i++)
			{
				ret.rooms_in_sight[i] = (this.RoomsInSight[i] as Room).Lower;
			}

			return ret;
		}

		public void PreRender()
		{
			foreach(object o in Objects) (o as IGameObject).PreRender();
		}

		public void PostRender()
		{
			foreach(object o in Objects) (o as IGameObject).PostRender();
		}

		#endregion

		#region IPeriod Members

		public void Period()
		{
			foreach(IPeriod period in Objects) period.Period();

			// Applying changes
			foreach(object o in turn_add) Objects.Add(o);
			foreach(object o in turn_remove) Objects.Remove(o);
			turn_add.Clear();
			turn_remove.Clear();
		}

		#endregion
	}

	/// <summary>
	/// Definates Entrance.
	/// </summary>
	class Entrance : TWLLink, IClipping, IDisposable, IGameObject, IRenderObject
	{
		#region Variables, constatns
		
		public int degree;
		protected Vector3 Room1Upper, Room1Lower, Room2Upper, Room2Lower;
		protected string tex_stair, tex_door;
		protected Stair stair;
		protected Hole hole;

		public static float STEP = 0.2f, 
			HALF_STEP = STEP/2f,
			DoorBottom = 0.05f, 
			DoorSide = 0.15f, 
			DoorCeiling = 1f, 
			DoorExtrude = 0.1f;

		#endregion

		public Entrance(int _degree, string _TextureStair, string _TextureDoor) : base()
		{
			degree = _degree;
			tex_stair = _TextureStair;
			tex_door = _TextureDoor;
		}
		
		[Serializable]
		protected class EntranceData : GameObjectData
		{
			public EntranceData()
			{
				description = "Entrance";
			}

			public string stair_texture, hole_texture;
			public int degree;
			public Vector3 room_from, room_to;
			public GameObjectData stair, hole;
		}

		public Entrance(GameObjectData data, TwoWayLinkedGraph Map)
		{
			EntranceData d = data as EntranceData;
			this.degree = d.degree;
			this.tex_door = d.hole_texture;
			this.tex_stair = d.stair_texture;
			Room1Lower = d.room_to;
			Room2Lower = d.room_from;

			Map.DoForAllNodes(new TWLFunc(IdentifyRoomReferences));
			Map.Link(From, To, this);
		}

		public void IdentifyRoomReferences(object theme)
		{
			Room room = theme as Room;
			if(room.Lower == Room1Lower) To = room;
			if(room.Lower == Room2Lower) From = room;
		}

		public GameObjectData getData()
		{
			EntranceData ret = new EntranceData();
			ret.degree = this.degree;
			ret.hole = this.hole.getData();
			ret.hole_texture = this.tex_door;
			ret.room_from = (this.From as Room).Lower;
			ret.room_to = (this.To as Room).Lower;
			ret.stair = this.stair.getData();
			ret.stair_texture = this.tex_stair;
			return ret;
		}

		public override void OnLinked()
		{
			#region For four walls

			if((degree <=  45) || (degree >= 315)){ ((Room)To).SubWall(Room.WALL_RIGHT);  ((Room)From).SubWall(Room.WALL_LEFT);  }
			if((degree >=  45) && (degree <= 135)){ ((Room)To).SubWall(Room.WALL_BACK);   ((Room)From).SubWall(Room.WALL_FRONT); }
			if((degree >= 135) && (degree <= 225)){ ((Room)To).SubWall(Room.WALL_LEFT);   ((Room)From).SubWall(Room.WALL_RIGHT); }
			if((degree >= 225) && (degree <= 315)){ ((Room)To).SubWall(Room.WALL_FRONT);  ((Room)From).SubWall(Room.WALL_BACK);  }

			#endregion
		}

		public void Build(Engine engine)
		{
			Room1Upper = ((Room)To).Upper;
			Room1Lower = ((Room)To).Lower;
			Room2Upper = ((Room)From).Upper;
			Room2Lower = ((Room)From).Lower;

			#region Direction dependent code

			if((degree <=  45) || (degree >= 315)) // LEFT
			{
				stair = new OldStair( ((Room)To), ((Room)From), StaticVectorLibrary.Left);
				stair.Build(engine);
				hole = new OldHole( ((Room)To), ((Room)From), StaticVectorLibrary.Left);
				hole.Build(engine);
			} 
			if((degree >=  45) && (degree <= 135)) // FRONT
			{
				stair = new OldStair( ((Room)To), ((Room)From), StaticVectorLibrary.Front);
				stair.Build(engine);
				hole = new OldHole( ((Room)To), ((Room)From), StaticVectorLibrary.Front);
				hole.Build(engine);
			}
			if((degree >= 135) && (degree <= 225)) // RIGHT
			{
				stair = new OldStair( ((Room)To), ((Room)From), StaticVectorLibrary.Right);
				stair.Build(engine);
				hole = new OldHole( ((Room)To), ((Room)From), StaticVectorLibrary.Right);
				hole.Build(engine);
			}
			if((degree >= 225) && (degree <= 315)) // BACK
			{
				stair = new OldStair( ((Room)To), ((Room)From), StaticVectorLibrary.Back);
				stair.Build(engine);
				hole = new OldHole( ((Room)To), ((Room)From), StaticVectorLibrary.Back);
				hole.Build(engine);
			}

			#endregion
		}

		#region IClipping Members

		public void Clip(Clipable clipable)
		{
			hole.Clip(clipable);
			stair.Clip(clipable);
		}

		#endregion

		#region IRenderObject Members

		public void Render()
		{
			hole.Render();
			stair.Render();
		}

		#endregion

		#region IDisposable Members

		public void Dispose()
		{
			hole.Dispose();
			stair.Dispose();
		}

		#endregion

		#region IGameObject Members

		public void PreRender()
		{
		}

		public void PostRender()
		{
		}

		#endregion

		#region IPeriod Members

		public void Period()
		{
			// TODO:  Add Entrance.Period implementation
		}

		#endregion
	}

	/// <summary>
	/// Base class for staires.
	/// </summary>
	abstract class Stair : IClipping, IDisposable, IGameObject, IRenderObject
	{
		public static float STEP = 0.2f, HALFSTEP = STEP / 2f;

		protected ArrayList planes = new ArrayList(), clippers = new ArrayList();
		protected Room lower_room, upper_room;
		protected Vector3 box_upper, box_lower;
		protected StaticVectorLibrary.Direction direction;

		public Stair(Room room1, Room room2, StaticVectorLibrary.Direction direction)
		{
			box_upper = new Vector3();
			box_lower = new Vector3();

			#region Selecting Lower Room

			if(room1.Lower.Y > room2.Lower.Y)
			{
				upper_room = room1;
				lower_room = room2;
			} 
			else 
			{
				upper_room = room2;
				lower_room = room1;
			}

			float dense;
			int steps;

			#endregion

			box_upper.Y = upper_room.Lower.Y;
			box_lower.Y = lower_room.Lower.Y;
			steps = (int)Math.Ceiling((box_upper.Y - box_lower.Y) / HALFSTEP);
			dense = (float)((box_upper.Y - box_lower.Y) / steps);

			#region Direction dependent code

			if(direction == StaticVectorLibrary.Left)
			{
				if(upper_room == room1)
				{
					this.direction = StaticVectorLibrary.Left;
					box_upper.X = room1.Upper.X;
					box_upper.Z = Math.Min(upper_room.Upper.Z, lower_room.Upper.Z);
					box_lower.X = room2.Lower.X + steps * STEP;
					box_lower.Z = Math.Max(upper_room.Lower.Z, lower_room.Lower.Z);
				} 
				else 
				{
					this.direction = StaticVectorLibrary.Right;
					box_upper.X = room1.Upper.X - steps * STEP;
					box_upper.Z = Math.Min(upper_room.Upper.Z, lower_room.Upper.Z);
					box_lower.X = room2.Lower.X;
					box_lower.Z = Math.Max(upper_room.Lower.Z, lower_room.Lower.Z);
				}
			} 
			else if(direction == StaticVectorLibrary.Right)
			{
				if(upper_room == room1)
				{
					this.direction = StaticVectorLibrary.Right;
					box_upper.X = room2.Upper.X - steps * STEP;
					box_upper.Z = Math.Min(upper_room.Upper.Z, lower_room.Upper.Z);
					box_lower.X = room1.Lower.X;
					box_lower.Z = Math.Max(upper_room.Lower.Z, lower_room.Lower.Z);
				} 
				else 
				{
					this.direction = StaticVectorLibrary.Left;
					box_upper.X = room2.Upper.X;
					box_upper.Z = Math.Min(upper_room.Upper.Z, lower_room.Upper.Z);
					box_lower.X = room1.Lower.X + steps * STEP;
					box_lower.Z = Math.Max(upper_room.Lower.Z, lower_room.Lower.Z);
				}
			}
			else if(direction == StaticVectorLibrary.Front)
			{
				if(upper_room == room1)
				{
					this.direction = StaticVectorLibrary.Front;
					box_upper.X = Math.Min(upper_room.Upper.X, lower_room.Upper.X);
					box_upper.Z = room1.Lower.Z;
					box_lower.X = Math.Max(upper_room.Lower.X, lower_room.Lower.X);
					box_lower.Z = room2.Upper.Z - steps * STEP;
				} 
				else 
				{
					this.direction = StaticVectorLibrary.Back;
					box_upper.X = Math.Min(upper_room.Upper.X, lower_room.Upper.X);
					box_upper.Z = room1.Lower.Z + steps * STEP;
					box_lower.X = Math.Max(upper_room.Lower.X, lower_room.Lower.X);
					box_lower.Z = room2.Upper.Z;
				}
			}
			else if(direction == StaticVectorLibrary.Back)
			{
				if(upper_room == room1)
				{
					this.direction = StaticVectorLibrary.Back;
					box_upper.X = Math.Min(upper_room.Upper.X, lower_room.Upper.X);
					box_upper.Z = room2.Lower.Z + steps * STEP;
					box_lower.X = Math.Max(upper_room.Lower.X, lower_room.Lower.X);
					box_lower.Z = room1.Upper.Z;
				} 
				else 
				{
					this.direction = StaticVectorLibrary.Front;
					box_upper.X = Math.Min(upper_room.Upper.X, lower_room.Upper.X);
					box_upper.Z = room2.Lower.Z;
					box_lower.X = Math.Max(upper_room.Lower.X, lower_room.Lower.X);
					box_lower.Z = room1.Upper.Z - steps * STEP;
				}
			}

			#endregion
		}

		/// <summary>
		/// Builds stair rendering and clipping
		/// </summary>
		/// <param name="engine">Engine</param>
		public abstract void Build(Engine engine);

		public abstract GameObjectData getData();

		#region IRenderObject Members

		public void Render()
		{
			EPlane plane;
			foreach(Object o in planes)
			{
				plane = o as EPlane;
				plane.Render();
			}
		}

		public void Dispose()
		{
			foreach(object o in planes)
			{
				(o as IDisposable).Dispose();
			}
		}

		#endregion

		#region IClipping Members

		public void Clip(Clipable clipable)
		{
			Clipper clipper;
			foreach(Object o in clippers)
			{
				clipper = o as Clipper;
				clipper.Clip(clipable);
			}
		}

		#endregion

		#region IGameObject Members

		public void PreRender()
		{
		}

		public void PostRender()
		{
		}

		#endregion

		#region IPeriod Members

		public virtual void Period()
		{
		}

		#endregion
	}

	/// <summary>
	/// Pure stair.
	/// </summary>
	class OldStair : Stair
	{
		public OldStair(Room room1, Room room2, StaticVectorLibrary.Direction direction)
			: base(room1, room2, direction)
		{
		}
			
		public override void Build(Engine engine)
		{
			float dense;
			int steps;
			String face = @"..\textures\stair_textures\images.bmp";

			steps = (int)Math.Ceiling((box_upper.Y - box_lower.Y) / HALFSTEP);
			dense = (box_upper.Y - box_lower.Y) / steps;

			Vector3 
				upper = box_upper, 
				lower = box_lower, 
				last_upper = upper, 
				last_lower = lower;

			if(direction == StaticVectorLibrary.Left)
			{
				#region Code for Left direction

				lower.X = upper.X;
				last_lower = lower;
				for(int i = 0; i < steps;  i++)
				{
					lower.X += STEP;
					lower.Z = Math.Max( lower.Z - HALFSTEP, lower_room.Lower.Z );
					upper.Z = Math.Min( upper.Z + HALFSTEP, lower_room.Upper.Z );

					// Front part of stair
					planes.Add(engine.Pr_Plane(
						new Vector3(lower.X, upper.Y, last_lower.Z),
						new Vector3(last_lower.X, upper.Y, last_upper.Z),
						StaticVectorLibrary.Top,
						face
						));
					planes.Add(engine.Pr_Plane(
						new Vector3(lower.X, upper.Y, last_lower.Z),
						new Vector3(lower.X, upper.Y - dense, last_upper.Z),
						StaticVectorLibrary.Right,
						face
						));

					// Upper Z part
					if(last_upper.Z < lower_room.Upper.Z)
					{
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, last_upper.Z),
							new Vector3(upper.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, last_upper.Z),
							new Vector3(lower.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Right,
							face
							));
					}
					if(upper.Z < lower_room.Upper.Z)
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, upper.Z),
							new Vector3(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Front,
							face
							));

					// Lower Z part
					if(last_lower.Z > lower_room.Lower.Z)
					{
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, lower.Z),
							new Vector3(upper.X, upper.Y, last_lower.Z),
							StaticVectorLibrary.Top,
							face
							));
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, lower.Z),
							new Vector3(lower.X, upper.Y - dense, last_lower.Z),
							StaticVectorLibrary.Right,
							face
							));
					}
					if(lower.Z > lower_room.Lower.Z)
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, lower.Z),
							new Vector3(upper.X, upper.Y - dense, lower.Z),
							StaticVectorLibrary.Back,
							face
							));

					clippers.Add(new Clipper(lower, upper));

					last_upper = upper;
					last_lower = lower;

					upper.Y -= dense;
				}

				#endregion
			}
			else if(direction == StaticVectorLibrary.Right)
			{
				#region Code for Right direction

				upper.X = lower.X;
				last_upper = upper;
				for(int i = 0; i < steps;  i++)
				{
					upper.X -= STEP;
					lower.Z = Math.Max( lower.Z - HALFSTEP, lower_room.Lower.Z );
					upper.Z = Math.Min( upper.Z + HALFSTEP, lower_room.Upper.Z );

					// Front part of stair
					planes.Add(engine.Pr_Plane(
						new Vector3(last_upper.X, upper.Y, last_lower.Z),
						new Vector3(upper.X, upper.Y, last_upper.Z),
						StaticVectorLibrary.Top,
						face
						));
					planes.Add(engine.Pr_Plane(
						new Vector3(upper.X, upper.Y, last_lower.Z),
						new Vector3(upper.X, upper.Y - dense, last_upper.Z),
						StaticVectorLibrary.Left,
						face
						));

					// Upper Z part
					if(last_upper.Z < lower_room.Upper.Z)
					{
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, last_upper.Z),
							new Vector3(upper.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
						planes.Add(engine.Pr_Plane(
							new Vector3(upper.X, upper.Y, last_upper.Z),
							new Vector3(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Left,
							face
							));
					}
					if(upper.Z < lower_room.Upper.Z)
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, upper.Z),
							new Vector3(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Front,
							face
							));

					// Lower Z part
					if(last_lower.Z > lower_room.Lower.Z)
					{
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, lower.Z),
							new Vector3(upper.X, upper.Y, last_lower.Z),
							StaticVectorLibrary.Top,
							face
							));
						planes.Add(engine.Pr_Plane(
							new Vector3(upper.X, upper.Y, lower.Z),
							new Vector3(upper.X, upper.Y - dense, last_lower.Z),
							StaticVectorLibrary.Left,
							face
							));
					}
					if(lower.Z > lower_room.Lower.Z)
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, lower.Z),
							new Vector3(upper.X, upper.Y - dense, lower.Z),
							StaticVectorLibrary.Back,
							face
							));

					clippers.Add(new Clipper(lower, upper));

					last_upper = upper;
					last_lower = lower;

					upper.Y -= dense;
				}

				#endregion
			}
			else if(direction == StaticVectorLibrary.Front)
			{
				#region Code for Front direction

				lower.Z = upper.Z;
				last_lower = lower;
				for(int i = 0; i < steps;  i++)
				{
					lower.Z -= STEP;
					lower.X = Math.Max( lower.X - HALFSTEP, lower_room.Lower.X );
					upper.X = Math.Min( upper.X + HALFSTEP, lower_room.Upper.X );

					// Front part of stair
					planes.Add(engine.Pr_Plane(
						new Vector3(last_lower.X, upper.Y, lower.Z),
						new Vector3(last_upper.X, upper.Y, last_lower.Z),
						StaticVectorLibrary.Top,
						face
						));
					planes.Add(engine.Pr_Plane(
						new Vector3(last_lower.X, upper.Y, lower.Z),
						new Vector3(last_upper.X, upper.Y - dense, lower.Z),
						StaticVectorLibrary.Back,
						face
						));

					// Upper X part
					if(last_upper.X < lower_room.Upper.X)
					{
						planes.Add(engine.Pr_Plane(
							new Vector3(last_upper.X, upper.Y, lower.Z),
							new Vector3(upper.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
						planes.Add(engine.Pr_Plane(
							new Vector3(last_upper.X, upper.Y, lower.Z),
							new Vector3(upper.X, upper.Y - dense, lower.Z),
							StaticVectorLibrary.Back,
							face
							));
					}
					if(upper.X < lower_room.Upper.X)
						planes.Add(engine.Pr_Plane(
							new Vector3(upper.X, upper.Y, lower.Z),
							new Vector3(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Right,
							face
							));

					// Lower X part
					if(last_lower.X > lower_room.Lower.X)
					{
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, lower.Z),
							new Vector3(last_lower.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, lower.Z),
							new Vector3(last_lower.X, upper.Y - dense, lower.Z),
							StaticVectorLibrary.Back,
							face
							));
					}
					if(lower.X > lower_room.Lower.X)
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, lower.Z),
							new Vector3(lower.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Left,
							face
							));

					clippers.Add(new Clipper(lower, upper));
					
					last_upper = upper;
					last_lower = lower;

					upper.Y -= dense;
				}

				#endregion
			}
			else if(direction == StaticVectorLibrary.Back)
			{
				#region Code for Back direction

				upper.Z = lower.Z;
				last_upper = upper;
				for(int i = 0; i < steps;  i++)
				{
					upper.Z += STEP;
					lower.X = Math.Max( lower.X - HALFSTEP, lower_room.Lower.X );
					upper.X = Math.Min( upper.X + HALFSTEP, lower_room.Upper.X );

					// Front part of stair
					planes.Add(engine.Pr_Plane(
						new Vector3(last_lower.X, upper.Y, last_upper.Z),
						new Vector3(last_upper.X, upper.Y, upper.Z),
						StaticVectorLibrary.Top,
						face
						));
					planes.Add(engine.Pr_Plane(
						new Vector3(last_lower.X, upper.Y, upper.Z),
						new Vector3(last_upper.X, upper.Y - dense, upper.Z),
						StaticVectorLibrary.Front,
						face
						));

					// Upper X part
					if(last_upper.X < lower_room.Upper.X)
					{
						planes.Add(engine.Pr_Plane(
							new Vector3(last_upper.X, upper.Y, lower.Z),
							new Vector3(upper.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
						planes.Add(engine.Pr_Plane(
							new Vector3(last_upper.X, upper.Y, upper.Z),
							new Vector3(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Front,
							face
							));
					}
					if(upper.X < lower_room.Upper.X)
						planes.Add(engine.Pr_Plane(
							new Vector3(upper.X, upper.Y, lower.Z),
							new Vector3(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Right,
							face
							));

					// Lower X part
					if(last_lower.X > lower_room.Lower.X)
					{
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, lower.Z),
							new Vector3(last_lower.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, upper.Z),
							new Vector3(last_lower.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Front,
							face
							));
					}
					if(lower.X > lower_room.Lower.X)
						planes.Add(engine.Pr_Plane(
							new Vector3(lower.X, upper.Y, lower.Z),
							new Vector3(lower.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Left,
							face
							));

					clippers.Add(new Clipper(lower, upper));
					
					last_upper = upper;
					last_lower = lower;

					upper.Y -= dense;
				}
				#endregion
			}

		}

		public override GameObjectData getData()
		{
			return new GameObjectData("Old Stair");
		}


	}

	/// <summary>
	/// Base class for entrance holes.
	/// </summary>
	abstract class Hole : IClipping, IDisposable, IGameObject, IRenderObject
	{
		public static float STEP = 0.2f, 
			HALF_STEP = STEP/2f,
			DoorBottom = 0.05f, 
			DoorSide = 0.15f, 
			DoorCeiling = 1f, 
			DoorExtrude = 0.1f;

		protected ArrayList planes = new ArrayList(), clippers = new ArrayList();
		protected Room lower_room, upper_room;
		protected Vector3 box_upper, box_lower;
		protected StaticVectorLibrary.Direction direction;

		public Hole(Room room1, Room room2, StaticVectorLibrary.Direction direction)
		{
			box_upper = new Vector3();
			box_lower = new Vector3();

			#region Direction dependent code

			if(direction == StaticVectorLibrary.Left)
			{
				lower_room = room1;
				upper_room = room2;
				this.direction = StaticVectorLibrary.Left;

			} 
			else if(direction == StaticVectorLibrary.Right)
			{
				lower_room = room2;
				upper_room = room1;
				this.direction = StaticVectorLibrary.Left;
			}
			else if(direction == StaticVectorLibrary.Front)
			{
				lower_room = room2;
				upper_room = room1;
				this.direction = StaticVectorLibrary.Front;
			}
			else if(direction == StaticVectorLibrary.Back)
			{
				lower_room = room1;
				upper_room = room2;
				this.direction = StaticVectorLibrary.Front;
			}

			if(this.direction == StaticVectorLibrary.Left)
			{
				box_upper.X = lower_room.Upper.X - DoorExtrude;
				box_lower.X = lower_room.Upper.X + DoorExtrude;
				box_upper.Y = Math.Min(lower_room.Upper.Y, upper_room.Upper.Y) - DoorCeiling;
				box_lower.Y = Math.Max(lower_room.Lower.Y, upper_room.Lower.Y) + DoorBottom;
				box_upper.Z = Math.Min(lower_room.Upper.Z, upper_room.Upper.Z) - DoorSide;
				box_lower.Z = Math.Max(lower_room.Lower.Z, upper_room.Lower.Z) + DoorSide;
			} 
			else 
			{
				box_upper.X = Math.Min(lower_room.Upper.X, upper_room.Upper.X) - DoorSide;
				box_lower.X = Math.Max(lower_room.Lower.X, upper_room.Lower.X) + DoorSide;
				box_upper.Y = Math.Min(lower_room.Upper.Y, upper_room.Upper.Y) - DoorCeiling;
				box_lower.Y = Math.Max(lower_room.Lower.Y, upper_room.Lower.Y) + DoorBottom;
				box_upper.Z = lower_room.Upper.Z - DoorExtrude;
				box_lower.Z = lower_room.Upper.Z + DoorExtrude;
			}

			#endregion
		}

		/// <summary>
		/// Build the entrance rendering and clipping.
		/// </summary>
		/// <param name="engine">Engine</param>
		public abstract void Build(Engine engine);

		public abstract GameObjectData getData();

		#region IRenderObject Members

		public void Render()
		{
			EPlane plane;
			foreach(Object o in planes)
			{
				plane = o as EPlane;
				plane.Render();
			}
		}

		public void Dispose()
		{
			foreach(object o in planes)
			{
				(o as IDisposable).Dispose();
			}
		}

		#endregion

		#region IClipping Members

		public void Clip(Clipable clipable)
		{
			Clipper clipper;
			foreach(Object o in clippers)
			{
				clipper = o as Clipper;
				clipper.Clip(clipable);
			}
		}

		#endregion

		#region IGameObject Members

		public void PreRender()
		{
		}

		public void PostRender()
		{
		}

		#endregion

		#region IPeriod Members

		public virtual void Period()
		{
		}

		#endregion
	}

	/// <summary>
	/// Pure entrance hole.
	/// </summary>
	class OldHole : Hole
	{
		private String face = @"..\textures\stair_textures\wood.bmp";

		public OldHole(Room room1, Room room2, StaticVectorLibrary.Direction direction)
			: base(room1, room2, direction)
		{
		}
	
		public override void Build(Engine engine)
		{
			if(direction == StaticVectorLibrary.Left)
			{
				#region Code for Left / Right direction

				// First side outer tile
				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, upper_room.Lower.Y, upper_room.Lower.Z),
					new Vector3(box_lower.X, upper_room.Upper.Y, box_lower.Z),
					StaticVectorLibrary.Right,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_lower.Y - DoorBottom, box_lower.Z),
					new Vector3(box_lower.X, box_lower.Y, box_upper.Z),
					StaticVectorLibrary.Right,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
					new Vector3(box_lower.X, upper_room.Upper.Y, box_upper.Z),
					StaticVectorLibrary.Right,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, upper_room.Lower.Y, box_upper.Z),
					new Vector3(box_lower.X, upper_room.Upper.Y, upper_room.Upper.Z),
					StaticVectorLibrary.Right,
					face
					));

				// Second side outer tile
				planes.Add(engine.Pr_Plane(
					new Vector3(box_upper.X, lower_room.Lower.Y, lower_room.Lower.Z),
					new Vector3(box_upper.X, lower_room.Upper.Y, box_lower.Z),
					StaticVectorLibrary.Left,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_upper.X, box_lower.Y - DoorBottom, box_lower.Z),
					new Vector3(box_upper.X, box_lower.Y, box_upper.Z),
					StaticVectorLibrary.Left,
					face
					));
				
				planes.Add(engine.Pr_Plane(
					new Vector3(box_upper.X, box_upper.Y, box_lower.Z),
					new Vector3(box_upper.X, lower_room.Upper.Y, box_upper.Z),
					StaticVectorLibrary.Left,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_upper.X, lower_room.Lower.Y, box_upper.Z),
					new Vector3(box_upper.X, lower_room.Upper.Y, lower_room.Upper.Z),
					StaticVectorLibrary.Left,
					face
					));

				// Inner tile
				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_lower.Y, box_upper.Z),
					new Vector3(box_upper.X, box_upper.Y, box_upper.Z),
					StaticVectorLibrary.Back,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_lower.Y, box_lower.Z),
					new Vector3(box_upper.X, box_upper.Y, box_lower.Z),
					StaticVectorLibrary.Front,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_lower.Y, box_lower.Z),
					new Vector3(box_upper.X, box_lower.Y, box_upper.Z),
					StaticVectorLibrary.Top,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
					new Vector3(box_upper.X, box_upper.Y, box_upper.Z),
					StaticVectorLibrary.Bottom,
					face
					));

				// Clippers
				clippers.Add(new Clipper(
					new Vector3(box_lower.X, Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), Math.Min(upper_room.Lower.Z, lower_room.Lower.Z)),
					new Vector3(box_upper.X, Math.Max(upper_room.Upper.Y, lower_room.Upper.Y), box_lower.Z)
					));

				clippers.Add(new Clipper(
					new Vector3(box_lower.X, Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), box_lower.Z),
					new Vector3(box_upper.X, box_lower.Y, box_upper.Z)
					));

				clippers.Add(new Clipper(
					new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
					new Vector3(box_upper.X, Math.Min(upper_room.Upper.Y, lower_room.Upper.Y), box_upper.Z)
					));

				clippers.Add(new Clipper(
					new Vector3(box_lower.X, Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), box_upper.Z),
					new Vector3(box_upper.X, Math.Max(upper_room.Upper.Y, lower_room.Upper.Y), Math.Max(upper_room.Upper.Z, lower_room.Upper.Z))
					));

				#endregion
			} 
			else 
			{
				#region Code for Front / Back direction

				// First side outer tile
				planes.Add(engine.Pr_Plane(
					new Vector3(lower_room.Lower.X, lower_room.Lower.Y, box_upper.Z),
					new Vector3(box_lower.X, lower_room.Upper.Y, box_upper.Z),
					StaticVectorLibrary.Back,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_lower.Y - DoorBottom, box_upper.Z),
					new Vector3(box_upper.X, box_lower.Y, box_upper.Z),
					StaticVectorLibrary.Back,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_upper.Y, box_upper.Z),
					new Vector3(box_upper.X, lower_room.Upper.Y, box_upper.Z),
					StaticVectorLibrary.Back,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_upper.X, lower_room.Lower.Y, box_upper.Z),
					new Vector3(lower_room.Upper.X, lower_room.Upper.Y, box_upper.Z),
					StaticVectorLibrary.Back,
					face
					));

				// Second side outer tile
				planes.Add(engine.Pr_Plane(
					new Vector3(upper_room.Lower.X, upper_room.Lower.Y, box_lower.Z),
					new Vector3(box_lower.X, upper_room.Upper.Y, box_lower.Z),
					StaticVectorLibrary.Front,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_lower.Y - DoorBottom, box_lower.Z),
					new Vector3(box_upper.X, box_lower.Y, box_lower.Z),
					StaticVectorLibrary.Front,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
					new Vector3(box_upper.X, upper_room.Upper.Y, box_lower.Z),
					StaticVectorLibrary.Front,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_upper.X, upper_room.Lower.Y, box_lower.Z),
					new Vector3(upper_room.Upper.X, upper_room.Upper.Y, box_lower.Z),
					StaticVectorLibrary.Front,
					face
					));

				// Inner tile
				planes.Add(engine.Pr_Plane(
					new Vector3(box_upper.X, box_lower.Y, box_lower.Z),
					new Vector3(box_upper.X, box_upper.Y, box_upper.Z),
					StaticVectorLibrary.Left,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_lower.Y, box_lower.Z),
					new Vector3(box_lower.X, box_upper.Y, box_upper.Z),
					StaticVectorLibrary.Right,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_lower.Y, box_lower.Z),
					new Vector3(box_upper.X, box_lower.Y, box_upper.Z),
					StaticVectorLibrary.Top,
					face
					));

				planes.Add(engine.Pr_Plane(
					new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
					new Vector3(box_upper.X, box_upper.Y, box_upper.Z),
					StaticVectorLibrary.Bottom,
					face
					));

				// Clippers
				clippers.Add(new Clipper(
					new Vector3(Math.Min(upper_room.Lower.X, lower_room.Lower.X), Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), box_lower.Z),
					new Vector3(box_lower.X, Math.Max(upper_room.Upper.Y, lower_room.Upper.Y), box_upper.Z)
					));

				clippers.Add(new Clipper(
					new Vector3(box_lower.X, Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), box_lower.Z),
					new Vector3(box_upper.X, box_lower.Y, box_upper.Z)
					));

				clippers.Add(new Clipper(
					new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
					new Vector3(box_upper.X, Math.Max(upper_room.Upper.Y, lower_room.Upper.Y), box_upper.Z)
					));

				clippers.Add(new Clipper(
					new Vector3(box_upper.X, Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), box_lower.Z),
					new Vector3(Math.Max(upper_room.Upper.X, lower_room.Upper.X), Math.Max(upper_room.Upper.Y, lower_room.Upper.Y), box_upper.Z)
					));

				#endregion
			}
		}

		public override GameObjectData getData()
		{
			return new GameObjectData("Old Hole");
		}

	}


}