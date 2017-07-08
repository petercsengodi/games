package hu.csega.game.asteroid.steps;

import hu.csega.game.asteroid.engine.AsteroidGameRenderingOptions;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.intf.GameGraphics;

public class AsteroidGameRenderingStep implements GameEngineCallback {

	private AsteroidGameRenderingOptions options;

	public AsteroidGameRenderingStep(AsteroidGameRenderingOptions options) {
		this.options = options;
	}

	@Override
	public Object call(GameEngineFacade facade) {
		GameObjectHandler model = (GameObjectHandler) facade.model();

		if(model != null) {
			GameGraphics g = facade.graphics();
			g.drawModel(model, null);
		}

		return facade;
	}

}
