package hu.csega.superstition.game.menu;

class Options : MenuElement, IMenu
{
	protected IMenu parent;
	protected MenuElement[] elements;
	protected MenuHelpClass menuhelp;
	protected int lastindex = 0;

	public Options(ModelParams param, IMenu parent) : base(param)
	{
		this.parent = parent;

		elements = new MenuElement[]{
										new Detail(param),
										new Shadows(param),
										new MeshShadows(param)
									};

		menuhelp = new MenuHelpClass(param, elements);
	}

	public override string getText()
	{
		return "Options";
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
		base.Dispose ();
		menuhelp.Dispose();
	}

	#endregion

}