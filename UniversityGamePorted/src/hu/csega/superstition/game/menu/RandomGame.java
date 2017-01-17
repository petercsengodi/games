package hu.csega.superstition.game.menu;

class RandomGame : MenuElement, IMenu
{
	protected IMenu parent;
	protected MenuElement[] elements;
	protected MenuHelpClass menuhelp;
	protected int lastindex = 0;

	public RandomGame(ModelParams param, IMenu parent) : base(param)
	{
		this.parent = parent;

		elements = new MenuElement[]{
										new DenseMaze(param)
									};

		menuhelp = new MenuHelpClass(param, elements);
	}

	public override string getText()
	{
		return "Random Game";
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