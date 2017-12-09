package hu.csega.game.asteroid.engine;

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

			GameObjectLocation shipLocation = gameState.shipLocation;

			GameObjectLocation cameraLocation = new GameObjectLocation();
			cameraLocation.position.copyValuesFrom(shipLocation.position);
			cameraLocation.position.z -= 100;
			g.placeCamera(cameraLocation);

			GameObjectHandler shipModel = model.getShipModel();
			g.drawModel(shipModel, shipLocation);
		}

		return facade;
	}

}
