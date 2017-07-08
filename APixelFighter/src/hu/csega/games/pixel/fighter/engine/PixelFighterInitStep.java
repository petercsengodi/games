package hu.csega.games.pixel.fighter.engine;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;

public class PixelFighterInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		facade.setModel(new PixelFighterModel());
		return facade;
	}

}
