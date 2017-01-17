package hu.csega.superstition.game.maze;

class DenseMaze : GridMaze
{
	public DenseMaze(int xsize, int ysize) :
		base(xsize, ysize)
	{
		ConnectAll();
		AddStartPlaces();
	}

	protected void ConnectAll()
	{
		for(int i = 0; i < XSize-1; i++)
		{
			for(int j = 0; j < YSize-1; j++)
			{
				DefaultCorridor(i, j, i+1, j);
				DefaultCorridor(i, j, i, j+1);
			}
		}
	}

	protected void AddStartPlaces()
	{
		for(int i = 0; i < XSize; i++)
			for(int j = 0; j < YSize; j++)
			{
				Room r = GetRoom(i, j);
				if(r != null) addStartRoom(r);
			}
	}
}