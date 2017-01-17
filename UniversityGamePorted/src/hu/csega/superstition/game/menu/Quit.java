package hu.csega.superstition.game.menu;

class Quit : MenuElement
{
	public Quit(ModelParams param) : base(param)
	{
	}

	public override string getText()
	{
		return "Quit";
	}

	public override IMenu DoItem()
	{
		param.engine.State.trigger(new TriggerParams(MainMenuSelection.QUIT));
		return null;
	}

}