package hu.csega.superstition.unported.game.maze;

import hu.csega.superstition.unported.storygenerator.Room;

public class DenseMaze extends GridMaze {

	public DenseMaze(int xsize, int ysize)
	{
		super(xsize, ysize);
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