package hu.csega.superstition.storygenerator;

class Entrance : TWLLink
{
	#region Variables, constatns

	public int degree;

	#endregion

	public Entrance(int _degree) : base()
	{
		degree = _degree;
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
}
