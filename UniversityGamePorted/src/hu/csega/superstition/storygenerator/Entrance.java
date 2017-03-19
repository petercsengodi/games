package hu.csega.superstition.storygenerator;

class Entrance extends TWLLink
{
	public int degree;

	public Entrance(int _degree)
	{
		degree = _degree;
	}

	@Override
	public void onLinked()
	{


		if((degree <=  45) || (degree >= 315)){ ((Room)to).SubWall(Room.WALL_RIGHT);  ((Room)from).SubWall(Room.WALL_LEFT);  }
		if((degree >=  45) && (degree <= 135)){ ((Room)to).SubWall(Room.WALL_BACK);   ((Room)from).SubWall(Room.WALL_FRONT); }
		if((degree >= 135) && (degree <= 225)){ ((Room)to).SubWall(Room.WALL_LEFT);   ((Room)from).SubWall(Room.WALL_RIGHT); }
		if((degree >= 225) && (degree <= 315)){ ((Room)to).SubWall(Room.WALL_FRONT);  ((Room)from).SubWall(Room.WALL_BACK);  }


	}
}
