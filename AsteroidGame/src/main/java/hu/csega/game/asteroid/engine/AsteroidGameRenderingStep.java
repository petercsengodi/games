package hu.csega.game.asteroid.engine;

import hu.csega.game.asteroid.model.AsteroidGameModel;
import hu.csega.game.asteroid.model.AsteroidGameState;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;
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

			GameObjectPlacement shipPlacement = gameState.shipPlacement;

			f += 0.005f;

			GameObjectPlacement cameraPlacement = new GameObjectPlacement();
			cameraPlacement.position.x = 0;
			cameraPlacement.position.y = 200;
			cameraPlacement.position.z = 0;
			cameraPlacement.target.x = 0;
			cameraPlacement.target.y = 0;
			cameraPlacement.target.z = 0;
			cameraPlacement.up.x = 1f;
			cameraPlacement.up.y = 0;
			cameraPlacement.up.z = 0;
			g.placeCamera(cameraPlacement);

			GameObjectHandler shipModel = model.getShipModel();
			g.drawModel(shipModel, shipPlacement);
		}

		return facade;
	}

}
