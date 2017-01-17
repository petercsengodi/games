package hu.csega.superstition.servertester;

public class NetworkClient
{
	private Socket socket;
	private IPAddress host;
	private int main_port;
	private int packet_size;

	private bool alive;
	private Thread thread;
	private ReceiveData recv_func;
	private int timeval;

	private byte[] buffer;
	private BinaryFormatter formatter;

	public NetworkClient(IPAddress host, int main_port)
	{
		this.host = host;
		this.main_port = main_port;
		this.alive = false;
		this.thread = null;
		this.recv_func = null;
		this.timeval = NetworkOptions.TimeVal();

		this.buffer = new byte[NetworkOptions.ReceiveLength()];
		this.formatter = new BinaryFormatter();
		this.packet_size = NetworkOptions.PacketLength();
	}

	public bool TcpConnect()
	{
		socket = new Socket(AddressFamily.InterNetwork,
			SocketType.Stream, ProtocolType.Tcp);
		socket.Blocking = false;
		EndPoint point = new IPEndPoint(host, main_port);
		try
		{
			// socket.Connect(point);
			System.IAsyncResult res;
			res = socket.BeginConnect(point, null, null);
			socket.EndConnect(res);

		}
		catch (Exception e)
		{
			Console.WriteLine(e.Message);
			Tester.Instance.WriteStatus(e.Message);
			socket.Close();
			return false;
		}

		Tester.Instance.WriteStatus("TCP Connection Established");
		return true;
	}

	public void TcpDisconnect()
	{
		StopListenThread();
		socket.Close();
		socket = null;
		Tester.Instance.WriteStatus("TCP Disconnect");
	}

	public void Send(object data)
	{
		MemoryStream stream = new MemoryStream(buffer);
		formatter.Serialize(stream, data);
		int size = (int)stream.Position, pos = 0;
		stream.Close();

		while(true)
		{
			if(size <= pos + packet_size)
			{
				socket.Send(buffer, pos, size - pos, SocketFlags.None);
				break;
			}
			else
			{
				socket.Send(buffer, pos, packet_size + 1, SocketFlags.None);
				pos += packet_size;
			}
		}

//		IAsyncResult res =  socket.BeginSend(buffer, 0,
//			NetworkOptions.ReceiveLength(), SocketFlags.None,
//			null, null);
//		socket.EndReceive(res);
	}

	public GameObjectData Receive()
	{
		GameObjectData ret;
//		int result = socket.Receive(buffer);
		int result = -1, pos = 0;

		while((pos == 0) || (result > packet_size))
		{
			IAsyncResult res = socket.BeginReceive(buffer,
				pos, packet_size + 1, SocketFlags.None, null, null);
			result = socket.EndReceive(res);
//			result = socket.Receive(buffer, pos, packet_size + 1, SocketFlags.None);
			pos += packet_size;
		}

		switch(result)
		{
			case 0:
				ret = null;
				alive = false;
				break;

			default:
				MemoryStream stream = new MemoryStream(buffer);
				ret = (GameObjectData)formatter.Deserialize(stream);
				stream.Close();
				Tester.Instance.WriteStatus(
					"Incoming packet: " + ret.Description);
				break;
		}

		return ret;
	}

	public void SendHostQuery()
	{
		GameObjectData data = new GameObjectData("query:hosts");
		Send(data);
	}

	public void SendPublishHost(GameObjectData map)
	{
		PublishHost ph = new PublishHost();

		MemoryStream stream = new MemoryStream(buffer);
		formatter.Serialize(stream, map);
		int map_size = (int)stream.Position;
		stream.Close();

		ph.map = new MapBuffer();
		ph.map.map_buffer = new byte[map_size];
		for(int i = 0; i < map_size; i++)
		{
			ph.map.map_buffer[i] = buffer[i];
		}
		ph.max_players = 8;

		Send(ph);
	}

	public void SendJoinHost(int hostID)
	{
		JoinHost jh = new JoinHost();
		jh.HostID = hostID;
		Send(jh);
	}

	public void StartListenThread(ReceiveData recv_func)
	{
		if(alive) return;
		alive = true;
		this.recv_func = recv_func;
		thread = new Thread(new ThreadStart(Run));
		thread.Start();
	}

	public void StopListenThread()
	{
		if(!alive) return;
		alive = false;
		thread.Join();
		thread = null;
		recv_func = null;
	}

	private void Run()
	{
		while(alive)
		{
			if(socket.Poll(timeval, SelectMode.SelectRead))
			{
				recv_func(Receive());
			}
		}
	}

} // End of class NetworkClient