package hu.csega.superstition.servertester;

import org.joml.Vector3f;

import hu.csega.superstition.gamelib.network.AllPlayerData;
import hu.csega.superstition.gamelib.network.GameObjectData;
import hu.csega.superstition.gamelib.network.HostList;
import hu.csega.superstition.gamelib.network.NetworkPlayerData;

public class Tester extends JPanel {

	private System.Windows.Forms.TextBox hostname;
	private System.Windows.Forms.Label host_label;
	private System.Windows.Forms.Label status_label;
	private System.Windows.Forms.ListBox history_box;
	private System.Windows.Forms.Label history_label;
	private System.Windows.Forms.Button connection_button;
	private System.Windows.Forms.Button disconnection_button;
	private System.Windows.Forms.Label port_label;
	private System.Windows.Forms.NumericUpDown port_num;
	private System.Windows.Forms.ListBox address_box;
	private System.Windows.Forms.Button host_query_button;
	private System.Windows.Forms.ListBox hosts_box;
	private System.Windows.Forms.Button publish_host_button;
	private System.Windows.Forms.Button join_host_button;
	private System.Windows.Forms.Button send_info_button;
	private System.Windows.Forms.Label udpport_label;
	private System.Windows.Forms.NumericUpDown udpport;
	private System.Windows.Forms.Button start_game_button;
	private ServerTester.PlayerView playerView1;
	private System.Windows.Forms.NumericUpDown clientport;

	/// <summary>
	/// Required designer variable.
	/// </summary>
	private System.ComponentModel.Container components = null;

	private static Tester instance;
	public static Tester Instance{ get{ return instance; } }

	private long userID;
	private int main_port, game_port, client_port;
	private String host_string;
	private StateControl state;
	private IPAddress[] list;
	private IPAddress server_address;
	private NetworkClient client;
	private PlayClient net_player;
	private HostList host_list;
	private Vector3f player_position;
	private Vector3f null_vector;
	private Vector3f[] all_data;

	public boolean ReadOnly
	{
		get{ return port_num.ReadOnly; }
		set
		{
			port_num.ReadOnly = value;
			hostname.ReadOnly = value;
		}
	}

	public Tester()
	{
		//
		// Required for Windows Form Designer support
		//
		InitializeComponent();
		playerView1.Initialize(new PlayerViewFunc(MouseFunc));

		client = null;
		host_list = null;
		state = new StateControl();
		main_port = (int)port_num.Value;
		game_port = 0;
		client_port = 0;
		host_string = hostname.Text;

		all_data = null;
		player_position = new Vector3f(0f, 0f, 0f);
		null_vector = new Vector3f(0f, 0f, 0f);

		OnHostNameChanged();
	}

	public boolean TcpConnect()
	{
		boolean ret;
		if(client != null) return false;
		if(address_box.SelectedIndex < 0) return false;
		server_address = list[address_box.SelectedIndex];
		client = new NetworkClient(server_address, main_port);
		ret = client.TcpConnect();
		if(ret) client.StartListenThread(new ReceiveData(ReceiveDataFunction));
		else client = null;
		return ret;
	}

	public void TcpDisconnect()
	{
		if(client == null) return;
		client.TcpDisconnect();
		client = null;
		ChangeHostBox(null);
	}

	public boolean UdpConnect()
	{
		if(net_player != null) return false;
		net_player = new PlayClient(server_address, game_port, client_port, userID);
		boolean ret = net_player.UdpConnect();
		if(ret) net_player.StartListenThread(new ReceiveData(ReceiveUdpFunction));
		else net_player = null;
		return ret;
	}

	public void UdpDisconnect()
	{
		if(net_player == null) return;
		net_player.UdpDisconnect();
		net_player = null;
		game_port = 0;
		client_port = 0;
		udpport_ValueChanged(null, null);
		clientport_ValueChanged(null, null);
	}

	public void WriteStatus(String status)
	{
		status_label.Text = status;
		history_box.Items.Add(status);
	}

	private void OnHostNameChanged()
	{
		address_box.BeginUpdate();
		address_box.Items.Clear();

		try
		{
			IPHostEntry entry = Dns.GetHostByName(host_string);
			list = entry.AddressList;
			for(int i = 0; i < list.Length; i++)
			{
				address_box.Items.Add(list[i].ToString());
			}

		}
		catch (SocketException){
			list = new IPAddress[0];
		}

		if(list.Length > 0) address_box.SelectedIndex = 0;

		address_box.EndUpdate();
		address_box.Invalidate();
	}

	private void ChangeHostBox(HostList list)
	{
		hosts_box.BeginUpdate();
		hosts_box.Items.Clear();

		if(list != null)
		{
			for(int i = 0; i < list.list.Length; i++)
			{
				if(list.list[i].ID == -1) hosts_box.Items.Add("<empty>");
				else
				{
					hosts_box.Items.Add(
							"ID=" + list.list[i].ID + "; " +
									"lim=" + list.list[i].limit + "; " +
									"plyrs=" + list.list[i].player_count);
				}
			}
			if(list.list.Length > 0) hosts_box.SelectedIndex = 0;
			else hosts_box.SelectedIndex = -1;
		} else hosts_box.SelectedIndex = -1;

		hosts_box.EndUpdate();
		hosts_box.Invalidate();
	}

	public void ReceiveDataFunction(GameObjectData data)
	{
		if(data == null) // Disconnect client
		{
			host_list = null;
			ChangeHostBox(null);
			//			state.trigger("disconnect"); // Not thread-safe
			WriteStatus("Server Disconnected");
			return;
		}

		if(data.Description.Equals("Host List"))
		{
			host_list = (HostList)data;
			ChangeHostBox(host_list);
		}

		if(data.Description.Equals("User Info"))
		{
			host_list = null;
			ChangeHostBox(host_list);
			UserInfo info = (UserInfo)data;
			userID = info.userID;
			game_port = info.game_port;
			client_port = info.client_port;
			udpport_ValueChanged(null, null);
			clientport_ValueChanged(null, null);
		}
	}

	public void ReceiveUdpFunction(GameObjectData data)
	{
		if((data == null) || (data.getDescription().equals("Quit Game"))) // Disconnect client
		{
			state.trigger("disconnect");
			return;
		}

		this.WriteStatus("Incoming packet: " + data.getDescription());

		if(data.getDescription().equals("AllNetPlayer"))
		{
			AllPlayerData all_player = (AllPlayerData) data;
			all_data = new Vector3f[all_player.all_data.length];

			for(int i = 0; i < all_player.all_data.length; i++) {
				if(all_player.all_data[i] == null) {
					continue;
				}

				all_data[i] = all_player.all_data[i].position + all_player.all_data[i].difference;
			}

			playerView1.SetPositions(player_position, all_data);
			playerView1.Invalidate();
		}
	}

	protected void OnClosing(CancelEventArgs e) {
		state.trigger("quit");
		super.OnClosing(e);
	}

	/// <summary>
	/// Clean up any resources being used.
	/// </summary>
	protected void Dispose( boolean disposing )
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
		this.hostname = new System.Windows.Forms.TextBox();
		this.host_label = new System.Windows.Forms.Label();
		this.status_label = new System.Windows.Forms.Label();
		this.history_box = new System.Windows.Forms.ListBox();
		this.history_label = new System.Windows.Forms.Label();
		this.connection_button = new System.Windows.Forms.Button();
		this.disconnection_button = new System.Windows.Forms.Button();
		this.port_label = new System.Windows.Forms.Label();
		this.port_num = new System.Windows.Forms.NumericUpDown();
		this.address_box = new System.Windows.Forms.ListBox();
		this.host_query_button = new System.Windows.Forms.Button();
		this.hosts_box = new System.Windows.Forms.ListBox();
		this.publish_host_button = new System.Windows.Forms.Button();
		this.join_host_button = new System.Windows.Forms.Button();
		this.send_info_button = new System.Windows.Forms.Button();
		this.udpport_label = new System.Windows.Forms.Label();
		this.udpport = new System.Windows.Forms.NumericUpDown();
		this.start_game_button = new System.Windows.Forms.Button();
		this.playerView1 = new ServerTester.PlayerView();
		this.clientport = new System.Windows.Forms.NumericUpDown();
		((System.ComponentModel.ISupportInitialize)(this.port_num)).BeginInit();
		((System.ComponentModel.ISupportInitialize)(this.udpport)).BeginInit();
		((System.ComponentModel.ISupportInitialize)(this.clientport)).BeginInit();
		this.SuspendLayout();
		//
		// hostname
		//
		this.hostname.Location = new System.Drawing.Point(152, 16);
		this.hostname.Name = "hostname";
		this.hostname.Size = new System.Drawing.Size(184, 20);
		this.hostname.TabIndex = 0;
		this.hostname.Text = "localhost";
		this.hostname.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.hostname_KeyPress);
		this.hostname.TextChanged += new System.EventHandler(this.hostname_TextChanged);
		this.hostname.Leave += new System.EventHandler(this.hostname_Leave);
		//
		// host_label
		//
		this.host_label.Location = new System.Drawing.Point(24, 16);
		this.host_label.Name = "host_label";
		this.host_label.Size = new System.Drawing.Size(120, 24);
		this.host_label.TabIndex = 1;
		this.host_label.Text = "Host Name";
		this.host_label.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
		//
		// status_label
		//
		this.status_label.Font = new System.Drawing.Font("Monotype Corsiva", 14.25F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((System.Byte)(238)));
		this.status_label.ForeColor = System.Drawing.Color.Red;
		this.status_label.Location = new System.Drawing.Point(8, 384);
		this.status_label.Name = "status_label";
		this.status_label.Size = new System.Drawing.Size(728, 72);
		this.status_label.TabIndex = 2;
		this.status_label.Text = "None";
		//
		// history_box
		//
		this.history_box.Location = new System.Drawing.Point(368, 64);
		this.history_box.Name = "history_box";
		this.history_box.Size = new System.Drawing.Size(368, 303);
		this.history_box.TabIndex = 3;
		//
		// history_label
		//
		this.history_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(238)));
		this.history_label.Location = new System.Drawing.Point(480, 16);
		this.history_label.Name = "history_label";
		this.history_label.Size = new System.Drawing.Size(144, 32);
		this.history_label.TabIndex = 4;
		this.history_label.Text = "History";
		this.history_label.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// connection_button
		//
		this.connection_button.Location = new System.Drawing.Point(16, 80);
		this.connection_button.Name = "connection_button";
		this.connection_button.Size = new System.Drawing.Size(88, 24);
		this.connection_button.TabIndex = 5;
		this.connection_button.Text = "Connect";
		this.connection_button.Click += new System.EventHandler(this.connection_button_Click);
		//
		// disconnection_button
		//
		this.disconnection_button.Location = new System.Drawing.Point(16, 336);
		this.disconnection_button.Name = "disconnection_button";
		this.disconnection_button.Size = new System.Drawing.Size(88, 24);
		this.disconnection_button.TabIndex = 6;
		this.disconnection_button.Text = "Disconnect";
		this.disconnection_button.Click += new System.EventHandler(this.disconnection_button_Click);
		//
		// port_label
		//
		this.port_label.Location = new System.Drawing.Point(24, 40);
		this.port_label.Name = "port_label";
		this.port_label.Size = new System.Drawing.Size(120, 24);
		this.port_label.TabIndex = 7;
		this.port_label.Text = "Main Port";
		this.port_label.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
		//
		// port_num
		//
		this.port_num.Location = new System.Drawing.Point(152, 40);
		this.port_num.Maximum = new System.Decimal(new int[] {
				65535,
				0,
				0,
				0});
		this.port_num.Minimum = new System.Decimal(new int[] {
				5000,
				0,
				0,
				0});
		this.port_num.Name = "port_num";
		this.port_num.Size = new System.Drawing.Size(80, 20);
		this.port_num.TabIndex = 8;
		this.port_num.Value = new System.Decimal(new int[] {
				10555,
				0,
				0,
				0});
		this.port_num.ValueChanged += new System.EventHandler(this.port_num_ValueChanged);
		//
		// address_box
		//
		this.address_box.Location = new System.Drawing.Point(112, 80);
		this.address_box.Name = "address_box";
		this.address_box.Size = new System.Drawing.Size(224, 69);
		this.address_box.TabIndex = 9;
		//
		// host_query_button
		//
		this.host_query_button.Location = new System.Drawing.Point(16, 120);
		this.host_query_button.Name = "host_query_button";
		this.host_query_button.Size = new System.Drawing.Size(88, 23);
		this.host_query_button.TabIndex = 10;
		this.host_query_button.Text = "Host Query";
		this.host_query_button.Click += new System.EventHandler(this.host_query_button_Click);
		//
		// hosts_box
		//
		this.hosts_box.Location = new System.Drawing.Point(112, 160);
		this.hosts_box.Name = "hosts_box";
		this.hosts_box.Size = new System.Drawing.Size(224, 69);
		this.hosts_box.TabIndex = 11;
		//
		// publish_host_button
		//
		this.publish_host_button.Location = new System.Drawing.Point(16, 160);
		this.publish_host_button.Name = "publish_host_button";
		this.publish_host_button.Size = new System.Drawing.Size(88, 24);
		this.publish_host_button.TabIndex = 12;
		this.publish_host_button.Text = "Publish Host";
		this.publish_host_button.Click += new System.EventHandler(this.publish_host_button_Click);
		//
		// join_host_button
		//
		this.join_host_button.Location = new System.Drawing.Point(16, 200);
		this.join_host_button.Name = "join_host_button";
		this.join_host_button.Size = new System.Drawing.Size(88, 24);
		this.join_host_button.TabIndex = 13;
		this.join_host_button.Text = "Join Host";
		this.join_host_button.Click += new System.EventHandler(this.join_host_button_Click);
		//
		// send_info_button
		//
		this.send_info_button.Location = new System.Drawing.Point(16, 296);
		this.send_info_button.Name = "send_info_button";
		this.send_info_button.Size = new System.Drawing.Size(88, 23);
		this.send_info_button.TabIndex = 14;
		this.send_info_button.Text = "Send Info";
		this.send_info_button.Click += new System.EventHandler(this.send_info_button_Click);
		//
		// udpport_label
		//
		this.udpport_label.Location = new System.Drawing.Point(264, 248);
		this.udpport_label.Name = "udpport_label";
		this.udpport_label.Size = new System.Drawing.Size(72, 24);
		this.udpport_label.TabIndex = 17;
		this.udpport_label.Text = "UDP Port:";
		this.udpport_label.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
		//
		// udpport
		//
		this.udpport.Location = new System.Drawing.Point(256, 280);
		this.udpport.Maximum = new System.Decimal(new int[] {
				64000,
				0,
				0,
				0});
		this.udpport.Name = "udpport";
		this.udpport.ReadOnly = true;
		this.udpport.Size = new System.Drawing.Size(80, 20);
		this.udpport.TabIndex = 18;
		this.udpport.ValueChanged += new System.EventHandler(this.udpport_ValueChanged);
		//
		// start_game_button
		//
		this.start_game_button.Location = new System.Drawing.Point(16, 248);
		this.start_game_button.Name = "start_game_button";
		this.start_game_button.Size = new System.Drawing.Size(88, 24);
		this.start_game_button.TabIndex = 19;
		this.start_game_button.Text = "Start Game";
		this.start_game_button.Click += new System.EventHandler(this.start_game_button_Click);
		//
		// playerView1
		//
		this.playerView1.Location = new System.Drawing.Point(120, 248);
		this.playerView1.Name = "playerView1";
		this.playerView1.Size = new System.Drawing.Size(128, 112);
		this.playerView1.TabIndex = 20;
		//
		// clientport
		//
		this.clientport.Location = new System.Drawing.Point(256, 312);
		this.clientport.Maximum = new System.Decimal(new int[] {
				64000,
				0,
				0,
				0});
		this.clientport.Name = "clientport";
		this.clientport.ReadOnly = true;
		this.clientport.Size = new System.Drawing.Size(80, 20);
		this.clientport.TabIndex = 21;
		this.clientport.ValueChanged += new System.EventHandler(this.clientport_ValueChanged);
		//
		// Tester
		//
		this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
		this.ClientSize = new System.Drawing.Size(744, 462);
		this.Controls.Add(this.clientport);
		this.Controls.Add(this.playerView1);
		this.Controls.Add(this.start_game_button);
		this.Controls.Add(this.udpport);
		this.Controls.Add(this.udpport_label);
		this.Controls.Add(this.send_info_button);
		this.Controls.Add(this.join_host_button);
		this.Controls.Add(this.publish_host_button);
		this.Controls.Add(this.hosts_box);
		this.Controls.Add(this.host_query_button);
		this.Controls.Add(this.address_box);
		this.Controls.Add(this.port_num);
		this.Controls.Add(this.port_label);
		this.Controls.Add(this.disconnection_button);
		this.Controls.Add(this.connection_button);
		this.Controls.Add(this.history_label);
		this.Controls.Add(this.history_box);
		this.Controls.Add(this.status_label);
		this.Controls.Add(this.host_label);
		this.Controls.Add(this.hostname);
		this.Name = "Tester";
		this.Text = "Tester Program";
		this.Load += new System.EventHandler(this.Tester_Load);
		((System.ComponentModel.ISupportInitialize)(this.port_num)).EndInit();
		((System.ComponentModel.ISupportInitialize)(this.udpport)).EndInit();
		((System.ComponentModel.ISupportInitialize)(this.clientport)).EndInit();
		this.ResumeLayout(false);

	}

	public static void main(String[] args) {
		instance = new Tester();
		// TODO csega: run
	}

	private void MouseFunc(float x, float y) {
		NetworkPlayerData data = new NetworkPlayerData();
		data.position = player_position;
		data.difference = new Vector3f(x, y, 0f);

		player_position.X += x;
		player_position.Y += y;

		if(net_player != null) net_player.Send(data);
		playerView1.SetPositions(player_position, all_data);
		playerView1.Invalidate();
	}

	private void hostname_TextChanged(Object sender, System.EventArgs e)
	{
		if(ReadOnly) hostname.Text = host_string;
		else
		{
			host_string = hostname.Text;
		}
	}

	private void port_num_ValueChanged(Object sender, System.EventArgs e)
	{
		if(ReadOnly) port_num.Value = main_port;
		else main_port = (int) port_num.Value;
	}

	private void connection_button_Click(Object sender, System.EventArgs e)
	{
		state.trigger("connect");
	}

	private void disconnection_button_Click(Object sender, System.EventArgs e)
	{
		state.trigger("disconnect");
	}

	private void hostname_Leave(Object sender, System.EventArgs e)
	{
		OnHostNameChanged();
	}

	private void hostname_KeyPress(Object sender, System.Windows.Forms.KeyPressEventArgs e)
	{
		if(e.KeyChar == (char)Keys.Enter)
		{
			OnHostNameChanged();
			address_box.Focus();
		}
	}

	private void host_query_button_Click(Object sender, System.EventArgs e)
	{
		if(client != null) client.SendHostQuery();
	}

	private void publish_host_button_Click(Object sender, System.EventArgs e)
	{
		if(client == null) return;
		client.SendPublishHost(new TestMapObject());
	}

	private void join_host_button_Click(Object sender, System.EventArgs e)
	{
		if(client == null) return;
		if(host_list == null) return;
		if(hosts_box.SelectedIndex < 0) return;
		int idx = hosts_box.SelectedIndex;
		int hostID = host_list.list[idx].ID;
		client.SendJoinHost(hostID);
	}

	private void udpport_ValueChanged(Object sender, System.EventArgs e)
	{
		udpport.Value = game_port;
	}

	private void start_game_button_Click(Object sender, System.EventArgs e)
	{
		if(game_port > 0) state.trigger("game");
	}

	private void Tester_Load(Object sender, System.EventArgs e)
	{

	}

	private void clientport_ValueChanged(Object sender, System.EventArgs e)
	{
		clientport.Value = client_port;
	}

	private void send_info_button_Click(Object sender, System.EventArgs e)
	{
		NetworkPlayerData data = new NetworkPlayerData();
		data.position = player_position;
		data.difference = new Vector3f(0f, 0f, 0f);
		if(net_player != null) net_player.Send(data);
		playerView1.SetPositions(player_position, all_data);
		playerView1.Invalidate();
	}

} // End of class Tester