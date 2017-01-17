package hu.csega.superstition.game.room;

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