package hu.csega.superstition.game.menu;

public class Options extends MenuElement implements IMenu
{
	protected IMenu parent;
	protected MenuElement[] elements;
	protected MenuHelpClass menuhelp;
	protected int lastindex = 0;

	public Options(ModelParams param, IMenu parent)
	{
		super(param);
		this.parent = parent;

		elements = new MenuElement[]{
				new Detail(param),
				new Shadows(param),
				new MeshShadows(param)
		};

		menuhelp = new MenuHelpClass(param, elements);
	}

	@Override
	public String getText()
	{
		return "Options";
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
		super.Dispose ();
		menuhelp.dispose();
	}

}