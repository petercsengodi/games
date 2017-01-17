package hu.csega.superstition.game.menu;

class HostMenu : IMenu
{
	protected ModelParams param;
	protected IMenu parent;
	protected Network.NetworkClient nclient;
	protected MenuElement[] elements;
	protected MenuHelpClass menuhelp;
	protected HostList host_list;
	protected hu.csega.superstition.game.network.NetHost host;
	protected IPAddress address;

	public HostMenu(ModelParams param, IMenu parent,
		Network.NetworkClient nclient, Network.NetHost host,
		IPAddress address)
	{
		this.host = host;
		this.param = param;
		this.parent = parent;
		this.nclient = nclient;
		this.address = address;

		nclient.TcpConnect();

		nclient.SendHostQuery();
		GameLib.GameObjectData data = nclient.PollReceive();

		if(data.Description.Equals("Host List"))
		{
			host_list = data as HostList;
			elements = new MenuElement[host_list.list.Length];
			for(int i = 0; i < elements.Length; i++)
			{

				if(host_list.list[i].ID == -1)
				{
					if(param.game_menu)
						elements[i] = new HostEntry(param, parent,
							host_list.list[i], host, address,
							nclient, true);

					else elements[i] = new HostEntry(param);
				}
				else
				{
					elements[i] = new HostEntry(param, parent,
						host_list.list[i], host, address,
						nclient);
				}
			}

		}


		menuhelp = new MenuHelpClass(param, elements);
	}

	#region IMenu Members

	public MenuElement[] getMenuElements()
	{
		return elements;
	}

	public IMenu getParent()
	{
		return parent;
	}

	public void RenderElements()
	{
		menuhelp.RenderElements();
	}

	public IMenu DoEscape()
	{
		nclient.TcpDisconnect();
		return parent;
	}

	public void setLastIndex(int idx)
	{
	}

	public int getLastIndex()
	{
		return 0;
	}

	#endregion

	#region IDisposable Members

	public void Dispose()
	{
		menuhelp.Dispose();
		// TODO:  Add HostEntry.Dispose implementation
	}

	#endregion
}