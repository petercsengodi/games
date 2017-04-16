package hu.csega.superstition.gameserver;

import org.joml.Vector3f;

public class Player {
	private static float PUSHAWAY = 0.1f;
	private static float MINDISTANCE = 0.5f;

	private int playerID;
	private int client_port;
	private boolean active;

	public int PlayerID{ get{ return playerID; } }
	public int ClientPort{ get{ return client_port; } }
	public boolean Active{ get{ return active; } }
	public void SetActive(){ active = true; }

	private Socket socket;
	private IPEndPoint end_point;
	private long counter, last_counter;
	private byte[] buffer;
	private BinaryFormatter formatter;

	private Vector3f position;
	private Vector3f difference;
	private Vector3f push_away;

	public Vector3f Position { get { return position + difference; } }

	public Vector3f PushAway
	{
		get{ return push_away; }
		set{ push_away = value; }
	}

	public Player(Socket socket, IPAddress address, int playerID, int client_port)
	{
		this.counter = 0;
		this.active = false;
		this.last_counter = -1;
		this.client_port = client_port;
		this.socket = socket;
		this.playerID = playerID;
		this.end_point = new IPEndPoint(address, client_port);
		this.buffer = new byte[NetworkOptions.PacketLength()];
		this.formatter = new BinaryFormatter();
	}

	public GameObjectData GetPlayerData()
	{
		NetworkPlayerData data = new NetworkPlayerData();
		data.position = position;
		data.difference = difference + push_away;
		return data;
	}

	public void SetPlayerData(NetworkPlayerData data)
	{
		position = data.position;
		difference = data.difference;
	}

	public void Send(GameObjectData data)
	{
		if(!active) return;

		Packet packet = new Packet();
		packet.sender = -1; // Sender == Server
		packet.counter = counter++;
		packet.data = data;
		MemoryStream stream = new MemoryStream(buffer);
		try
		{
			formatter.Serialize(stream, packet);
			int size = (int)stream.Position;
			socket.SendTo(buffer, size, SocketFlags.None, end_point);
		}
		catch(Exception e)
		{
			Console.WriteLine(e.Message);
		}
		finally
		{
			stream.Close();
		}
	}

	public void Disconnect()
	{
		Send(new GameObjectData("Quit Game"));
	}

	public boolean CheckCounter(long counter)
	{
		if(counter > last_counter)
		{
			last_counter = counter;
			return true;
		} else return false;
	}



	public void dispose()
	{
		Disconnect();
	}



	/// <summary>
	///
	/// </summary>
	/// <param name="players"></param>
	public static void CalculatePushAway(Player[] players)
	{
		float length;
		Vector3f sub;
		Vector3f zero = new Vector3f(0f, 0f, 0f);
		Vector3f[] push_away = new Vector3f[players.Length];

		for(int i = 0; i < players.Length; i++)
		{
			if(players[i] == null) continue;
			push_away[i] = new Vector3f(0f,0f,0f);
		}

		for(int i = 0; i < players.Length; i++)
		{
			if(players[i] == null) continue;
			for(int j = i + 1; j < players.Length; j++)
			{
				if(players[j] == null) continue;
				sub = (players[j].Position - players[i].Position);
				length = sub.Length();

				if(length < MINDISTANCE)
				{
					if(length == 0f) sub = new Vector3f(0f, 1f, 0f);
					else
					{
						sub.Normalize();
						sub = sub * PUSHAWAY;
					}

					push_away[i] -= sub;
					push_away[j] += sub;
				}
			}
		}

		AllPlayerData data = new AllPlayerData();
		data.all_data = new NetworkPlayerData[players.Length];
		for(int i = 0; i < players.Length; i++)
		{
			if(players[i] == null)
			{
				data.all_data[i] = null;
			}
			else
			{
				players[i].PushAway = push_away[i];
				data.all_data[i] = (NetworkPlayerData)players[i].GetPlayerData();
			}
		}

		for(int i = 0; i < players.Length; i++)
		{
			if(players[i] == null) continue;
			players[i].Send(data);
		}

	} // End of function Calculate Push Away

} // End of class Player