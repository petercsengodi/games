using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using Mathematics;
using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;

namespace StoryGenerator
{
	class Room : TWLNode
	{
		#region Direction Constants

		public const int WALL_LEFT = 1, 
			WALL_RIGHT = 2, 
			WALL_FRONT = 4, 
			WALL_BACK = 8,
			WALL_FLOOR = 16, 
			WALL_CEILING = 32, 
			WALL_SUM = 63;
	
		public const int DG_LEFT = 0, 
			DG_RIGHT = 180, 
			DG_FRONT = 90, 
			DG_BACK = 270;

		#endregion

		#region Variables, constants
		
		protected int walls;
		protected Vector3 corner1, corner2;
		protected string face, texString;
		protected Image bitmap;
		
		protected Texture texture = null;
		protected Device device = null;
		protected VertexBuffer buffer = null;

		public Vector3 Lower{ get{ return corner1;} }
		public Vector3 Upper{ get{ return corner2;} }

		public string Face{ get{ return (string)face.Clone(); } 
			set
			{
				face = (string)value.Clone(); 
				if((face != null) && (face.CompareTo("") != 0))
					bitmap = Bitmap.FromFile(face);
			}
		}

		public string DirectXTexture 
		{
			get { return (string)texString.Clone(); }
			set 
			{
				texString = (string)value.Clone();
				texture = TextureLibrary.getTexture(texString);
			}
		}

		public string Mark{ get{ return face; } 
			set	{ face = value; }
		}

		public Image BITMAP
		{
			get { return bitmap; }
			set { bitmap = value; }
		}

		private Node node;
		public Node NODE { get { return node; } set { node = value; } }

		#endregion

		public Room(Vector3 _corner1, Vector3 _corner2)
		{
			#region Initializations
		
			walls = WALL_SUM;
			corner1 = Vector3.Minimize(_corner1, _corner2);
			corner2 = Vector3.Maximize(_corner1, _corner2);

			#endregion
		}

		public Vector3 CenterOnFloor
		{
			get 
			{ 
				Vector3 average = StaticMathLibrary.Center(corner1, corner2);
				average.Y = corner1.Y + 1.0f;
				return average;
			}
		}

		public void SubWall(int wall_dif)
		{
			walls = walls & (WALL_SUM ^ wall_dif);
		}

		public void Draw(Graphics g)
		{
			if(bitmap == null) 
			{
				SolidBrush brush = new SolidBrush(Color.Black);
				g.FillRectangle(brush, corner1.X, corner1.Y, corner2.X - corner1.X, corner2.Y - corner1.Y);
				brush.Dispose();
			} g.DrawImage(bitmap, corner1.X, corner1.Z, corner2.X - corner1.X, corner2.Z - corner1.Z);
		}

		public void Initialize(Device device)
		{
			this.device = device;
			buffer = new VertexBuffer(typeof(CustomVertex.PositionTextured),
				36, this.device, 0, CustomVertex.PositionTextured.Format, Pool.Default);
			buffer.Created += new EventHandler(OnReCreate);
			OnReCreate(buffer, null);
		}

		public void OnReCreate(object buf, EventArgs ea)
		{
			GraphicsStream stream = buffer.Lock(0, 0, 0);

			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner1.Z, 0f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner2.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner1.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner2.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner1.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner2.Z, 1f, 1f));

			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner1.Z, 0f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner2.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner1.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner2.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner1.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner2.Z, 1f, 1f));

			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner1.Z, 0f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner2.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner1.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner2.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner1.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner2.Z, 1f, 1f));

			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner1.Z, 0f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner2.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner1.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner2.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner1.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner2.Z, 1f, 1f));

			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner1.Z, 0f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner1.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner1.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner1.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner1.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner1.Z, 1f, 1f));

			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner1.Y, corner2.Z, 0f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner2.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner2.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner1.X, corner2.Y, corner2.Z, 0f, 1f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner1.Y, corner2.Z, 1f, 0f));
			stream.Write(new CustomVertex.PositionTextured(corner2.X, corner2.Y, corner2.Z, 1f, 1f));

			buffer.Unlock();
		}
		
		public void DrawDirectX()
		{
			if(device == null) return;
			device.SetTexture(0, texture);
			device.SetStreamSource(0, buffer, 0);
			device.VertexFormat = CustomVertex.PositionTextured.Format;
			device.DrawPrimitives(PrimitiveType.TriangleList, 0, 12);
		}
	}

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

	interface IGeneratedMaze
	{
		TwoWayLinkedGraph Generate();
	}

	#region Structured Grid Maze
	
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

	#endregion

	#region Grid Maze

	class GridMaze : IGeneratedMaze
	{
		#region Texture Libraries

//		string[] walls, floors, stairs;

		#endregion

		#region Attributes

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

		#endregion

		#region Node Type

		protected enum Connection
		{
			None = 0,
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

		#endregion

		public GridMaze(int xsize, int ysize)
		{
			this.xsize = xsize;
			this.ysize = ysize;
			this.max_room_size = 3f;

			SetUpGraph();
			distribution = Distribution();

		}

		#region Graph Building

		private Node[,] graph;
		private void SetUpGraph()
		{
			#region Instatiate

			graph = new Node[xsize, ysize];
			for(int i = 0; i < xsize; i++)
			{
				for(int j = 0; j < ysize; j++)
				{
					if((i % 3 == 1) && (j % 3 == 1)) graph[i,j] = null;
					else graph[i,j] = new Node();
				}
			}
			
			#endregion

			#region Set Borders

			for(int i = 0; i < xsize; i++)
			{
				if(graph[i, 0] != null) 
					graph[i, 0].up = Connection.None;
				if(graph[i, ysize-1] != null) 
					graph[i, ysize-1].down = Connection.None;
			}
			for(int i = 0; i < ysize; i++)
			{
				if(graph[0, i] != null) 
					graph[0, i].left = Connection.None;
				if(graph[xsize - 1, i] != null) 
					graph[xsize-1, i].right = Connection.None;
			}

			#endregion

			#region Set Structure
			
			for(int i = 0; i < xsize; i++)
			{
				for(int j = 0; j < ysize; j++)
				{
					if(graph[i,j] == null) continue;

					// erase right links
					if((j % 3 != 1) && (i % 3 == 2)) 
						graph[i,j].right = Connection.None;
					if((j % 3 == 1) && (i % 3 < 2)) 
						graph[i,j].right = Connection.None;
					// erase left links
					if((j % 3 != 1) && (i % 3 == 0)) 
						graph[i,j].left = Connection.None;
					if((j % 3 == 1) && (i % 3 > 0)) 
						graph[i,j].left = Connection.None;
					// erase down links
					if((i % 3 != 1) && (j % 3 == 2)) 
						graph[i,j].down = Connection.None;
					if((i % 3 == 1) && (j % 3 < 2)) 
						graph[i,j].down = Connection.None;
					// erase up links
					if((i % 3 != 1) && (j % 3 == 0)) 
						graph[i,j].up = Connection.None;
					if((i % 3 == 1) && (j % 3 > 0)) 
						graph[i,j].up = Connection.None;
				}
			}

			#endregion

		} // End of function SetUpGraph

		protected Node Graph(int x, int y)
		{
			return graph[x,y];
		}

		#endregion

		#region Randomizing

		protected double[] Distribution()
		{
			double[] ret = new double[xsize * ysize];
			for(int i = 0; i < xsize; i++)
			{
				for(int j = 0; j < ysize; j++)
				{
					if(graph[i,j] != null) ret[j * xsize + i] = 1.0;
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
			bool modified = false;
			for(int x = 0; x < xsize; x++)
			{
				for(int y = 0; y < ysize; y++)
				{
					int idx = y * xsize + x;
					if(graph[x, y] == null) 
					{
						ret[idx] = 0.0;
						continue;
					}
					else if(graph[x,y].room != null)
					{
						ret[idx] = 0.0;
						continue;
					}
					else 
					{
						if((GetRoom(x-1, y) != null) 
							&& (graph[x, y].left != Connection.None))
						{
							ret[idx] = 1.0;
							modified = true;
							continue;
						}

						if((GetRoom(x+1, y) != null) 
							&& (graph[x, y].right != Connection.None))
						{
							ret[idx] = 1.0;
							modified = true;
							continue;
						}

						if((GetRoom(x, y-1) != null) 
							&& (graph[x, y].up != Connection.None))
						{
							ret[idx] = 1.0;
							modified = true;
							continue;
						}

						if((GetRoom(x, y+1) != null) 
							&& (graph[x, y].down != Connection.None))
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
				if((GetRoom(x-1, y) != null) && (graph[x,y].left != Connection.None))
					dense[0] = 1.0; else dense[0] = 0.0;
				if((GetRoom(x+1, y) != null) && (graph[x,y].right != Connection.None)) 
					dense[1] = 1.0; else dense[1] = 0.0;
				if((GetRoom(x, y-1) != null) && (graph[x,y].up != Connection.None))
					dense[2] = 1.0; else dense[2] = 0.0;
				if((GetRoom(x, y+1) != null) && (graph[x,y].down != Connection.None))
					dense[3] = 1.0; else dense[3] = 0.0;

				idx = StaticRandomLibrary.SelectValue(dense);

				if(idx == 0) DefaultCorridor(x, y, x-1, y);
				else if(idx == 1) DefaultCorridor(x, y, x+1, y);
				else if(idx == 2) DefaultCorridor(x, y, x, y-1);
				else if(idx == 3) DefaultCorridor(x, y, x, y+1);

				dense = createNDis();
			}
		}

		#endregion

		#region IGeneratedMaze Members

		public TwoWayLinkedGraph Generate()
		{
			TwoWayLinkedGraph ret = new TwoWayLinkedGraph();

			#region Add Rooms

			for(int i = 0; i < xsize; i++)
			{
				for(int j = 0; j < ysize; j++)
				{
					if(graph[i, j] != null)
					{
						Node node = graph[i,j];
						if(node.room != null)
						{
							ret.AddNode(node.room);
						}
					}
				}
			}

			#endregion

			#region Add Corridors

			for(int i = 0; i < xsize; i++)
			{
				for(int j = 0; j < ysize; j++)
				{
					Node node = graph[i,j];
					if(node != null && node.room != null)
					{
						if(node.RCorr != null) 
						{
							ret.AddNode(node.RCorr);

							ret.Link(node.room, node.RCorr,
								new Entrance(Room.DG_RIGHT));
						
						
							ret.Link(graph[i+1,j].room, node.RCorr,
								new Entrance(Room.DG_LEFT));
						
						}

						if(node.DCorr != null)
						{
							ret.AddNode(node.DCorr);

							ret.Link(node.room, node.DCorr,
								new Entrance(Room.DG_FRONT));
						
							ret.Link(graph[i,j+1].room, node.DCorr,
								new Entrance(Room.DG_BACK));
						
						}
					}
				}
			}

			#endregion


			return ret;
		}

		#endregion

		/// <summary>
		/// Generates a Default Room with Random Texture
		/// </summary>
		/// <param name="x">Node X coordinate</param>
		/// <param name="y">Node Y coordinate</param>
		/// <returns>Instatiated Room</returns>
		public Room DefaultRoom(int x, int y)
		{
			if(graph[x, y] == null) return null;

			// Minimum space between rooms
			float grid = max_room_size * 2f + 4f; 
			float room_height = 3f;

			#region Create Default Room

			Room room = new Room(
				new Vector3(grid * x - max_room_size, 
				- room_height, grid * y - max_room_size),
				new Vector3(grid * x + max_room_size, 
				+ room_height, grid * y + max_room_size));

			#endregion

			graph[x,y].room = room;
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

			#region Four Direction Check

			if(x1 == x2 && y1 == y2 - 1)
			{
				if(graph[x1, y1] == null) return null;
				if(graph[x2, y2] == null) return null;
				if(graph[x1, y1].down != Connection.Available) return null;
				if(graph[x2, y2].up != Connection.Available) return null;

				if(graph[x1, y1].room == null)
					graph[x1, y1].room = DefaultRoom(x1, y1);
				if(graph[x2, y2].room == null)
					graph[x2, y2].room = DefaultRoom(x2, y2);
				Room upper_room = graph[x1, y1].room;
				Room lower_room = graph[x2, y2].room;

				Room Corridor = new Room(
					new Vector3(
					grid * x1 - corridor,
					-corridor,
					upper_room.Upper.Z),
					new Vector3(
					grid * x1 + corridor, 
					+corridor,
					lower_room.Lower.Z));

				graph[x1, y1].down = Connection.Connected;
				graph[x1, y1].DCorr = Corridor;
				graph[x2, y2].up = Connection.Connected;
				graph[x2, y2].UCorr = Corridor;

				return Corridor;
			}

			else if(x1 == x2 && y1 == y2 + 1)
			{
				if(graph[x1, y1] == null) return null;
				if(graph[x2, y2] == null) return null;
				if(graph[x1, y1].up != Connection.Available) return null;
				if(graph[x2, y2].down != Connection.Available) return null;

				if(graph[x1, y1].room == null)
					graph[x1, y1].room = DefaultRoom(x1, y1);
				if(graph[x2, y2].room == null)
					graph[x2, y2].room = DefaultRoom(x2, y2);
				Room upper_room = graph[x2, y2].room;
				Room lower_room = graph[x1, y1].room;

				Room Corridor = new Room(
					new Vector3(
					grid * x1 - corridor,
					-corridor,
					upper_room.Upper.Z),
					new Vector3(
					grid * x1 + corridor, 
					+corridor,
					lower_room.Lower.Z));

				graph[x2, y2].down = Connection.Connected;
				graph[x2, y2].DCorr = Corridor;
				graph[x1, y1].up = Connection.Connected;
				graph[x1, y1].UCorr = Corridor;

				return Corridor;
			}

			else if(x1 == x2 - 1 && y1 == y2)
			{
				if(graph[x1, y1] == null) return null;
				if(graph[x2, y2] == null) return null;
				if(graph[x1, y1].right != Connection.Available) return null;
				if(graph[x2, y2].left != Connection.Available) return null;

				if(graph[x1, y1].room == null)
					graph[x1, y1].room = DefaultRoom(x1, y1);
				if(graph[x2, y2].room == null)
					graph[x2, y2].room = DefaultRoom(x2, y2);
				Room left_room = graph[x1, y1].room;
				Room right_room = graph[x2, y2].room;

				Room Corridor = new Room(
					new Vector3(left_room.Upper.X,
					-corridor, 
					grid * y1 -corridor),
					new Vector3(right_room.Lower.X,
					+corridor, 
					grid * y1 + corridor));

				graph[x1, y1].right = Connection.Connected;
				graph[x1, y1].RCorr = Corridor;
				graph[x2, y2].left = Connection.Connected;
				graph[x2, y2].LCorr = Corridor;

				return Corridor;
			}

			else if(x1 == x2 + 1 && y1 == y2)
			{
				if(graph[x1, y1] == null) return null;
				if(graph[x2, y2] == null) return null;
				if(graph[x1, y1].left != Connection.Available) return null;
				if(graph[x2, y2].right != Connection.Available) return null;

				if(graph[x1, y1].room == null)
					graph[x1, y1].room = DefaultRoom(x1, y1);
				if(graph[x2, y2].room == null)
					graph[x2, y2].room = DefaultRoom(x2, y2);
				Room left_room = graph[x2, y2].room;
				Room right_room = graph[x1, y1].room;

				Room Corridor = new Room(
					new Vector3(left_room.Upper.X,
					-corridor,
					grid * y1 -corridor),
					new Vector3(right_room.Lower.X,
					+corridor,
					grid * y1 + corridor));

				graph[x2, y2].right = Connection.Connected;
				graph[x2, y2].RCorr = Corridor;
				graph[x1, y1].left = Connection.Connected;
				graph[x1, y1].LCorr = Corridor;

				return Corridor;
			}

			else return null;

			#endregion

		} // End of Function Default Corridor

		public Room GetRoom(int x, int y)
		{
			if((x < 0) || (x >= xsize)) return null;
			if((y < 0) || (y >= ysize)) return null;
			if(graph[x, y] == null) return null;
			return graph[x,y].room;
		}
	}


	#endregion

	#region DenseMaze

	class DenseMaze : GridMaze
	{
		public DenseMaze(int xsize, int ysize) : 
			base(xsize, ysize)
		{
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

	#endregion

	#region Structured Maze

	class StructedMaze : GridMaze
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

	#endregion

}