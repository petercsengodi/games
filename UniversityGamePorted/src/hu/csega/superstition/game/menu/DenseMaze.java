package hu.csega.superstition.game.menu;

public class DenseMaze extends MenuElement {
	public DenseMaze(ModelParams param)
	{
		super(param);
	}

	@Override
	public IMenu DoItem()
	{
		param.engine.State.trigger(
				new TriggerParams(MainMenuSelection.DENSE_MAP, null));
		return null;
	}

	@Override
	public String getText()
	{
		return "Dense Maze";
	}

}