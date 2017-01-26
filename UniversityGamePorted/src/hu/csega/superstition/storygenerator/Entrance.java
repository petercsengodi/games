package hu.csega.superstition.storygenerator;

class Entrance extends TWLLink
{


	public int degree;



	public Entrance(int _degree) : base()
	{
		degree = _degree;
	}

	@Override
	public override void OnLinked()
	{


		if((degree <=  45) || (degree >= 315)){ ((Room)To).SubWall(Room.WALL_RIGHT);  ((Room)From).SubWall(Room.WALL_LEFT);  }
		if((degree >=  45) && (degree <= 135)){ ((Room)To).SubWall(Room.WALL_BACK);   ((Room)From).SubWall(Room.WALL_FRONT); }
		if((degree >= 135) && (degree <= 225)){ ((Room)To).SubWall(Room.WALL_LEFT);   ((Room)From).SubWall(Room.WALL_RIGHT); }
		if((degree >= 225) && (degree <= 315)){ ((Room)To).SubWall(Room.WALL_FRONT);  ((Room)From).SubWall(Room.WALL_BACK);  }


	}
}
