using System;
using Microsoft.DirectX;

namespace GameLib
{
	/// <summary>
	/// A class for common network options.
	/// </summary>
	public class NetworkOptions
	{
		public static int PacketLength(){ return 4000; }
		public static int ReceiveLength(){ return 100000; }
		public static int TimeVal(){ return 300; }
		public static int MaxLimit(){ return 15; }
		public static int UdpRate(){ return 10; }

		public static int GenerateHostPort(int main_port, int hostID)
		{
			return main_port + (hostID + 1) * (MaxLimit() + 1);
		}

		public static int GenerateHostPlayerPort(int main_port, int hostID, int playerID)
		{
			return GenerateHostPort(main_port, hostID) + playerID + 1;
		}

		public static int GeneratePlayerPort(int host_port, int playerID)
		{
			return host_port + playerID + 1;
		}
	}

	/// <summary>
	/// Base class for Object datas.
	/// </summary>
	[Serializable]
	public class GameObjectData
	{
		protected string description;

		/// <summary>
		/// Description of this 
		/// </summary>
		public string Description { get { return description; } } 
		
		public GameObjectData(){ description = null; }
		public GameObjectData(string description){ this.description = description; }
		
		public virtual object create()
		{
			throw new Exception("Illegal create function call.");
		}
	}

	/// <summary>
	/// Class for Host Data.
	/// </summary>
	[Serializable]
	public class HostData : GameObjectData
	{
		public int ID;
		public int player_count;
		public int limit;
		public string information;

		public HostData()
		{
			description = "Host Data";
		}
	}

	/// <summary>
	/// Class for Host Data.
	/// </summary>
	[Serializable]
	public class HostList : GameObjectData
	{
		public HostData[] list;

		public HostList()
		{
			description = "Host List";
		}
	}

	/// <summary>
	/// Class for Player data trough Network.
	/// </summary>
	[Serializable]
	public class NetworkPlayerData : GameObjectData
	{
		public Vector3 position, difference;

		public NetworkPlayerData()
		{
			description = "NetPlayer";
		}
	}

	/// <summary>
	/// Class for Player data trough Network.
	/// </summary>
	[Serializable]
	public class AllPlayerData : GameObjectData
	{
		public NetworkPlayerData[] all_data;

		public AllPlayerData()
		{
			description = "AllNetPlayer";
		}
	}

	/// <summary>
	/// Class for creating new host on server.
	/// </summary>
	[Serializable]
	public class PublishHost : GameObjectData
	{
		public int max_players;
		public MapBuffer map;

		public PublishHost()
		{
			description = "Publish Host";
		}
	}

	/// <summary>
	/// Class for creating new host on server.
	/// </summary>
	[Serializable]
	public class MapBuffer : GameObjectData
	{
		public byte[] map_buffer;

		public MapBuffer()
		{
			description = "Map";
		}
	}

	/// <summary>
	/// Class for joining a host on server.
	/// </summary>
	[Serializable]
	public class JoinHost : GameObjectData
	{
		public int HostID;

		public JoinHost()
		{
			description = "Join Host";
		}
	}

	/// <summary>
	/// Class for information of server to clients.
	/// </summary>
	[Serializable]
	public class UserInfo : GameObjectData
	{
		public long userID;
		public int game_port;
		public int client_port;

		public UserInfo()
		{
			description = "User Info";
		}
	}

	/// <summary>
	/// Network Packet class.
	/// </summary>
	[Serializable]
	public class Packet
	{
		public int sender;
		public long counter;
		public GameObjectData data;
	}

}
