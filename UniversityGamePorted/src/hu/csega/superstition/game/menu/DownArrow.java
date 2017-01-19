package hu.csega.superstition.game.menu;

public class DownArrow extends MenuElement {
	protected FileMenu menu;

	public DownArrow(ModelParams param, FileMenu menu) : base(param)
	{
		this.menu = menu;
	}

	public override IMenu DoItem()
	{
		menu.ScrollDown();
		return null;
	}

	public override string getText()
	{
		return "< DOWN >";
	}

}