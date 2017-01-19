package hu.csega.superstition.game.menu;

class Shadows extends MenuElement {
	public Shadows(ModelParams param)
	{
		super(param);
	}

	@Override
	public String getText()
	{
		return "Dynamic Shadows";
	}

	@Override
	public IMenu DoItem()
	{
		param.engine.Options.renderShadow = !param.engine.Options.renderShadow;
		return null;
	}

	@Override
	public void Render()
	{
		bool val = param.engine.Options.renderShadow;
		base.Render();
		param.onoff.Render(translation + OnOff.DEFAULT, val);
	}

}