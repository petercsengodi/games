package hu.csega.superstition.gameserver;

public class ServerControl {
	private Socket socket;
	private Server server;
	private byte[] buffer;
	private BinaryFormatter formatter;
	private int max_receive;
	private int packet_size;

	public ServerControl(Server server, Socket socket, byte[] buffer)
	{
		this.server = server;
		this.socket = socket;
		this.buffer = buffer;

		formatter = new BinaryFormatter();
		max_receive = NetworkOptions.ReceiveLength();
		packet_size = NetworkOptions.PacketLength();
	}

	public Socket Socket
	{
		get { return socket; }
	}

	public GameObjectData Receive()
	{
		GameObjectData ret;
		int result = -1;
		int pos = 0;

		while((pos == 0) || (result > packet_size))
		{
			IAsyncResult res = socket.BeginReceive(buffer,
					pos, packet_size + 1, SocketFlags.None, null, null);
			result = socket.EndReceive(res);
			pos += packet_size;
		}

		if(result == 0)
		{
			ret = null;
		}
		else
		{
			MemoryStream stream = new MemoryStream(buffer);
			ret = (GameObjectData)formatter.Deserialize(stream);
			Console.WriteLine(ret.Description);
			stream.Close();
			Server.Instance.WriteStatus("Incoming packet: " + ret.Description);
		}

		return ret;
	}

	public void Send(Object data)
	{
		MemoryStream stream = new MemoryStream(buffer);
		formatter.Serialize(stream, data);
		int size = (int)stream.Position, pos = 0;
		stream.Close();

		while(true)
		{
			if(size <= pos + packet_size)
			{
				IAsyncResult res = socket.BeginSend(buffer, pos, size - pos, SocketFlags.None, null, null);
				socket.EndSend(res);
				break;
			}
			else
			{
				IAsyncResult res = socket.BeginSend(buffer, pos, packet_size + 1, SocketFlags.None, null, null);
				socket.EndSend(res);
				pos += packet_size;
			}
		}

	}

	public void SendUserInfo(int playerID, int game_port, int client_port)
	{
		UserInfo info = new UserInfo();
		info.game_port = game_port;
		info.client_port = client_port;
		info.userID = playerID;
		Send(info);
	}

	public Player AttachPlayerToHost(Host hst)
	{
		Player ret = hst.AddSocketPlayer(socket);
		return ret;
	}



	public void Dispose()
	{
		if(socket != null) socket.Close();
		socket = null;
	}


}