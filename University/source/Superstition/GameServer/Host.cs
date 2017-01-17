using System;
using System.IO;
using System.Threading;
using System.Collections;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;

using GameLib;
using System.Net;
using System.Net.Sockets;

namespace GameServer
{
	/// <summary>
	/// Host class.
	/// </summary>
	public class Host : IDisposable
	{
		private HostData host_data;
		private GameObjectData map;
		private Player[] players;
		private Thread thread;
		private bool alive;

		private int game_port;
		private Socket game_socket;
		private byte[] buffer;
		private BinaryFormatter formatter;
		private int timeval;
		private int packet_length;
		
		public HostData HostData
		{
			get
			{
				return host_data;
			}
		}

		public GameObjectData Map
		{
			get{ return map; }
		}

		public int GamePort
		{
			get{ return game_port; }
		}

		public bool Alive{ get{ return alive; } }

		private Host()
		{
			this.thread = null;
			this.alive = true;
			this.host_data = new HostData();
			this.host_data.player_count = 0;
			this.buffer = new byte[NetworkOptions.PacketLength()]; 
			this.formatter = new BinaryFormatter();
			this.timeval = NetworkOptions.TimeVal();
			this.packet_length = NetworkOptions.PacketLength();
		}

		public static Host StartNewHost(
			int game_port, string info, int ID,
			int limit, GameObjectData map)
		{
			if(limit < 1) limit = 1;
			else if(limit > NetworkOptions.MaxLimit()) 
				limit = NetworkOptions.MaxLimit();

			Host host = new Host();
			host.game_port = game_port;
			host.host_data.ID = ID;
			host.host_data.information = (string)info.Clone();
			host.host_data.limit = limit;
			host.players = new Player[host.host_data.limit];
			host.map = map;
			host.Start();
			return host;
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="socket"></param>
		/// <returns>User ID for player. -1, if not managed to add.</returns>
		public Player AddSocketPlayer(Socket socket)
		{
			Player result = null;
			lock(this)
			{
				if(host_data.player_count < host_data.limit)
				{
					host_data.player_count++;
					for(int i=0; i<players.Length; i++)
					{
						if(players[i] == null)
						{
							IPEndPoint ep = (IPEndPoint)
								socket.RemoteEndPoint;
							players[i] = new Player(game_socket, 
								ep.Address, i,
								NetworkOptions.GeneratePlayerPort(game_port, i));
							result = players[i];
							break;
						}
					}
				}
			}

			return result;
		}

		public void Start()
		{
			game_socket = new Socket(AddressFamily.InterNetwork,
				SocketType.Dgram, ProtocolType.Udp);
			game_socket.Bind(new IPEndPoint(IPAddress.Any, game_port));
			game_socket.Blocking = false;
//			game_socket.SetSocketOption(SocketOptionLevel.Socket, 
//				SocketOptionName.Broadcast, 1);

			thread = new Thread(new ThreadStart(Run));
			thread.Start();
		}

		private void Run()
		{
			while(alive)
			{
				if(game_socket.Poll(timeval, SelectMode.SelectRead))
				{
					Receive();
				}
				
			}
		}

		public void Stop()
		{
			if(thread == null) return;
			alive = false;
			thread.Join();
			thread = null;
			
			for(int i = 0; i < players.Length; i++)
			{
				if(players[i] != null) players[i].Dispose();
			}

			game_socket.Close();
		}

		public GameObjectData Receive()
		{
			GameObjectData ret = null;
			EndPoint point = new IPEndPoint(IPAddress.Any, game_port);
//			int result = game_socket.ReceiveFrom(buffer, ref point);
			
			int result = -1;
			IAsyncResult res = game_socket.BeginReceiveFrom(buffer, 0, 
				packet_length, SocketFlags.None, ref point, null, null);
			result = game_socket.EndReceiveFrom(res, ref point);

			if(result == 0)
			{
				ProcessCommand(null, -1);
			} 
			else 
			{
				MemoryStream stream = new MemoryStream(buffer);
				try
				{
					Packet packet = (Packet)formatter.Deserialize(stream);
					if(packet.sender != -1)
					{
						if((players[packet.sender] != null) && 
							(players[packet.sender].CheckCounter(packet.counter)))
						{
							players[packet.sender].SetActive();
							ProcessCommand(packet.data, packet.sender);
							Server.Instance.WriteStatus("Incoming packet: " + 
								packet.data.Description);
						}
					}
				} 
				catch(Exception e)
				{
					Console.WriteLine(result + ":" + e.Message);
				}
				finally
				{
					stream.Close();
				}
			}

			return ret;
		}

		private void ProcessCommand(GameObjectData data, int userID)
		{
			if(userID == -1) return;
			if((data == null) || (data.Description.Equals("Quit Game")))
			{
				players[userID].Dispose();
				players[userID] = null;
				host_data.player_count--;
				//				if(host_data.player_count == 0) Stop();
				alive = false;
				Server.Instance.OnHostsChanged();
			} 
			else if(data.Description.Equals("NetPlayer"))
			{
				NetworkPlayerData net_player = (NetworkPlayerData)data;
				players[userID].SetPlayerData(net_player);
				Player.CalculatePushAway(players);
			}
		}

		#region IDisposable Members

		public void Dispose()
		{
			Stop();
		}

		#endregion
	
	}
}
