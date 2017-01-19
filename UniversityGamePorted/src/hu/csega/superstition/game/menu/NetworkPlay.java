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

	public IMenu DoChildrenItem(string filename)
	{
		Network.NetHost host = new Network.NetHost("../network/" + filename);
		return new AddressList(param, parent, host);
	}

	public MenuElement[] getMenuElements()
	{
		return null;
	}

	public IMenu getParent()
	{
		return parent;
	}

	public void RenderElements()
	{
		param.filemenu.RenderElements();
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

	@Override
	public override void Dispose()
	{
		base.Dispose();
	}

}