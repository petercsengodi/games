package hu.csega.superstition.game.menu;

public class SaveGame extends MenuElement implements IMenu, IFileParent {
	protected IMenu parent;

	public SaveGame(ModelParams param, IMenu parent) : base(param)
	{
		this.parent = parent;
	}

	@Override
	public string getText()
	{
		return "Save Game";
	}

	@Override
	public IMenu DoItem()
	{
		param.filemenu.Refresh(@"..\saves", "*.save", 15, this);
		return param.filemenu;
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

}