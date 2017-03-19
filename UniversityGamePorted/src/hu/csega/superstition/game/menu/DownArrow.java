package hu.csega.superstition.game.menu;

public class DownArrow extends MenuElement {
	protected FileMenu menu;

	public DownArrow(ModelParams param, FileMenu menu)
	{
		super(param);
		this.menu = menu;
	}

	@Override
	public IMenu DoItem()
	{
		menu.ScrollDown();
		return null;
	}

	@Override
	public String getText()
	{
		return "< DOWN >";
	}

}