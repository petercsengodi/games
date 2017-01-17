package hu.csega.superstition.game.room;

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