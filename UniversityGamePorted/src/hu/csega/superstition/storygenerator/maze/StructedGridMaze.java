package hu.csega.superstition.storygenerator.maze;

import hu.csega.superstition.storygenerator.NormalPreview;
import hu.csega.superstition.storygenerator.Room;
import hu.csega.superstition.storygenerator.TwoWayLinkedGraph;

class StructedGridMaze : IGeneratedMaze
{
	int xsize, zsize, maxNodes;
	public NormalPreview it;
	double[] distribution;
	TwoWayLinkedGraph Map;

	enum NodeState { NotLinkable, NotLinked, Linked }

	struct Node
	{
		#region Node definition

		public Room room;

		public NodeState left, right, up, down;
		public Room Left, Right, Up, Down;

		public int x, z, index;

		public Node(int _x, int _z, int _index)
		{
			x = _x;
			z = _z;
			index = _index;

			room = null;
			left = right = up = down = NodeState.NotLinkable;
			Left = Right = Up = Down = null;
		}

		#endregion
	}

	Node[,] graph;

	public StructedGridMaze(int _xsize, int _zsize, NormalPreview _it)
	{
		#region Static Building Structure

		it = _it;
		xsize = _xsize*3;
		zsize = _zsize*3;
		maxNodes = xsize*zsize;
		distribution = new double[maxNodes];
		graph = new Node[xsize, zsize];
		Map = new TwoWayLinkedGraph();

		int i, j;
		for(i=j=0; j < zsize; i+=1, j+= (i / xsize), i %= xsize)
		{
			graph[i,j] = new Node(i, j, j*xsize + i);
			distribution[j*xsize + i] = 1.0;

			switch((i % 3) + (j % 3) * 3)
			{ // MAKING STATIC CONNECTIONS
				case 0:
					graph[i,j].right = graph[i,j].down = NodeState.NotLinked;
					break;

				case 1:
					if(j > 0) graph[i,j].up = NodeState.NotLinked;
					graph[i,j].left = graph[i,j].right = NodeState.NotLinked;
					break;

				case 2:
					graph[i,j].left = graph[i,j].down = NodeState.NotLinked;
					break;

				case 3:
					if(i > 0) graph[i,j].left = NodeState.NotLinked;
					graph[i,j].up = graph[i,j].down = NodeState.NotLinked;
					break;

				case 5:
					if(i < xsize -1) graph[i,j].right = NodeState.NotLinked;
					graph[i,j].up = graph[i,j].down = NodeState.NotLinked;
					break;

				case 6:
					graph[i,j].right = graph[i,j].up = NodeState.NotLinked;
					break;

				case 7:
					if(j < zsize -1) graph[i,j].down = NodeState.NotLinked;
					graph[i,j].left = graph[i,j].right = NodeState.NotLinked;
					break;

				case 8:
					graph[i,j].left = graph[i,j].up = NodeState.NotLinked;
					break;

				default:
					distribution[j*xsize + i] = 0.0;
					break;
			}
		}

		#endregion
	}

	public StructedGridMaze(int _xsize, int _zsize)
	{
		#region Static Building Structure

		it = null;
		xsize = _xsize*3;
		zsize = _zsize*3;
		maxNodes = xsize*zsize;
		distribution = new double[maxNodes];
		graph = new Node[xsize, zsize];
		Map = new TwoWayLinkedGraph();

		int i, j;
		for(i=j=0; j < zsize; i+=1, j+= (i / xsize), i %= xsize)
		{
			graph[i,j] = new Node(i, j, j*xsize + i);
			distribution[j*xsize + i] = 1.0;

			switch((i % 3) + (j % 3) * 3)
			{ // MAKING STATIC CONNECTIONS
				case 0:
					graph[i,j].right = graph[i,j].down = NodeState.NotLinked;
					break;

				case 1:
					if(j > 0) graph[i,j].up = NodeState.NotLinked;
					graph[i,j].left = graph[i,j].right = NodeState.NotLinked;
					break;

				case 2:
					graph[i,j].left = graph[i,j].down = NodeState.NotLinked;
					break;

				case 3:
					if(i > 0) graph[i,j].left = NodeState.NotLinked;
					graph[i,j].up = graph[i,j].down = NodeState.NotLinked;
					break;

				case 5:
					if(i < xsize -1) graph[i,j].right = NodeState.NotLinked;
					graph[i,j].up = graph[i,j].down = NodeState.NotLinked;
					break;

				case 6:
					graph[i,j].right = graph[i,j].up = NodeState.NotLinked;
					break;

				case 7:
					if(j < zsize -1) graph[i,j].down = NodeState.NotLinked;
					graph[i,j].left = graph[i,j].right = NodeState.NotLinked;
					break;

				case 8:
					graph[i,j].left = graph[i,j].up = NodeState.NotLinked;
					break;

				default:
					distribution[j*xsize + i] = 0.0;
					break;
			}
		}

		#endregion
	}

	public Room SelectRandomRoom()
	{
		#region Algorythm

		int index = StaticRandomLibrary.SelectValue(distribution);
		distribution[index] = 0f;

		int z = (index / xsize), x = (index % xsize);

		graph[x,z].room = new Room(
			new Vector3(x*12f, -4f, z*12f),
			new Vector3(x*12f+6f, 1f, z*12f + 6f));
		if(it != null) graph[x,z].room.BITMAP = it.RoomImage;
		else graph[x,z].room.DirectXTexture = @"floor_textures\brick.bmp";
		graph[x,z].room.Mark = "Room";

		Map.AddNode(graph[x,z].room);

		#endregion

		return graph[x,z].room;
	}

	public void Connect(Room from, Room to)
	{
		#region Algorythm

		int i, j, degree = 0, fromX = 0, fromZ = 0, toX = 0, toZ = 0;
		for(i=j=0; j < zsize; i+=1, j+= (i / xsize), i %= xsize)
		{
			if(graph[i,j].room == from) {fromX =i; fromZ = j;}
			if(graph[i,j].room == to) {toX = i; toZ = j;}
		}

		int lastX = fromX, lastZ = fromZ, actX = 0, actZ = 0;
		Room last = from;
		double hval1, hval2;

		while(last != to)
		{
			hval1 = hval2 = -10 - Math.Sqrt(xsize*xsize + zsize*zsize);

			if(graph[lastX, lastZ].left != NodeState.NotLinkable)
			{ // LEFT DIRECTION
				hval2 = - Math.Sqrt((toX-lastX+1)*(toX-lastX+1) + (toZ-lastZ)*(toZ-lastZ))
					+ StaticRandomLibrary.DoubleValue();
				if(hval1 < hval2)
				{
					hval1 = hval2;
					actX = lastX-1;
					actZ = lastZ;
					degree = 0;
				}
			} // END OF LEFT DIRECTION

			if(graph[lastX, lastZ].right != NodeState.NotLinkable)
			{ // RIGHT DIRECTION
				hval2 = - Math.Sqrt((toX-lastX-1)*(toX-lastX-1) + (toZ-lastZ)*(toZ-lastZ))
					+ StaticRandomLibrary.DoubleValue();
				if(hval1 < hval2)
				{
					hval1 = hval2;
					actX = lastX+1;
					actZ = lastZ;
					degree = 1;
				}
			} // END OF RIGHT DIRECTION

			if(graph[lastX, lastZ].up != NodeState.NotLinkable)
			{ // UP DIRECTION
				hval2 = - Math.Sqrt((toX-lastX)*(toX-lastX) + (toZ-lastZ+1)*(toZ-lastZ+1))
					+ StaticRandomLibrary.DoubleValue();
				if(hval1 < hval2)
				{
					hval1 = hval2;
					actX = lastX;
					actZ = lastZ-1;
					degree = 2;
				}
			} // END OF UP DIRECTION

			if(graph[lastX, lastZ].down != NodeState.NotLinkable)
			{ // DOWN DIRECTION
				hval2 = - Math.Sqrt((toX-lastX)*(toX-lastX) + (toZ-lastZ-1)*(toZ-lastZ-1))
					+ StaticRandomLibrary.DoubleValue();
				if(hval1 < hval2)
				{
					hval1 = hval2;
					actX = lastX;
					actZ = lastZ+1;
					degree = 3;
				}
			} // END OF DOWN DIRECTION

			if(graph[actX, actZ].room == null)
			{
				graph[actX, actZ].room = new Room(
					new Vector3(actX*12f, -4f, actZ*12f),
					new Vector3(actX*12f+6f, 1f, actZ*12f + 6f));
				if(it != null) graph[actX, actZ].room.BITMAP = it.RoomImage;
				else graph[actX, actZ].room.DirectXTexture = @"floor_textures\brick.bmp";
				graph[actX, actZ].room.Mark = "Room";
			}

			switch(degree)
			{
				case 0:
					graph[lastX, lastZ].left = NodeState.Linked;
					graph[actX, actZ].right = NodeState.Linked;
					break;

				case 1:
					graph[lastX, lastZ].right = NodeState.Linked;
					graph[actX, actZ].left = NodeState.Linked;
					break;

				case 2:
					graph[lastX, lastZ].up = NodeState.Linked;
					graph[actX, actZ].down = NodeState.Linked;
					break;

				default:
					graph[lastX, lastZ].down = NodeState.Linked;
					graph[actX, actZ].up = NodeState.Linked;
					break;
			}

			lastX = actX;
			lastZ = actZ;
			last = graph[actX, actZ].room;
		}

		#endregion
	}

	public int ToIndex(int x, int z){ return x + z*xsize; }
	public int ToX(int index) { return index % xsize; }
	public int ToZ(int index) { return index / xsize; }

	#region IGeneratedMaze Members

	public TwoWayLinkedGraph Generate()
	{
		int i, j; Room corridor;
		for(i=j=0; j < zsize; i+=1, j+= (i / (xsize-1)), i %= (xsize-1))
		{
			if(graph[i,j].room == null) continue;

			if(graph[i,j].right == NodeState.Linked)
			{
				corridor = new Room(
					new Vector3(12f * i + 6f, -3.5f, 12f * j + 2.15f),
					new Vector3(12f * i + 12f, -0.5f, 12f * j + 3.85f));
				if(it != null) corridor.BITMAP = it.CorridorImage;
				else corridor.DirectXTexture = @"wall_textures\brick3.bmp";

				corridor.Mark = "Corridor";

				Map.AddNode(corridor);
				Map.Link(graph[i,j].room, corridor, new Entrance(Room.DG_RIGHT));
				Map.Link(graph[i+1,j].room, corridor, new Entrance(Room.DG_LEFT));
				graph[i,j].Right = corridor;
				graph[i+1,j].Left = corridor;
			}

		}

		for(i=j=0; j < zsize-1; i+=1, j+= (i / xsize), i %= xsize)
		{
			if(graph[i,j].room == null) continue;

			if(graph[i,j].down == NodeState.Linked)
			{
				corridor = new Room(
					new Vector3(12f * i + 2.15f, -3.5f, 12f * j + 6f),
					new Vector3(12f * i + 3.85f, -0.5f, 12f * j + 12f));
				if(it != null) corridor.BITMAP = it.CorridorImage;
				else corridor.DirectXTexture = @"wall_textures\brick3.bmp";
				corridor.Mark = "Corridor";

				Map.AddNode(corridor);
				Map.Link(graph[i,j].room, corridor, new Entrance(Room.DG_FRONT));
				Map.Link(graph[i,j+1].room, corridor, new Entrance(Room.DG_BACK));
				graph[i,j].Down = corridor;
				graph[i,j+1].Up = corridor;
			}
		}

		return Map;
	}

	#endregion
}
