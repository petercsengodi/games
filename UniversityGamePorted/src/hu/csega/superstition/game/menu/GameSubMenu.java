package hu.csega.superstition.game.menu;

public class GameSubMenu extends MenuElement implements IMenu
{
	protected IMenu parent;
	protected MenuElement[] elements;
	protected MenuHelpClass menuhelp;
	protected int lastindex = 0;

	public GameSubMenu(ModelParams param, IMenu parent)
	{
		super(param);
		this.parent = parent;


		if(param.game_menu)
		{
			elements = new MenuElement[]{
					//											new NewGame(param),
					new LoadMap(param, this),
					new LoadGame(param, this),
					new SaveGame(param, this),
					new RandomGame(param, this)
			};
		}
		else
		{
			elements = new MenuElement[]{
					//											new NewGame(param),
					new LoadMap(param, this),
					new LoadGame(param, this),
					new RandomGame(param, this)
			};
		}

		menuhelp = new MenuHelpClass(param, elements);
	}

	@Override
	public String getText()
	{
		return "Game";
	}

	@Override
	public IMenu DoItem()
	{
		return this;
	}

	@Override
	public MenuElement[] getMenuElements()
	{
		return elements;
	}

	@Override
	public IMenu getParent()
	{
		return parent;
	}

	@Override
	public void RenderElements()
	{
		menuhelp.RenderElements();
	}

	@Override
	public IMenu DoEscape()
	{
		return parent;
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

	@Override
	public void dispose()
	{
		super.Dispose();
		menuhelp.Dispose();
	}

}