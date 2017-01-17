package hu.csega.superstition.game.menu;

class Main : IMenu
{
	protected ModelParams param;
	protected MenuElement[] elements;
	protected MenuHelpClass menuhelp;
	protected int lastindex = 0;

	public Main(ModelParams param)
	{
		elements = new MenuElement[]{
										new GameSubMenu(param, this),
										new NetworkPlay(param, this),
										new Options(param, this),
										new ResumeGame(param),
										new Quit(param)
									};

		this.param = param;
		menuhelp = new MenuHelpClass(param, elements);
	}

	#region IMenu Members

	public MenuElement[] getMenuElements()
	{
		return elements;
	}

	public IMenu getParent()
	{
		return null;
	}

	public void RenderElements()
	{
		menuhelp.RenderElements();
	}

	public IMenu DoEscape()
	{
		param.engine.State.trigger(new TriggerParams(MainMenuSelection.FAILURE));
		return null;
	}

	public void setLastIndex(int idx)
	{
		lastindex = idx;
	}

	public int getLastIndex()
	{
		return lastindex;
	}

	public void Dispose()
	{
		menuhelp.Dispose();
	}

	#endregion
}