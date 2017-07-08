package hu.csega.superstition.unported.game;

import org.joml.Vector3f;

import hu.csega.superstition.gamelib.network.GameObjectData;

public class PlayClient {

	private Socket socket;
	private IPAddress host;
	private IPEndPoint server;
	private int game_port, client_port;
	private long userID;
	private long counter, last_counter;

	private boolean alive;
	private Thread thread;
	private ReceiveData recv_func;
	private int timeval;

	private byte[] buffer;
	private BinaryFormatter formatter;

	public PlayClient(IPAddress host, int game_port, int client_port, long userID)
	{
		this.host = host;
		this.userID = userID;
		this.game_port = game_port;
		this.client_port = client_port;
		this.alive = false;
		this.thread = null;
		this.recv_func = null;
		this.timeval = NetworkOptions.TimeVal();

		this.buffer = new byte[NetworkOptions.PacketLength()];
		this.formatter = new BinaryFormatter();
		this.server = new IPEndPoint(host, game_port);
		this.last_counter = -1;
		this.counter = 0;
	}

	public boolean UdpConnect()
	{
		socket = new Socket(AddressFamily.InterNetwork,
				SocketType.Dgram, ProtocolType.Udp);
		socket.Bind(new IPEndPoint(IPAddress.Any, client_port));
		socket.Blocking = false;

		return true;
	}

	public void UdpDisconnect()
	{
		StopListenThread();
		QuitGame();
		socket.Close();
		socket = null;
	}

	public void Send(GameObjectData data)
	{
		Packet packet = new Packet();
		packet.data = data;
		packet.sender = (int)userID;
		packet.counter = counter++;
		MemoryStream stream = new MemoryStream(buffer);

		try
		{
			formatter.Serialize(stream, packet);
			int size = (int)stream.Position;
			socket.SendTo(buffer, size, SocketFlags.None, server);
		}
		catch (Exception e)
		{
			Console.WriteLine(e.ToString());
		}
		finally
		{
			stream.Close();
		}

	}

	public GameObjectData Receive()
	{
		GameObjectData ret = null;
		EndPoint point = new IPEndPoint(IPAddress.Any, game_port);
		int result = socket.ReceiveFrom(buffer, ref point);
		switch(result)
		{
		case 0:
			ret = null;
			break;

		default:
			MemoryStream stream = new MemoryStream(buffer);
			try
			{
				Packet packet = (Packet)formatter.Deserialize(stream);
				if(packet.sender != -1) break;
				if(packet.counter < last_counter) break;
				last_counter = packet.counter;
				ret = packet.data;
			}
			catch(Exception e)
			{
				Console.WriteLine(result + ":" + e.Message);
				ret = null;
			}
			finally
			{
				stream.Close();
			}
			break;
		}

		return ret;
	}

	public void SendNetworkPlayer(Vector3f position, Vector3f difference)
	{
		NetworkPlayerData data = new NetworkPlayerData();
		data.position = position;
		data.difference = difference;
		Send(data);
	}

	public void QuitGame()
	{
		GameObjectData data = new GameObjectData("Quit Game");
		Send(data);
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

} // End of class PlayClient