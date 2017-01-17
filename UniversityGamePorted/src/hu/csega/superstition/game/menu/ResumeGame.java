package hu.csega.superstition.game.menu;

class ResumeGame : MenuElement
{
	public ResumeGame(ModelParams param) : base(param)
	{
	}

	public override string getText()
	{
		return "Resume Game";
	}

	public override IMenu DoItem()
	{
		param.engine.State.trigger(new TriggerParams(MainMenuSelection.FAILURE));
		return null;
	}

}