using System;
using System.IO;
using System.Xml;
using System.Collections;

using Microsoft.DirectX;

namespace Model
{
	using Mathematics;

	class FaceLibrary
	{
		class Cache
		{
			public Cache(string library, string[] list)
			{
				this.library = library;
				this.list = list;
			}

			public string library;
			public string[] list;
		}

		protected static ArrayList cache = null;

		public static string[] getLibrary(string library)
		{
			if(cache == null) cache = new ArrayList();

			foreach(object o in cache)
			{
				Cache c = o as Cache;
				if(c.library == library) return c.list;
			}

			DirectoryInfo info = new DirectoryInfo(@"..\textures\" + library);
			FileInfo[] files = info.GetFiles();
			string[] list = new string[files.Length];
			for(int i = 0; i < files.Length; i++)
			{
				list[i] = @"..\textures\" + library + "\\" + files[i].Name;
			}

			cache.Add(new Cache(library, list));
			return list;
		}

		public static string RandomGetFromLibrary(string library)
		{
			string[] list = getLibrary(library);
			int idx = (int)Math.Round(StaticRandomLibrary.DoubleValue(list.Length));
			return list[idx];
		}

		public static string RandomGetFromLibrary(string[] list)
		{
			int idx = (int)Math.Floor(StaticRandomLibrary.DoubleValue(list.Length));
			return list[idx];
		}
	}

	interface IGeneratedMaze
	{
		TwoWayLinkedGraph Generate();
//		object GetSerialData();
//		void UploadSerialData(object obj);

		void addStartRoom(Room room);
		ArrayList getStartPlaces();
	}

	#region Grid Maze

	class GridMaze : IGeneratedMaze
	{
		#region Texture Libraries

		string[] walls, floors, stairs;

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
				if(value < min_room_width) 
					max_room_size = min_room_width;
				else if(value > max_room_width) 
					max_room_size = max_room_width;
				else max_room_size = value;
			}
		}

		private const float
			max_room_width = 12f,
			max_room_height = 10f,
			max_corridor_height = 4f,
			max_corridor_width = 4f,
			min_room_height = 4f,
			min_corridor_height = 2.0f,
			min_room_width = 5f,
			min_corridor_width = 1.5f,
			room_difference = 2f,
			room_corridor_diff = 1f,
			room_corridor_hdiff = 0.75f,
			min_corridor_length = 4f;

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

		ArrayList start_rooms;

		public GridMaze(int xsize, int ysize)
		{
			this.xsize = xsize;
			this.ysize = ysize;
			this.max_room_size = max_room_width;

			SetUpGraph();
			distribution = Distribution();
			start_rooms = new ArrayList();

			#region Setting Texture Libraries

			walls = FaceLibrary.getLibrary("wall_textures");
			floors = FaceLibrary.getLibrary("floor_textures");
			stairs = FaceLibrary.getLibrary("stair_textures");

			#endregion
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

		#region Randomizing Algorythm

		protected virtual Vector3[,,] CountRoomBoxes()
		{
			Vector3[,,] ret = new Vector3[xsize, ysize, 2];
			
			float xmin, xmax, ymin, ymax, height, hpos;

			for(int x = 0; x < xsize; x++)
				for(int y = 0; y < ysize; y++)
				{
					// Placing the room horizontally

					xmin = StaticRandomLibrary.FloatValue(
						-max_room_size / 2f, -min_room_width / 2f);
					xmax = StaticRandomLibrary.FloatValue(
						min_room_width / 2f, max_room_size / 2f);
					
					ymin = StaticRandomLibrary.FloatValue(
						-max_room_size / 2f, -min_room_width / 2f);
					ymax = StaticRandomLibrary.FloatValue(
						min_room_width / 2f, max_room_size / 2f);
					
					// Placing the room vertically

					hpos = StaticRandomLibrary.FloatValue(
						room_difference);
					height = StaticRandomLibrary.FloatValue(
						min_room_height, max_room_height);

					// Creating vector

					ret[x,y,0] = new Vector3(xmin, hpos, ymin);
					ret[x,y,1] = new Vector3(xmax, hpos + height, ymax);
				}

			return ret;
		}

		protected Vector3[,,] ApplyHorizontalCorridors(Vector3[,,] rooms)
		{
			Vector3[,,] ret = new Vector3[xsize-1, ysize, 2];

			float min, max, diff;

			for(int x = 0; x < xsize - 1; x++)
				for(int y = 0; y < ysize; y++)
				{
					// filling default values

					ret[x, y, 0].X = 0f;
					ret[x, y, 1].X = 0f;

					// counting z values

					min = Math.Max(rooms[x,y,0].Z, rooms[x+1,y,0].Z);
					max = Math.Min(rooms[x,y,1].Z, rooms[x+1,y,1].Z);
					diff = StaticRandomLibrary.FloatValue(
						min_corridor_width, max_corridor_width);
					ret[x,y,0].Z = StaticRandomLibrary.FloatValue(
						min, max - diff);
					ret[x,y,1].Z = ret[x,y,0].Z + diff;

					// counting ymin

					min = Math.Min(
						rooms[x, y, 0].Y - room_corridor_hdiff,
						rooms[x + 1, y, 0].Y - room_corridor_hdiff);
					max = Math.Max(
						rooms[x, y, 0].Y + room_corridor_hdiff,
						rooms[x + 1, y, 0].Y + room_corridor_hdiff);
					ret[x, y, 0].Y = StaticRandomLibrary.FloatValue(
						min, max);

					// counting ymax

					min = Math.Min(
						rooms[x, y, 1].Y - room_corridor_hdiff,
						rooms[x + 1, y, 1].Y - room_corridor_hdiff);
					max = Math.Max(
						rooms[x, y, 1].Y + room_corridor_hdiff,
						rooms[x + 1, y, 1].Y + room_corridor_hdiff);
					ret[x, y, 1].Y = StaticRandomLibrary.FloatValue(
						min, max);

					// counting appropriate difference

					diff = ret[x, y, 1].Y - ret[x, y, 0].Y;
					if(diff < min_corridor_height)
					{
						diff = (min_corridor_height - diff);
						ret[x, y, 1].Y += diff;
					} 
					else if(diff > max_corridor_height)
					{
						diff = diff - max_corridor_height;
						ret[x, y, 1].Y -= diff;
					}

				}

			return ret;
		}

		protected Vector3[,,] ApplyVerticalCorridors(Vector3[,,] rooms)
		{
			Vector3[,,] ret = new Vector3[xsize, ysize - 1, 2];

			float min, max, diff;

			for(int x = 0; x < xsize; x++)
				for(int y = 0; y < ysize - 1; y++)
				{
					// filling default values

					ret[x, y, 0].Z = 0f;
					ret[x, y, 1].Z = 0f;

					// counting x values

					min = Math.Max(rooms[x,y,0].X, rooms[x,y+1,0].X);
					max = Math.Min(rooms[x,y,1].X, rooms[x,y+1,1].X);
					diff = StaticRandomLibrary.FloatValue(
						min_corridor_width, max_corridor_width);
					ret[x,y,0].X = StaticRandomLibrary.FloatValue(
						min, max - diff);
					ret[x,y,1].X = ret[x,y,0].X + diff;

					// counting ymin

					min = Math.Min(
						rooms[x, y, 0].Y - room_corridor_hdiff,
						rooms[x, y + 1, 0].Y - room_corridor_hdiff);
					max = Math.Max(
						rooms[x, y, 0].Y + room_corridor_hdiff,
						rooms[x, y + 1, 0].Y + room_corridor_hdiff);
					ret[x, y, 0].Y = StaticRandomLibrary.FloatValue(
						min, max);

					// counting ymax

					min = Math.Min(
						rooms[x, y, 1].Y - room_corridor_hdiff,
						rooms[x, y + 1, 1].Y - room_corridor_hdiff);
					max = Math.Max(
						rooms[x, y, 1].Y + room_corridor_hdiff,
						rooms[x, y + 1, 1].Y + room_corridor_hdiff);
					ret[x, y, 1].Y = StaticRandomLibrary.FloatValue(
						min, max);

					// counting appropriate difference

					diff = ret[x, y, 1].Y - ret[x, y, 0].Y;
					if(diff < min_corridor_height)
					{
						diff = (min_corridor_height - diff);
						ret[x, y, 1].Y += diff;
					} 
					else if(diff > max_corridor_height)
					{
						diff = diff - max_corridor_height;
						ret[x, y, 1].Y -= diff;
					}

				}

			return ret;
		}

		protected void IdentifyRoomsCorridors(Vector3[,,] rooms,
			Vector3[,,] hor_corr, Vector3[,,] ver_corr)
		{
			float grid = min_corridor_length + max_room_size;

			for(int x = 0; x < xsize; x++)
				for(int y = 0; y < ysize; y++)
				{
					// Identifying room

					Room room = GetRoom(x,y);
					if(room == null) continue;

					room.Lower = new Vector3(
						grid * x + rooms[x, y, 0].X,
						rooms[x, y, 0].Y,
						grid * y + rooms[x, y, 0].Z);
					
					room.Upper = new Vector3(
						grid * x + rooms[x, y, 1].X,
						rooms[x, y, 1].Y,
						grid * y + rooms[x, y, 1].Z);

					room.RePlaceObjects();

					// Identifying right corridor

					room = graph[x,y].RCorr;
					if(room != null)
					{
                        float x1 = grid * x + rooms[x, y, 1].X;
						float x2 = grid * (x+1) + rooms[x+1, y, 0].X;

						room.Lower = new Vector3(
							x1, hor_corr[x, y, 0].Y,
							grid * y + hor_corr[x, y, 0].Z);

						room.Upper = new Vector3(
							x2, hor_corr[x, y, 1].Y,
							grid * y + hor_corr[x, y, 1].Z);

						room.RePlaceObjects();
					}

					// Identifying down corridor

					room = graph[x,y].DCorr;
					if(room != null)
					{
						float y1 = grid * y + rooms[x, y, 1].Z;
						float y2 = grid * (y+1) + rooms[x, y+1, 0].Z;

						room.Lower = new Vector3(
							grid * x + ver_corr[x, y, 0].X, 
							ver_corr[x, y, 0].Y, y1);

						room.Upper = new Vector3(
							grid * x + ver_corr[x, y, 1].X,
							ver_corr[x, y, 1].Y, y2);

						room.RePlaceObjects();
					}

				}
		}

		#endregion

		/// <summary>
		/// Makes room size variables varied by random.
		/// </summary>
		public void Randomize()
		{
			Vector3[,,] rooms = CountRoomBoxes();
			Vector3[,,] hor_corr = ApplyHorizontalCorridors(rooms);
			Vector3[,,] ver_corr = ApplyVerticalCorridors(rooms);
			IdentifyRoomsCorridors(rooms, hor_corr, ver_corr);
		}

		/// <summary>
		/// Creates a distribution that represents the neighbour
		/// nodes.
		/// </summary>
		/// <returns>Null, if there's no free neighbour.</returns>
		private double[] createNDis()
		{
			double[] ret = new double[xsize * ysize];

			#region Create Distribution

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

			#endregion

			if(modified) return ret;
			else return null;
		}

		/// <summary>
		/// Makes map dense.
		/// </summary>
		public void MakeDense()
		{
 			#region Density Algorythm

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

				GetRoom(x, y).Post = (RoomPostType)(StaticRandomLibrary.IntValue(4));

				dense = createNDis();
			}

			#endregion
		}

		#endregion

		#region IGeneratedMaze Members

		public TwoWayLinkedGraph Generate()
		{
			TwoWayLinkedGraph ret = new TwoWayLinkedGraph();

			Randomize();

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

			#region Add Spiders to Rooms
			
			for(int i = 0; i < xsize; i++)
			{
				for(int j = 0; j < ysize; j++)
				{
					if(graph[i, j] != null)
					{
						Node node = graph[i,j];
						if(node.room != null)
						{
							Room room = node.room;
							room.RePlaceObjects();
							for(int sp = 0; sp < 2; sp++)
							{
								Spider spider = new Spider();
								room.AddObject(spider);
								spider.CurrentRoom = room;
								spider.position = room.CenterOnFloor;
							}
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
								new Entrance(Room.DG_RIGHT,
								FaceLibrary.RandomGetFromLibrary(stairs),
								FaceLibrary.RandomGetFromLibrary(floors)));
						
							ret.Link(graph[i+1,j].room, node.RCorr,
								new Entrance(Room.DG_LEFT,
								FaceLibrary.RandomGetFromLibrary(stairs),
								FaceLibrary.RandomGetFromLibrary(floors)));
						}

						if(node.DCorr != null)
						{
							ret.AddNode(node.DCorr);

							ret.Link(node.room, node.DCorr,
								new Entrance(Room.DG_FRONT,
								FaceLibrary.RandomGetFromLibrary(stairs),
								FaceLibrary.RandomGetFromLibrary(floors)));
						
							ret.Link(graph[i,j+1].room, node.DCorr,
								new Entrance(Room.DG_BACK,
								FaceLibrary.RandomGetFromLibrary(stairs),
								FaceLibrary.RandomGetFromLibrary(floors)));
						}
					}
				}
			}

			#endregion

			#region Set Room Sights

			for(int i = 0; i < xsize; i++)
			{
				for(int j = 0; j < ysize; j++)
				{
					if(graph[i, j] != null)
					{
						Room room = graph[i,j].room;
						if(room != null)
						{
							for(int x = i, y = j; graph[x,y].UCorr != null; y--)
							{
								room.AddRoomToSight(graph[x,y].UCorr);
								room.AddRoomToSight(graph[x,y-1].room);
								if(graph[x, y-1].LCorr != null) 
									room.AddRoomToSight(graph[x, y-1].LCorr);
								if(graph[x, y-1].RCorr != null) 
									room.AddRoomToSight(graph[x, y-1].RCorr);
							}

							for(int x = i, y = j; graph[x,y].DCorr != null; y++)
							{
								room.AddRoomToSight(graph[x,y].DCorr);
								room.AddRoomToSight(graph[x,y+1].room);
								if(graph[x, y+1].LCorr != null) 
									room.AddRoomToSight(graph[x, y+1].LCorr);
								if(graph[x, y+1].RCorr != null) 
									room.AddRoomToSight(graph[x, y+1].RCorr);
							}

							for(int x = i, y = j; graph[x,y].LCorr != null; x--)
							{
								room.AddRoomToSight(graph[x,y].LCorr);
								room.AddRoomToSight(graph[x-1,y].room);
								if(graph[x-1, y].UCorr != null) 
									room.AddRoomToSight(graph[x-1, y].UCorr);
								if(graph[x-1, y].DCorr != null) 
									room.AddRoomToSight(graph[x-1, y].DCorr);
							}

							for(int x = i, y = j; graph[x,y].RCorr != null; x++)
							{
								room.AddRoomToSight(graph[x,y].RCorr);
								room.AddRoomToSight(graph[x+1,y].room);
								if(graph[x+1, y].UCorr != null) 
									room.AddRoomToSight(graph[x+1, y].UCorr);
								if(graph[x+1, y].DCorr != null) 
									room.AddRoomToSight(graph[x+1, y].DCorr);
							}
						}
					}
				}
			}

			#endregion

			#region Set Corridor Sights

			for(int i = 0; i < xsize; i++)
			{
				for(int j = 0; j < ysize; j++)
				{

					if((graph[i,j] != null) && (graph[i,j].RCorr != null))
					{
						Room corridor = graph[i,j].RCorr;
						for(int x = i, y = j; graph[x,y].RCorr != null; x++)
						{
							corridor.AddRoomToSight(graph[x+1,y].room);
							if(graph[x+1,y].RCorr != null)
								corridor.AddRoomToSight(graph[x+1,y].RCorr);
							if(graph[x+1,y].UCorr != null)
								corridor.AddRoomToSight(graph[x+1,y].UCorr);
							if(graph[x+1,y].DCorr != null)
								corridor.AddRoomToSight(graph[x+1,y].DCorr);
						}
						for(int x = i+1, y = j; graph[x,y].LCorr != null; x--)
						{
							corridor.AddRoomToSight(graph[x-1,y].room);
							if(graph[x-1,y].LCorr != null)
								corridor.AddRoomToSight(graph[x-1,y].LCorr);
							if(graph[x-1,y].UCorr != null)
								corridor.AddRoomToSight(graph[x-1,y].UCorr);
							if(graph[x-1,y].DCorr != null)
								corridor.AddRoomToSight(graph[x-1,y].DCorr);
						}
					}

					if((graph[i,j] != null) && (graph[i,j].DCorr != null))
					{
						Room corridor = graph[i,j].DCorr;
						for(int x = i, y = j; graph[x,y].DCorr != null; y++)
						{
							corridor.AddRoomToSight(graph[x,y+1].room);
							if(graph[x,y+1].DCorr != null)
								corridor.AddRoomToSight(graph[x,y+1].DCorr);
							if(graph[x,y+1].LCorr != null)
								corridor.AddRoomToSight(graph[x,y+1].LCorr);
							if(graph[x,y+1].RCorr != null)
								corridor.AddRoomToSight(graph[x,y+1].RCorr);
						}
						for(int x = i, y = j+1; graph[x,y].UCorr != null; y--)
						{
							corridor.AddRoomToSight(graph[x,y-1].room);
							if(graph[x,y-1].UCorr != null)
								corridor.AddRoomToSight(graph[x,y-1].UCorr);
							if(graph[x,y-1].LCorr != null)
								corridor.AddRoomToSight(graph[x,y-1].LCorr);
							if(graph[x,y-1].RCorr != null)
								corridor.AddRoomToSight(graph[x,y-1].RCorr);
						}
					}

				}
			}

			#endregion

			#region Set Room Lights

			for(int i = 0; i < xsize; i++)
			{
				for(int j = 0; j < ysize; j++)
				{
					if(graph[i, j] != null)
					{
						Node node = graph[i,j];
						if(node.room != null)
						{
							node.room.AddLighsToAllWalls();
							if(node.RCorr != null)
								node.RCorr.AddLighsToAllWalls();
							if(node.DCorr != null)
								node.DCorr.AddLighsToAllWalls();
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
				+ room_height, grid * y + max_room_size),
				FaceLibrary.RandomGetFromLibrary(walls),
				FaceLibrary.RandomGetFromLibrary(floors));

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
					lower_room.Lower.Z),
					FaceLibrary.RandomGetFromLibrary(walls),
					FaceLibrary.RandomGetFromLibrary(floors));

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
					lower_room.Lower.Z),
					FaceLibrary.RandomGetFromLibrary(walls),
					FaceLibrary.RandomGetFromLibrary(floors));

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
					grid * y1 + corridor),
					FaceLibrary.RandomGetFromLibrary(walls),
					FaceLibrary.RandomGetFromLibrary(floors));

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
					grid * y1 + corridor),
					FaceLibrary.RandomGetFromLibrary(walls),
					FaceLibrary.RandomGetFromLibrary(floors));

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

		/// <summary>
		/// Prints Maze to Console
		/// </summary>
		public void print()
		{
			#region Print Algorythm

			for(int j = 0; j < ysize; j++)
			{
				for(int i = 0; i < xsize; i++)
				{
					if(GetRoom(i, j) != null)
					{
						if(graph[i,j].right == Connection.Connected)
							Console.Write("X - ");
						else Console.Write("X   ");
					} 
					else 
					{
						Console.Write("    ");
					}
				}

				Console.WriteLine();

				for(int i = 0; i < xsize; i++)
				{
					if(GetRoom(i, j) != null)
					{
						if(graph[i,j].down == Connection.Connected)
							Console.Write("|   ");
						else Console.Write("    ");
					} 
					else 
					{
						Console.Write("    ");
					}
				}

				Console.WriteLine();
			}

			#endregion
		}

		public void addStartRoom(Room room)
		{
			start_rooms.Add(room);
		}

		public ArrayList getStartPlaces()
		{
			ArrayList ret = new ArrayList();
			foreach(Room r in start_rooms)
			{
				ret.Add(new StartPlace(r.CenterOnFloor));
			}
			return ret;
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
					res.Post = (RoomPostType)(StaticRandomLibrary.IntValue(4));
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