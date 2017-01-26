package hu.csega.superstition.game.menu;

public class LoadGame extends MenuElement implements IMenu, IFileParent
{
	protected IMenu parent;

	public LoadGame(ModelParams param, IMenu parent)
	{
		super(param);
		this.parent = parent;
	}

	@Override
	public getText()
	{
		return "Load Game";
	}

	@Override
	public IMenu DoItem()
	{
		param.filemenu.Refresh("/saves", "*.save", 15, this);
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
		param.engine.State.trigger(
				new TriggerParams(MainMenuSelection.LOAD_GAME, "/saves" + filename));
		return null;
	}

}