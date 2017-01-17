package hu.csega.superstition.game.menu;

class Shadows extends MenuElement
{
	public Shadows(ModelParams param) : base(param)
	{
	}

	public override string getText()
	{
		return "Dynamic Shadows";
	}

	public override IMenu DoItem()
	{
		param.engine.Options.renderShadow = !param.engine.Options.renderShadow;
		return null;
	}

	public override void Render()
	{
		bool val = param.engine.Options.renderShadow;
		base.Render();
		param.onoff.Render(translation + OnOff.DEFAULT, val);
	}

}