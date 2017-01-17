package hu.csega.superstition.game.menu;

class AddressList : IMenu
{
	protected ModelParams param;
	protected IMenu parent;
	protected IPAddress[] list;
	protected hu.csega.superstition.game.network.NetHost host;
	protected MenuElement[] elements;
	protected MenuHelpClass helpclass;

	public AddressList(ModelParams param, IMenu parent, Network.NetHost host)
	{
		this.param = param;
		this.parent = parent;
		this.host = host;
		this.list = host.GetHost();

		this.elements = new MenuElement[list.Length];
		for(int i = 0; i < list.Length; i++)
		{
			elements[i] = new AddressEntry(param, parent, list[i], host);
		}

		this.helpclass = new MenuHelpClass(param, elements);
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
		helpclass.RenderElements();
	}

	public IMenu DoEscape()
	{
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
		helpclass.Dispose();
		// TODO:  Add AddressList.Dispose implementation
	}

	#endregion

}