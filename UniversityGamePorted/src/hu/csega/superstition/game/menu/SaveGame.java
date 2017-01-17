package hu.csega.superstition.game.menu;

class SaveGame : MenuElement, IMenu, IFileParent
{
	protected IMenu parent;

	public SaveGame(ModelParams param, IMenu parent) : base(param)
	{
		this.parent = parent;
	}

	public override string getText()
	{
		return "Save Game";
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
		int idx = param.filemenu.getLastIndex();

		string fn;
		fn = @"..\saves\save" + (idx / 10).ToString()
			+ (idx % 10).ToString() + ".save";
		param.engine.State.trigger(
			new TriggerParams(MainMenuSelection.SAVE_GAME, fn));
		return null;
	}

	#endregion
}