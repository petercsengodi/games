package hu.csega.game.asteroid.steps;

import hu.csega.game.asteroid.engine.AsteroidGameRenderingOptions;
import hu.csega.game.asteroid.model.AsteroidGameModel;
import hu.csega.game.asteroid.model.AsteroidGameState;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.intf.GameGraphics;

public class AsteroidGameRenderingStep implements GameEngineCallback {

	private AsteroidGameRenderingOptions options;

	public AsteroidGameRenderingStep(AsteroidGameRenderingOptions options) {
		this.options = options;
	}

	@Override
	public Object call(GameEngineFacade facade) {
		AsteroidGameModel model = (AsteroidGameModel) facade.model();

		if(model != null) {
			GameGraphics g = facade.graphics();
			AsteroidGameState gameState = model.getGameState();

			GameObjectHandler shipModel = model.getShipModel();
			GameObjectLocation shipLocation = gameState.shipLocation;
			g.drawModel(shipModel, shipLocation);
		}

		return facade;
	}

}
