package hu.csega.superstition.game.menu;

public class Detail extends MenuElement {
	public Detail(ModelParams param)
	{
		super(param);
	}

	@Override
	public String getText()
	{
		return "Detail";
	}

	@Override
	public void Render()
	{
		int val;
		if(param.engine.Options.detail == EngineOptions.D_LOW)
			val = Slider.LOW;
		else if(param.engine.Options.detail == EngineOptions.D_MID)
			val = Slider.MEDIUM;
		else val = Slider.HIGH;

		base.Render();
		param.slider.Render(translation + Slider.DEFAULT[val], val);
	}

	@Override
	public IMenu DoItem()
	{
		if(param.game_menu) return null;
		if(param.engine.Options.detail == EngineOptions.D_LOW)
			param.engine.Options.detail = EngineOptions.D_MID;
		else if(param.engine.Options.detail == EngineOptions.D_MID)
			param.engine.Options.detail = EngineOptions.D_HIGH;
		else if (param.engine.Options.detail == EngineOptions.D_HIGH)
			param.engine.Options.detail = EngineOptions.D_LOW;
		return null;
	}

}