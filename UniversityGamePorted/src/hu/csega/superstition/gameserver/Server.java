package hu.csega.superstition.gameserver;

public class Server extends System.Windows.Forms.Form
{
	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;
	private System.Windows.Forms.Label port_label;
	private System.Windows.Forms.NumericUpDown numericUpDown1;
	private System.Windows.Forms.Button start_button;
	private System.Windows.Forms.Button stop_button;
	private System.Windows.Forms.Label status_label;
	private System.Windows.Forms.ListBox info_box;
	private System.Windows.Forms.Label info_label;
	private System.Windows.Forms.Label hosts_label;
	private System.Windows.Forms.ListBox host_box;
	private System.Windows.Forms.Label controls_label;
	private System.Windows.Forms.ListBox control_box;

	private static final string
	ServerIsRunning = "Server is Running";

	private int timeval;
	private static Thread thread;
	private int main_port;
	private StateControl state;
	private static Server instance;
	public static Server Instance{ get{ return instance; } }
	private static int MAX_HOSTS = 5;
	private HostData empty_host;

	private Socket socket, temp;
	private ServerControl control;
	private Host[] hosts;
	private ArrayList controls, rmlist;
	private IList list_read;
	private boolean alive;
	private int idx;
	private byte[] receive_buffer;

	public Server()
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();

		alive = true;
		hosts = new Host[MAX_HOSTS];
		controls = new ArrayList();
		rmlist = new ArrayList();
		state = new StateControl();
		main_port = (int)numericUpDown1.Value;
		timeval = NetworkOptions.TimeVal();
		list_read = new ArrayList();
		receive_buffer = new byte[NetworkOptions.ReceiveLength()];

		empty_host = new HostData();
		empty_host.ID = -1;
		empty_host.information = "<empty slot>";
		empty_host.limit = 0;
		empty_host.player_count = 0;
	}

	public void Start()
	{
		if(thread != null) return;
		alive = true;
		thread = new Thread(new ThreadStart(Run));

		socket = new Socket(AddressFamily.InterNetwork,
				SocketType.Stream, ProtocolType.Tcp);
		socket.Bind(new IPEndPoint(IPAddress.Any, main_port));
		socket.Blocking = false;
		socket.Listen(10);

		thread.Start();
		status_label.Text = "Server Started";
		info_box.Items.Add(ServerIsRunning);
	}

	public void Stop()
	{
		if(thread == null) return;
		alive = false;
		thread.Join();
		thread = null;

		for(int i = 0; i < hosts.Length; i++)
		{
			if(hosts[i] != null)
			{
				hosts[i].Dispose();
				hosts[i] = null;
			}
		}

		for(ServerControl ctrl : controls)
		{
			ctrl.Dispose();
		}
		controls.Clear();

		socket.Close();
		info_box.Items.Remove(ServerIsRunning);
		status_label.Text = "Server Stopped";

		OnHostsChanged();
		OnControlsChanged();
	}

	public void RemoveHost(Host hst)
	{
		hst.Dispose();
		hosts[hst.HostData.ID] = null;
		OnHostsChanged();
	}

	public void RemoveServerControl(ServerControl ctrl)
	{
		ctrl.Dispose();
		controls.Remove(ctrl);
		OnControlsChanged();
	}

	public void WriteStatus(String information)
	{
		status_label.Text = (string)information.Clone();
	}

	protected void Run()
	{
		while(alive)
		{
			try
			{
				temp = socket.Accept();
				if(temp != null)
				{
					control = new ServerControl(this, temp, receive_buffer);
					controls.Add(control);
					OnControlsChanged();
				}
			}
			catch(Exception){}

			// Listening Server Control Connections

			list_read.Clear();
			for(int i = 0; i < controls.Count; i++)
			{
				list_read.Add((controls[i] as ServerControl).Socket);
			}

			if(list_read.Count > 0)
			{
				Socket.Select(list_read, null, null, timeval);
				for(ServerControl ctrl : controls)
				{
					idx = list_read.IndexOf(ctrl.Socket);
					if(idx < 0) continue;
					ProcessControlCommand(ctrl, ctrl.Receive());
				}
				for(ServerControl ctrl : rmlist) RemoveServerControl(ctrl);
				rmlist.Clear();
			}

			// Checking empty hosts

			for(int i = 0; i < hosts.Length; i++)
			{
				if((hosts[i] == null) || hosts[i].Alive) continue;
				hosts[i].Dispose();
				hosts[i] = null;
				OnHostsChanged();
			}

		} // End of While
	}

	public void ProcessControlCommand(ServerControl ctrl, GameObjectData data)
	{
		if(data == null)
		{
			rmlist.Add(ctrl);
			return;
		}

		if(data.Description.Equals("query:hosts"))
		{
			HostList list = new HostList();
			list.list = new HostData[hosts.Length];
			for(int i = 0; i < hosts.Length; i++)
			{
				if(hosts[i] == null)
				{
					list.list[i] = empty_host;
				}
				else
				{
					list.list[i] = (hosts[i] as Host).HostData;
				}
			}

			ctrl.Send(list);
		}
		else if(data.Description.Equals("Publish Host"))
		{
			PublishHost ph = (PublishHost)data;

			int id = -1;
			for(int i = 0; i < hosts.Length; i++)
			{
				if(hosts[i] == null)
				{
					id = i;
					break;
				}
			}

			if(id < 0) return; // TODO: Exception
			hosts[id] = Host.StartNewHost(
					NetworkOptions.GenerateHostPort(main_port, id),
					ph.Description,
					id,
					ph.max_players,
					ph.map);

			Player result = ctrl.AttachPlayerToHost(hosts[id]);
			if(result != null)
			{
				//				ctrl.Send(hosts[id].Map); // Not needed

				ctrl.SendUserInfo(
						result.PlayerID,
						hosts[id].GamePort,
						result.ClientPort);

				rmlist.Add(ctrl);
				OnControlsChanged();
			}
			OnHostsChanged();
		}
		else if(data.Description.Equals("Join Host"))
		{
			JoinHost jh = (JoinHost)data;
			int id = jh.HostID;
			if(id < 0) return;
			Player result = ctrl.AttachPlayerToHost(hosts[id]);
			if(result != null)
			{
				ctrl.Send(hosts[id].Map);

				ctrl.SendUserInfo(
						result.PlayerID,
						hosts[id].GamePort,
						result.ClientPort);

				rmlist.Add(ctrl);
				OnControlsChanged();
				OnHostsChanged();
			}
		}
	}

	public void OnControlsChanged()
	{
		control_box.BeginUpdate();

		control_box.Items.Clear();

		for(ServerControl ctrl : controls)
		{
			control_box.Items.Add(ctrl.ToString());
		}

		control_box.EndUpdate();
		control_box.Invalidate();
	}

	public void OnHostsChanged()
	{
		host_box.BeginUpdate();

		host_box.Items.Clear();

		String temp;
		HostData data;
		for(int i = 0; i < hosts.Length; i++){
			if(hosts[i] == null) continue;
			data = hosts[i].HostData;

			temp =
					"ID=" + data.ID + "; " +
							"lim=" + data.limit + "; " +
							"plyrs=" + data.player_count + "; ";


			host_box.Items.Add(temp);
		}

		host_box.EndUpdate();
		host_box.Invalidate();
	}

	public boolean ReadOnly
	{
		get { return numericUpDown1.ReadOnly; }
		set
		{
			numericUpDown1.ReadOnly = value;
		}
	}

	protected void OnClosing(CancelEventArgs e)
	{
		super.OnClosing(e);
		state.trigger("quit");
	}


	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected void dispose( boolean disposing )
	{
		if( disposing )
		{
			if (components != null)
			{
				components.Dispose();
			}
		}
		super.Dispose( disposing );
	}


	/// <summary>
	/// Required method for Designer support - do not modify
	/// the contents of this method with the code editor.
	/// </summary>
	private void InitializeComponent()
	{
		this.port_label = new System.Windows.Forms.Label();
		this.numericUpDown1 = new System.Windows.Forms.NumericUpDown();
		this.start_button = new System.Windows.Forms.Button();
		this.stop_button = new System.Windows.Forms.Button();
		this.status_label = new System.Windows.Forms.Label();
		this.info_box = new System.Windows.Forms.ListBox();
		this.info_label = new System.Windows.Forms.Label();
		this.hosts_label = new System.Windows.Forms.Label();
		this.host_box = new System.Windows.Forms.ListBox();
		this.controls_label = new System.Windows.Forms.Label();
		this.control_box = new System.Windows.Forms.ListBox();
		((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).BeginInit();
		this.SuspendLayout();
		//
		// port_label
		//
		this.port_label.Location = new System.Drawing.Point(8, 16);
		this.port_label.Name = "port_label";
		this.port_label.Size = new System.Drawing.Size(72, 24);
		this.port_label.TabIndex = 0;
		this.port_label.Text = "Main Port";
		this.port_label.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
		//
		// numericUpDown1
		//
		this.numericUpDown1.Location = new System.Drawing.Point(88, 16);
		this.numericUpDown1.Maximum = new System.Decimal(new int[] {
				65535,
				0,
				0,
				0});
		this.numericUpDown1.Minimum = new System.Decimal(new int[] {
				5000,
				0,
				0,
				0});
		this.numericUpDown1.Name = "numericUpDown1";
		this.numericUpDown1.Size = new System.Drawing.Size(96, 20);
		this.numericUpDown1.TabIndex = 2;
		this.numericUpDown1.Value = new System.Decimal(new int[] {
				10555,
				0,
				0,
				0});
		this.numericUpDown1.ValueChanged += new System.EventHandler(this.numericUpDown1_ValueChanged);
		//
		// start_button
		//
		this.start_button.Location = new System.Drawing.Point(8, 48);
		this.start_button.Name = "start_button";
		this.start_button.TabIndex = 3;
		this.start_button.Text = "Start Server";
		this.start_button.Click += new System.EventHandler(this.start_button_Click);
		//
		// stop_button
		//
		this.stop_button.Location = new System.Drawing.Point(8, 80);
		this.stop_button.Name = "stop_button";
		this.stop_button.TabIndex = 4;
		this.stop_button.Text = "Stop Server";
		this.stop_button.Click += new System.EventHandler(this.stop_button_Click);
		//
		// status_label
		//
		this.status_label.Font = new System.Drawing.Font("Lucida Console", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(238)));
		this.status_label.ForeColor = System.Drawing.Color.Green;
		this.status_label.Location = new System.Drawing.Point(8, 320);
		this.status_label.Name = "status_label";
		this.status_label.Size = new System.Drawing.Size(584, 56);
		this.status_label.TabIndex = 5;
		this.status_label.Text = "Server Program Opened";
		//
		// info_box
		//
		this.info_box.Location = new System.Drawing.Point(464, 48);
		this.info_box.Name = "info_box";
		this.info_box.Size = new System.Drawing.Size(128, 264);
		this.info_box.TabIndex = 6;
		//
		// info_label
		//
		this.info_label.Font = new System.Drawing.Font("Arial", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(238)));
		this.info_label.Location = new System.Drawing.Point(464, 8);
		this.info_label.Name = "info_label";
		this.info_label.Size = new System.Drawing.Size(128, 32);
		this.info_label.TabIndex = 7;
		this.info_label.Text = "Status Information";
		this.info_label.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// hosts_label
		//
		this.hosts_label.Font = new System.Drawing.Font("Arial", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(238)));
		this.hosts_label.Location = new System.Drawing.Point(328, 8);
		this.hosts_label.Name = "hosts_label";
		this.hosts_label.Size = new System.Drawing.Size(128, 32);
		this.hosts_label.TabIndex = 9;
		this.hosts_label.Text = "Hosts";
		this.hosts_label.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// host_box
		//
		this.host_box.Location = new System.Drawing.Point(328, 48);
		this.host_box.Name = "host_box";
		this.host_box.Size = new System.Drawing.Size(128, 264);
		this.host_box.TabIndex = 8;
		//
		// controls_label
		//
		this.controls_label.Font = new System.Drawing.Font("Arial", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(238)));
		this.controls_label.Location = new System.Drawing.Point(192, 8);
		this.controls_label.Name = "controls_label";
		this.controls_label.Size = new System.Drawing.Size(128, 32);
		this.controls_label.TabIndex = 11;
		this.controls_label.Text = "Controls";
		this.controls_label.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// control_box
		//
		this.control_box.Location = new System.Drawing.Point(192, 48);
		this.control_box.Name = "control_box";
		this.control_box.Size = new System.Drawing.Size(128, 264);
		this.control_box.TabIndex = 10;
		//
		// Server
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(600, 382);
		this.Controls.Add(this.controls_label);
		this.Controls.Add(this.control_box);
		this.Controls.Add(this.hosts_label);
		this.Controls.Add(this.host_box);
		this.Controls.Add(this.info_label);
		this.Controls.Add(this.info_box);
		this.Controls.Add(this.status_label);
		this.Controls.Add(this.stop_button);
		this.Controls.Add(this.start_button);
		this.Controls.Add(this.numericUpDown1);
		this.Controls.Add(this.port_label);
		this.Name = "Server";
		this.Text = "Server Program for Superstition game";
		((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).EndInit();
		this.ResumeLayout(false);

	}


	/// <summary>
	/// The main entry point for the application.
	/// </summary>
	[STAThread]
			static void Main()
	{
		instance = new Server();
		Application.Run(instance);
	}

	private void numericUpDown1_ValueChanged(Object sender, System.EventArgs e)
	{
		if(numericUpDown1.ReadOnly)
		{
			numericUpDown1.Value = main_port;
		} else main_port = (int)numericUpDown1.Value;
	}

	private void start_button_Click(Object sender, System.EventArgs e)
	{
		state.trigger("start");
	}

	private void stop_button_Click(Object sender, System.EventArgs e)
	{
		state.trigger("stop");
	}
}
