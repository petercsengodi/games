package hu.csega.games.rotary.steps;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.rotary.play.RotaryModel;

public class RotaryInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		RotaryModel model = new RotaryModel();
		model.init();
		facade.setModel(model);

		facade.control().registerKeyListener(model);
		return facade;
	}

}
