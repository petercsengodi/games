package hu.csega.superstition.game.menu;

class LoadMap : MenuElement, IMenu, IFileParent
{
	protected IMenu parent;

	public LoadMap(ModelParams param, IMenu parent) : base(param)
	{
		this.parent = parent;
	}

	public override string getText()
	{
		return "Load Map";
	}

	public override IMenu DoItem()
	{
		param.filemenu.Refresh(@"..\maps", "*.xml", 0, this);
		return param.filemenu;
	}

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

	#region IFileParent Members

	public IMenu DoChildrenItem(string filename)
	{
		param.engine.State.trigger(
			new TriggerParams(MainMenuSelection.LOAD_MAP, @"..\maps\" + filename));
		return null;
	}

	#endregion
}