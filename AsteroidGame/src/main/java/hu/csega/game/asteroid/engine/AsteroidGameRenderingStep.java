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

	static float f = 0.0f;

	@Override
	public Object call(GameEngineFacade facade) {
		AsteroidGameModel model = (AsteroidGameModel) facade.model();

		if(model != null) {
			GameGraphics g = facade.graphics();
			AsteroidGameState gameState = model.getGameState();

			GameObjectLocation shipLocation = gameState.shipLocation;

			f += 0.005f;

			GameObjectLocation cameraLocation = new GameObjectLocation();
			cameraLocation.position.x = 0;
			cameraLocation.position.y = 200;
			cameraLocation.position.z = 0;
			cameraLocation.rotation.x = (float)(Math.PI / 2.0);
			cameraLocation.rotation.y = 0;
			cameraLocation.rotation.z = 0;
			g.placeCamera(cameraLocation);

			//			GameObjectLocation cameraLocation = new GameObjectLocation();
			//			cameraLocation.position.x = 200;
			//			cameraLocation.position.y = 0;
			//			cameraLocation.position.z = 0;
			//			g.placeCamera(cameraLocation);

			GameObjectHandler shipModel = model.getShipModel();
			g.drawModel(shipModel, shipLocation);
		}

		return facade;
	}

}
