package hu.csega.superstition.game.menu;

public class NetworkPlay extends MenuElement implements IFileParent
{
	protected IMenu parent;

	public NetworkPlay(ModelParams param, IMenu parent)
	{
		super(param);
		this.parent = parent;
	}

	@Override
	public IMenu DoItem()
	{
		param.filemenu.Refresh(@"..\network", "*.srv", 0, this);
		return param.filemenu;
	}

	@Override
	public String getText()
	{
		return "Network Play";
	}

	@Override
	public IMenu DoChildrenItem(String filename)
	{
		Network.NetHost host = new Network.NetHost("../network/" + filename);
		return new AddressList(param, parent, host);
	}

	@Override
	public MenuElement[] getMenuElements()
	{
		return null;
	}

	@Override
	public IMenu getParent()
	{
		return parent;
	}

	@Override
	public void RenderElements()
	{
		param.filemenu.RenderElements();
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

	@Override
	public void Dispose()
	{
		super.Dispose();
	}

}