package hu.csega.superstition.game.menu;

public class AddressList implements IMenu
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

	@Override
	public MenuElement[] getMenuElements()
	{
		return elements;
	}

	@Override
	public IMenu getParent()
	{
		return parent;
	}

	@Override
	public void RenderElements()
	{
		helpclass.RenderElements();
	}

	@Override
	public IMenu DoEscape()
	{
		return parent;
	}

	@Override
	public void setLastIndex(int idx)
	{
	}

	@Override
	public int getLastIndex()
	{
		return 0;
	}

	public void dispose()
	{
		helpclass.dispose();
		// TODO:  Add AddressList.Dispose implementation
	}

}