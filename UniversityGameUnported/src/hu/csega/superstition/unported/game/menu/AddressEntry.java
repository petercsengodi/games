package hu.csega.superstition.game.menu;

public class AddressEntry extends MenuElement
{
	protected IMenu parent;
	protected IPAddress address;
	protected hu.csega.superstition.game.network.NetHost host;

	public AddressEntry(ModelParams param, IMenu parent,
			IPAddress address, Network.NetHost host) {
		super(param, address.ToString());
		this.parent = parent;
		this.host = host;
		this.param = param;
		this.address = address;
	}

	@Override
	public IMenu DoItem()
	{
		Network.NetworkClient nclient = new Network.NetworkClient(
				address, host.Port);
		return new HostMenu(param, parent, nclient, host, address);
	}

	@Override
	public String getText()
	{
		return address.ToString();
	}
}