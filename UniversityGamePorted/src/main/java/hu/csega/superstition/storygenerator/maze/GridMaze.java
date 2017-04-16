package hu.csega.superstition.storygenerator.maze;

import hu.csega.superstition.storygenerator.Room;
import hu.csega.superstition.storygenerator.TwoWayLinkedGraph;
import hu.csega.superstition.util.StaticRandomLibrary;

class GridMaze implements IGeneratedMaze
{


	//	string[] walls, floors, stairs;





	private int xsize, ysize;
	public int XSize
	{
		get{ return xsize; }
		set{ xsize = value; }
	}
	public int YSize
	{
		get{ return ysize; }
		set{ ysize = value; }
	}

	private float max_room_size;
	public float MaxRoomSize
	{
		get{ return max_room_size;}
		set
		{
			if(value < 2f) max_room_size = 2f;
			else if(value > 10f) max_room_size = 10f;
			else max_room_size = value;
		}
	}

	protected double[] distribution;





	protected enum Connection
	{
		None,
		Available,
		Connected
	}

	protected class Node
	{
		public Connection left, right, up, down;
		public Room LCorr, RCorr, UCorr, DCorr;
		public ArrayList stuffs;
		public Room room;

		public Node()
		{
			left = Connection.Available;
			right = Connection.Available;
			up = Connection.Available;
			down = Connection.Available;

			LCorr = null;
			RCorr = null;
			UCorr = null;
			DCorr = null;

			stuffs = new ArrayList();
			room = null;
		}

	}



	public GridMaze(int xsize, int ysize)
	{
		this.xsize = xsize;
		this.ysize = ysize;
		this.max_room_size = 3f;

		SetUpGraph();
		distribution = Distribution();

	}



	private Node[][] graph;
	private void SetUpGraph()
	{


		graph = new Node[xsize][ysize];
		for(int i = 0; i < xsize; i++)
		{
			for(int j = 0; j < ysize; j++)
			{
				if((i % 3 == 1) && (j % 3 == 1)) graph[i][j] = null;
				else graph[i][j] = new Node();
			}
		}





		for(int i = 0; i < xsize; i++)
		{
			if(graph[i][0] != null)
				graph[i][0].up = Connection.None;
			if(graph[i][ysize-1] != null)
				graph[i][ysize-1].down = Connection.None;
		}
		for(int i = 0; i < ysize; i++)
		{
			if(graph[0][i] != null)
				graph[0][i].left = Connection.None;
			if(graph[xsize - 1][i] != null)
				graph[xsize-1][i].right = Connection.None;
		}





		for(int i = 0; i < xsize; i++)
		{
			for(int j = 0; j < ysize; j++)
			{
				if(graph[i][j] == null) continue;

				// erase right links
				if((j % 3 != 1) && (i % 3 == 2))
					graph[i][j].right = Connection.None;
				if((j % 3 == 1) && (i % 3 < 2))
					graph[i][j].right = Connection.None;
				// erase left links
				if((j % 3 != 1) && (i % 3 == 0))
					graph[i][j].left = Connection.None;
				if((j % 3 == 1) && (i % 3 > 0))
					graph[i][j].left = Connection.None;
				// erase down links
				if((i % 3 != 1) && (j % 3 == 2))
					graph[i][j].down = Connection.None;
				if((i % 3 == 1) && (j % 3 < 2))
					graph[i][j].down = Connection.None;
				// erase up links
				if((i % 3 != 1) && (j % 3 == 0))
					graph[i][j].up = Connection.None;
				if((i % 3 == 1) && (j % 3 > 0))
					graph[i][j].up = Connection.None;
			}
		}



	} // End of function SetUpGraph

	protected Node Graph(int x, int y)
	{
		return graph[x][y];
	}





	protected double[] Distribution()
	{
		double[] ret = new double[xsize * ysize];
		for(int i = 0; i < xsize; i++)
		{
			for(int j = 0; j < ysize; j++)
			{
				if(graph[i][j] != null) ret[j * xsize + i] = 1.0;
				else ret[j * xsize + i] = 0.0;
			}
		}
		return ret;
	}

	private void Constraints()
	{
		// TODO: Constraint programing
	}

	/// <summary>
	/// Creates a distribution that represents the neighbour
	/// nodes.
	/// </summary>
	/// <returns>Null, if there's no free neighbour.</returns>
	private double[] createNDis()
	{
		double[] ret = new double[xsize * ysize];
		boolean modified = false;
		for(int x = 0; x < xsize; x++)
		{
			for(int y = 0; y < ysize; y++)
			{
				int idx = y * xsize + x;
				if(graph[x][y] == null)
				{
					ret[idx] = 0.0;
					continue;
				}
				else if(graph[x][y].room != null)
				{
					ret[idx] = 0.0;
					continue;
				}
				else
				{
					if((GetRoom(x-1, y) != null)
							&& (graph[x][y].left != Connection.None))
					{
						ret[idx] = 1.0;
						modified = true;
						continue;
					}

					if((GetRoom(x+1, y) != null)
							&& (graph[x][y].right != Connection.None))
					{
						ret[idx] = 1.0;
						modified = true;
						continue;
					}

					if((GetRoom(x, y-1) != null)
							&& (graph[x][y].up != Connection.None))
					{
						ret[idx] = 1.0;
						modified = true;
						continue;
					}

					if((GetRoom(x, y+1) != null)
							&& (graph[x][y].down != Connection.None))
					{
						ret[idx] = 1.0;
						modified = true;
						continue;
					}

					ret[idx] = 0.0;
				}
			}
		} // End of for cycles

		if(modified) return ret;
		else return null;
	}

	/// <summary>
	/// Makes map dense.
	/// </summary>
	public void MakeDense()
	{
		int idx, x, y;
		double[] dense = createNDis();
		while(dense != null)
		{
			idx = StaticRandomLibrary.SelectValue(dense);
			x = idx % xsize;
			y = idx / xsize;

			dense = new double[4];
			if((GetRoom(x-1, y) != null) && (graph[x][y].left != Connection.None))
				dense[0] = 1.0; else dense[0] = 0.0;
			if((GetRoom(x+1, y) != null) && (graph[x][y].right != Connection.None))
				dense[1] = 1.0; else dense[1] = 0.0;
			if((GetRoom(x, y-1) != null) && (graph[x][y].up != Connection.None))
				dense[2] = 1.0; else dense[2] = 0.0;
			if((GetRoom(x, y+1) != null) && (graph[x][y].down != Connection.None))
				dense[3] = 1.0; else dense[3] = 0.0;

			idx = StaticRandomLibrary.SelectValue(dense);

			if(idx == 0) DefaultCorridor(x, y, x-1, y);
			else if(idx == 1) DefaultCorridor(x, y, x+1, y);
			else if(idx == 2) DefaultCorridor(x, y, x, y-1);
			else if(idx == 3) DefaultCorridor(x, y, x, y+1);

			dense = createNDis();
		}
	}





	@Override
	public TwoWayLinkedGraph Generate()
	{
		TwoWayLinkedGraph ret = new TwoWayLinkedGraph();



		for(int i = 0; i < xsize; i++)
		{
			for(int j = 0; j < ysize; j++)
			{
				if(graph[i][j] != null)
				{
					Node node = graph[i][j];
					if(node.room != null)
					{
						ret.AddNode(node.room);
					}
				}
			}
		}





		for(int i = 0; i < xsize; i++)
		{
			for(int j = 0; j < ysize; j++)
			{
				Node node = graph[i][j];
				if(node != null && node.room != null)
				{
					if(node.RCorr != null)
					{
						ret.AddNode(node.RCorr);

						ret.Link(node.room, node.RCorr,
								new Entrance(Room.DG_RIGHT));


						ret.Link(graph[i+1][j].room, node.RCorr,
								new Entrance(Room.DG_LEFT));

					}

					if(node.DCorr != null)
					{
						ret.AddNode(node.DCorr);

						ret.Link(node.room, node.DCorr,
								new Entrance(Room.DG_FRONT));

						ret.Link(graph[i][j+1].room, node.DCorr,
								new Entrance(Room.DG_BACK));

					}
				}
			}
		}




		return ret;
	}



	/// <summary>
	/// Generates a Default Room with Random Texture
	/// </summary>
	/// <param name="x">Node X coordinate</param>
	/// <param name="y">Node Y coordinate</param>
	/// <returns>Instatiated Room</returns>
	public Room DefaultRoom(int x, int y)
	{
		if(graph[x][y] == null) return null;

		// Minimum space between rooms
		float grid = max_room_size * 2f + 4f;
		float room_height = 3f;



		Room room = new Room(
				new Vector3f(grid * x - max_room_size,
						- room_height, grid * y - max_room_size),
				new Vector3f(grid * x + max_room_size,
						+ room_height, grid * y + max_room_size));



		graph[x][y].room = room;
		return room;
	}

	/// <summary>
	/// Returns default corridor with random textures
	/// between two rooms
	/// </summary>
	/// <param name="x1">First Node X coordinate</param>
	/// <param name="y1">First Node Y coordinate</param>
	/// <param name="x2">Second Node X coordinate</param>
	/// <param name="y2">Second Node Y coordinate</param>
	/// <returns>Instatiated Corridor</returns>
	public Room DefaultCorridor(int x1, int y1, int x2, int y2)
	{
		float grid = max_room_size * 2f + 4f;
		float corridor = 2f;



		if(x1 == x2 && y1 == y2 - 1)
		{
			if(graph[x1][y1] == null) return null;
			if(graph[x2][y2] == null) return null;
			if(graph[x1][y1].down != Connection.Available) return null;
			if(graph[x2][y2].up != Connection.Available) return null;

			if(graph[x1][y1].room == null)
				graph[x1][y1].room = DefaultRoom(x1, y1);
			if(graph[x2][y2].room == null)
				graph[x2][y2].room = DefaultRoom(x2, y2);
			Room upper_room = graph[x1][y1].room;
			Room lower_room = graph[x2][y2].room;

			Room Corridor = new Room(
					new Vector3f(
							grid * x1 - corridor,
							-corridor,
							upper_room.Upper.Z),
					new Vector3f(
							grid * x1 + corridor,
							+corridor,
							lower_room.Lower.Z));

			graph[x1][y1].down = Connection.Connected;
			graph[x1][y1].DCorr = Corridor;
			graph[x2][y2].up = Connection.Connected;
			graph[x2][y2].UCorr = Corridor;

			return Corridor;
		}

		else if(x1 == x2 && y1 == y2 + 1)
		{
			if(graph[x1][y1] == null) return null;
			if(graph[x2][y2] == null) return null;
			if(graph[x1][y1].up != Connection.Available) return null;
			if(graph[x2][y2].down != Connection.Available) return null;

			if(graph[x1][y1].room == null)
				graph[x1][y1].room = DefaultRoom(x1, y1);
			if(graph[x2][y2].room == null)
				graph[x2][y2].room = DefaultRoom(x2, y2);
			Room upper_room = graph[x2][y2].room;
			Room lower_room = graph[x1][y1].room;

			Room Corridor = new Room(
					new Vector3f(
							grid * x1 - corridor,
							-corridor,
							upper_room.Upper.Z),
					new Vector3f(
							grid * x1 + corridor,
							+corridor,
							lower_room.Lower.Z));

			graph[x2][y2].down = Connection.Connected;
			graph[x2][y2].DCorr = Corridor;
			graph[x1][y1].up = Connection.Connected;
			graph[x1][y1].UCorr = Corridor;

			return Corridor;
		}

		else if(x1 == x2 - 1 && y1 == y2)
		{
			if(graph[x1][y1] == null) return null;
			if(graph[x2][y2] == null) return null;
			if(graph[x1][y1].right != Connection.Available) return null;
			if(graph[x2][y2].left != Connection.Available) return null;

			if(graph[x1][y1].room == null)
				graph[x1][y1].room = DefaultRoom(x1, y1);
			if(graph[x2][y2].room == null)
				graph[x2][y2].room = DefaultRoom(x2, y2);
			Room left_room = graph[x1][y1].room;
			Room right_room = graph[x2][y2].room;

			Room Corridor = new Room(
					new Vector3f(left_room.Upper.X,
							-corridor,
							grid * y1 -corridor),
					new Vector3f(right_room.Lower.X,
							+corridor,
							grid * y1 + corridor));

			graph[x1][y1].right = Connection.Connected;
			graph[x1][y1].RCorr = Corridor;
			graph[x2][y2].left = Connection.Connected;
			graph[x2][y2].LCorr = Corridor;

			return Corridor;
		}

		else if(x1 == x2 + 1 && y1 == y2)
		{
			if(graph[x1][y1] == null) return null;
			if(graph[x2][y2] == null) return null;
			if(graph[x1][y1].left != Connection.Available) return null;
			if(graph[x2][y2].right != Connection.Available) return null;

			if(graph[x1][y1].room == null)
				graph[x1][y1].room = DefaultRoom(x1, y1);
			if(graph[x2][y2].room == null)
				graph[x2][y2].room = DefaultRoom(x2, y2);
			Room left_room = graph[x2][y2].room;
			Room right_room = graph[x1][y1].room;

			Room Corridor = new Room(
					new Vector3f(left_room.Upper.X,
							-corridor,
							grid * y1 -corridor),
					new Vector3f(right_room.Lower.X,
							+corridor,
							grid * y1 + corridor));

			graph[x2][y2].right = Connection.Connected;
			graph[x2][y2].RCorr = Corridor;
			graph[x1][y1].left = Connection.Connected;
			graph[x1][y1].LCorr = Corridor;

			return Corridor;
		}

		else return null;



	} // End of Function Default Corridor

	public Room GetRoom(int x, int y)
	{
		if((x < 0) || (x >= xsize)) return null;
		if((y < 0) || (y >= ysize)) return null;
		if(graph[x][y] == null) return null;
		return graph[x][y].room;
	}
}