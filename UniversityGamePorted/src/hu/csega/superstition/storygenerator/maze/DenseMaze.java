package hu.csega.superstition.storygenerator.maze;

class DenseMaze extends GridMaze
{
	public DenseMaze(int xsize, int ysize) {
		super(xsize, ysize);
		ConnectAll();
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
}