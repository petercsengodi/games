package hu.csega.superstition.game.menu;

public class Quit extends MenuElement
{
	public Quit(ModelParams param)
	{
		super(param);
	}

	@Override
	public String getText()
	{
		return "Quit";
	}

	@Override
	public IMenu DoItem()
	{
		param.engine.State.trigger(new TriggerParams(MainMenuSelection.QUIT));
		return null;
	}

}