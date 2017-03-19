package hu.csega.superstition.game.menu;

public class Dots extends MenuElement {
	protected FileMenu menu;
	protected IFileParent parent;
	protected boolean show;

	public boolean Show
	{
		get { return show; }
		set { show = value; }
	}

	public Dots(ModelParams param, FileMenu menu)
	{
		super(param);
		this.menu = menu;
		show = true;
		this.parent = parent;
	}

	public void setParent(IFileParent parent)
	{
		this.parent = parent;
	}

	@Override
	public IMenu DoItem()
	{
		return parent.DoChildrenItem(null);
	}

	@Override
	public string getText()
	{
		return "...";
	}

	@Override
	public void Render()
	{
		if(show) base.Render();
	}

}