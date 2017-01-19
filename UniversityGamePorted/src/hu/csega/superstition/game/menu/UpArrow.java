package hu.csega.superstition.game.menu;

public class UpArrow extends MenuElement {

	protected FileMenu menu;

	public UpArrow(ModelParams param, FileMenu menu)
	{
		super(param);
		this.menu = menu;
	}

	@Override
	public IMenu DoItem()
	{
		menu.ScrollUp();
		return null;
	}

	@Override
	public String getText()
	{
		return "< UP >";
	}

}