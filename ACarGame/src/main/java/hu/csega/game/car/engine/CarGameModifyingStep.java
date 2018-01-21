package hu.csega.game.car.engine;

import hu.csega.game.car.model.CarGameModel;
import hu.csega.game.car.model.CarGameState;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.intf.GameControl;

public class CarGameModifyingStep implements GameEngineCallback {

	public CarGameModifyingStep() {
	}

	@Override
	public Object call(GameEngineFacade facade) {
		CarGameModel model = (CarGameModel) facade.model();
		GameControl control = facade.control();

		if(model != null) {
			CarGameState gameState = model.getGameState();
			if(gameState != null) {
				modify(gameState, control);
			}
		}

		return facade;
	}

	private void modify(CarGameState gameState, GameControl control) {
		GameObjectPosition v = gameState.carVelocity.position;
		GameObjectPosition p = gameState.carLocation.position;

		if(control.isUpOn()) {
			v.z = Math.min(v.z + 0.01f, 1f);
		} else {
			v.z = Math.max(v.z - 0.01f, 0f);
		}

		p.x += v.z;
	}

}
