package hu.csega.superstition.game.menu;

public class HostEntry extends MenuElement
{
	protected IMenu menu;
	protected HostData data;
	protected hu.csega.superstition.game.network.NetHost host;
	protected Network.NetworkClient nclient;
	protected bool publish;
	protected IPAddress address;

	public HostEntry(ModelParams param, IMenu menu,
			HostData data, Network.NetHost host,
			IPAddress address, Network.NetworkClient nclient) {
		super(param, "Join Game:" +
				data.player_count.ToString() + "/" +
				data.limit.ToString());
		this.menu = menu;
		this.param = param;
		this.data = data;
		this.host = host;
		this.nclient = nclient;
		this.publish = false;
		this.address = address;
	}

	public HostEntry(ModelParams param, IMenu menu,
			HostData data, Network.NetHost host, IPAddress address,
			Network.NetworkClient nclient, bool publish) {
		super(param, "Publish Game");
		this.menu = menu;
		this.param = param;
		this.data = data;
		this.host = host;
		this.nclient = nclient;
		this.publish = true;
		this.address = address;
	}

	public HostEntry(ModelParams param) {
		super(param, "< empty >");
		this.menu = menu;
		this.param = param;
		this.data = null;
		this.nclient = null;
		this.publish = false;
	}

	@Override
	public IMenu DoItem()
	{
		if(nclient == null) return null;

		GameObjectData map = null;

		if(!publish)
		{
			nclient.SendJoinHost(data.ID);
			map = nclient.PollReceive();
		}
		else
		{
			nclient.SendPublishHost(param.game_model.GetDataModel());
		}

		GameObjectData user_info = nclient.PollReceive();
		nclient.TcpDisconnect();

		if(publish)
		{
			object[] parameters = new object[4];
			parameters[0] = host;
			parameters[1] = address;
			parameters[2] = data;
			parameters[3] = user_info;

			param.engine.State.trigger(
					new TriggerParams(
							MainMenuSelection.PUBLISH_HOST,
							"Publish Host",
							parameters));
		}
		else
		{
			object[] parameters = new object[5];
			parameters[0] = host;
			parameters[1] = address;
			parameters[2] = data;
			parameters[3] = user_info;
			parameters[4] = map;

			param.engine.State.trigger(
					new TriggerParams(
							MainMenuSelection.JOIN_HOST,
							"Join Host",
							parameters));

		}

		return null;

	}

	@Override
	public string getText()
	{
		return "Join / Publish";
	}
}