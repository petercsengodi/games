package hu.csega.superstition.game.menu;

public class GameSubMenu extends MenuElement implements IMenu
{
	protected IMenu parent;
	protected MenuElement[] elements;
	protected MenuHelpClass menuhelp;
	protected int lastindex = 0;

	public GameSubMenu(ModelParams param, IMenu parent) : base(param)
	{
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

	public override string getText()
	{
		return "Game";
	}

	public override IMenu DoItem()
	{
		return this;
	}

	#region IMenu Members

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
	public override void Dispose()
	{
		base.Dispose();
		menuhelp.Dispose();
	}

	#endregion
}