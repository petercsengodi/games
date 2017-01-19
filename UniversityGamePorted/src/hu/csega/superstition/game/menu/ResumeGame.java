package hu.csega.superstition.game.menu;

public class ResumeGame extends MenuElement
{
	public ResumeGame(ModelParams param)
	{
		super(param);
	}

	@Override
	public String getText()
	{
		return "Resume Game";
	}

	@Override
	public IMenu DoItem()
	{
		param.engine.State.trigger(new TriggerParams(MainMenuSelection.FAILURE));
		return null;
	}

}