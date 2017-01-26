package hu.csega.superstition.game.menu;

public class Main implements IMenu
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


	@Override
	public MenuElement[] getMenuElements()
	{
		return elements;
	}

	@Override
	public IMenu getParent()
	{
		return null;
	}

	@Override
	public void RenderElements()
	{
		menuhelp.RenderElements();
	}

	@Override
	public IMenu DoEscape()
	{
		param.engine.State.trigger(new TriggerParams(MainMenuSelection.FAILURE));
		return null;
	}

	@Override
	public void setLastIndex(int idx)
	{
		lastindex = idx;
	}

	@Override
	public int getLastIndex()
	{
		return lastindex;
	}

	public void Dispose()
	{
		menuhelp.Dispose();
	}

}