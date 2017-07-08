package hu.csega.games.rotary.steps;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;

public class RotaryDisposeStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		return facade;
	}

}
