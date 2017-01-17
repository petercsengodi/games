package hu.csega.superstition.game.menu;

class LoadGame : MenuElement, IMenu, IFileParent
{
	protected IMenu parent;

	public LoadGame(ModelParams param, IMenu parent) : base(param)
	{
		this.parent = parent;
	}

	public override string getText()
	{
		return "Load Game";
	}

	public override IMenu DoItem()
	{
		param.filemenu.Refresh(@"..\saves", "*.save", 15, this);
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
			new TriggerParams(MainMenuSelection.LOAD_GAME, @"..\saves\" + filename));
		return null;
	}

	#endregion
}