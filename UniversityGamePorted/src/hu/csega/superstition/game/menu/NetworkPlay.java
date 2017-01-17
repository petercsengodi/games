package hu.csega.superstition.game.menu;

class NetworkPlay : MenuElement, IFileParent
{
	protected IMenu parent;

	public NetworkPlay(ModelParams param, IMenu parent) : base(param)
	{
		this.parent = parent;
	}

	public override IMenu DoItem()
	{
		param.filemenu.Refresh(@"..\network", "*.srv", 0, this);
		return param.filemenu;
	}

	public override string getText()
	{
		return "Network Play";
	}

	#region IFileParent Members

	public IMenu DoChildrenItem(string filename)
	{
		Network.NetHost host = new Network.NetHost("../network/" + filename);
		return new AddressList(param, parent, host);
	}

	#endregion

	#region IMenu Members

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

	#endregion

	#region IDisposable Members

	public override void Dispose()
	{
		base.Dispose();

		// TODO:  Add JoinHost.Dispose implementation
	}

	#endregion
}