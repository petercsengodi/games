package hu.csega.superstition.storygenerator.maze;

import hu.csega.superstition.storygenerator.Room;
import hu.csega.superstition.util.StaticRandomLibrary;

class StructedMaze extends GridMaze
{
	public StructedMaze(int xsize, int ysize) :
		base(xsize, ysize)
		{
		}

	public Room SelectRandomRoom()
	{
		int index = StaticRandomLibrary.SelectValue(distribution);
		distribution[index] = 0f;
		int z = (index / XSize), x = (index % YSize);
		return base.DefaultRoom(x, z);
	}

	public void Connect(Room from, Room to)
	{
		#region Algorythm

		int i, j, fromX = 0, fromY = 0, toX = 0, toY = 0;
		for(i=j=0; j < YSize; i+=1, j+= (i / XSize), i %= XSize)
		{
			if(Graph(i,j) == null) continue;
			if(Graph(i,j).room == from) {fromX =i; fromY = j;}
			if(Graph(i,j).room == to) {toX = i; toY = j;}
		}

		int lastX = fromX, lastY = fromY, actX = 0, actY = 0;
		Room last = from;
		double hval1, hval2;

		while(last != to)
		{
			hval1 = hval2 = -10 - Math.Sqrt(XSize*XSize + YSize*YSize);

			if(Graph(lastX, lastY).left != Connection.None)
			{ // LEFT DIRECTION
				hval2 = - Math.Sqrt((toX-lastX+1)*(toX-lastX+1) + (toY-lastY)*(toY-lastY))
						+ StaticRandomLibrary.DoubleValue();
				if(hval1 < hval2)
				{
					hval1 = hval2;
					actX = lastX-1;
					actY = lastY;
				}
			} // END OF LEFT DIRECTION

			if(Graph(lastX, lastY).right != Connection.None)
			{ // RIGHT DIRECTION
				hval2 = - Math.Sqrt((toX-lastX-1)*(toX-lastX-1) + (toY-lastY)*(toY-lastY))
						+ StaticRandomLibrary.DoubleValue();
				if(hval1 < hval2)
				{
					hval1 = hval2;
					actX = lastX+1;
					actY = lastY;
				}
			} // END OF RIGHT DIRECTION

			if(Graph(lastX, lastY).up != Connection.None)
			{ // UP DIRECTION
				hval2 = - Math.Sqrt((toX-lastX)*(toX-lastX) + (toY-lastY+1)*(toY-lastY+1))
						+ StaticRandomLibrary.DoubleValue();
				if(hval1 < hval2)
				{
					hval1 = hval2;
					actX = lastX;
					actY = lastY-1;
				}
			} // END OF UP DIRECTION

			if(Graph(lastX, lastY).down != Connection.None)
			{ // DOWN DIRECTION
				hval2 = - Math.Sqrt((toX-lastX)*(toX-lastX) + (toY-lastY-1)*(toY-lastY-1))
						+ StaticRandomLibrary.DoubleValue();
				if(hval1 < hval2)
				{
					hval1 = hval2;
					actX = lastX;
					actY = lastY+1;
				}
			} // END OF DOWN DIRECTION

			if(Graph(actX, actY).room == null)
			{
				Room res = base.DefaultRoom(actX, actY);
			}

			base.DefaultCorridor(lastX, lastY, actX, actY);

			lastX = actX;
			lastY = actY;
			last = Graph(actX, actY).room;
		}

		#endregion
	}
}