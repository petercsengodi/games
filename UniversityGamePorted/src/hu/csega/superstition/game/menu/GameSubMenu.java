package hu.csega.superstition.game.menu;

class GameSubMenu : MenuElement, IMenu
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

	public MenuElement[] getMenuElements()
	{
		return elements;
	}

	public IMenu getParent()
	{
		return parent;
	}

	public void RenderElements()
	{
		menuhelp.RenderElements();
	}

	public IMenu DoEscape()
	{
		return parent;
	}

	public void setLastIndex(int idx)
	{
		lastindex = idx;
	}

	public int getLastIndex()
	{
		return lastindex;
	}

	public override void Dispose()
	{
		base.Dispose();
		menuhelp.Dispose();
	}

	#endregion
}