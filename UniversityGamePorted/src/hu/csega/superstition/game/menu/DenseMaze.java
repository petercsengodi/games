package hu.csega.superstition.game.menu;

class DenseMaze : MenuElement
{
	public DenseMaze(ModelParams param) : base(param)
	{
	}

	public override IMenu DoItem()
	{
		param.engine.State.trigger(
			new TriggerParams(MainMenuSelection.DENSE_MAP, null));
		return null;
	}

	public override string getText()
	{
		return "Dense Maze";
	}

}