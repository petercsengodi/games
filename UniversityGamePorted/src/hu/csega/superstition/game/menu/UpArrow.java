package hu.csega.superstition.game.menu;

class UpArrow : MenuElement
{
	protected FileMenu menu;

	public UpArrow(ModelParams param, FileMenu menu) : base(param)
	{
		this.menu = menu;
	}

	public override IMenu DoItem()
	{
		menu.ScrollUp();
		return null;
	}

	public override string getText()
	{
		return "< UP >";
	}

}