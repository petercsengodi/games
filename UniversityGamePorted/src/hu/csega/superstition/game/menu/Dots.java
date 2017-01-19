package hu.csega.superstition.game.menu;

public class Dots extends MenuElement {
	protected FileMenu menu;
	protected IFileParent parent;
	protected bool show;

	public bool Show
	{
		get { return show; }
		set { show = value; }
	}

	public Dots(ModelParams param, FileMenu menu) : base(param)
	{
		this.menu = menu;
		show = true;
		this.parent = parent;
	}

	public void setParent(IFileParent parent)
	{
		this.parent = parent;
	}

	public override IMenu DoItem()
	{
		return parent.DoChildrenItem(null);
	}

	public override string getText()
	{
		return "...";
	}

	@Override
	public override void Render()
	{
		if(show) base.Render();
	}

}