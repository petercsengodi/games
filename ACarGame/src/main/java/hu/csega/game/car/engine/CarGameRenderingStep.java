package hu.csega.game.car.engine;

import hu.csega.game.car.model.CarGameModel;
import hu.csega.game.car.model.CarGameState;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.intf.GameGraphics;

public class CarGameRenderingStep implements GameEngineCallback {

	private CarGameRenderingOptions options;

	public CarGameRenderingStep(CarGameRenderingOptions options) {
		this.options = options;
	}

	@Override
	public Object call(GameEngineFacade facade) {
		CarGameModel model = (CarGameModel) facade.model();

		if(model != null) {
			GameGraphics g = facade.graphics();
			CarGameState gameState = model.getGameState();

			GameObjectLocation carLocation = gameState.carLocation;
			GameObjectLocation groundLocation = gameState.groundLocation;

			GameObjectLocation cameraLocation = new GameObjectLocation();
			cameraLocation.position.x = 0;
			cameraLocation.position.y = -200;
			cameraLocation.position.z = 0;
			cameraLocation.rotation.y = (float)(3 * Math.PI / 2);
			g.placeCamera(cameraLocation);

			GameObjectHandler carModel = model.getCarModel();
			g.drawModel(carModel, carLocation);

			GameObjectHandler groundModel = model.getCarMapModel();
			g.drawModel(groundModel, groundLocation);
		}

		return facade;
	}

}
