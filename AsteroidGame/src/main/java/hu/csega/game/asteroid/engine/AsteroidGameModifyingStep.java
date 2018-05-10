package hu.csega.game.asteroid.engine;

import hu.csega.game.asteroid.model.AsteroidGameModel;
import hu.csega.game.asteroid.model.AsteroidGameState;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameObjectRotation;
import hu.csega.games.engine.intf.GameControl;

public class AsteroidGameModifyingStep implements GameEngineCallback {

	private static final double PI2 = Math.PI * 2.0;
	private static final double PLAYER_ROTATION = 0.01;
	private static final double PLAYER_FORWARD = 1.0;

	@Override
	public Object call(GameEngineFacade facade) {
		AsteroidGameModel model = (AsteroidGameModel) facade.model();
		AsteroidGameState state = (model != null ? model.getGameState() : null);
		GameControl control = facade.control();

		if(state != null && control != null) {

			GameObjectLocation player = state.shipLocation;
			GameObjectPosition position = player.position;
			GameObjectRotation rotation = player.rotation;

			double speedModifier = (control.isControlOn() ? 15.0 : 1.0) * PLAYER_FORWARD;

			if(control.isUpOn()) {
				position.x += (speedModifier * Math.sin(rotation.x));
				position.z += (speedModifier * Math.cos(rotation.x));
			}

			if(control.isDownOn()) {
				position.x -= (speedModifier * Math.sin(rotation.x));
				position.z -= (speedModifier * Math.cos(rotation.x));
			}

			if(control.isRightOn()) {
				rotation.x -= PLAYER_ROTATION;
				while(rotation.x < 0) {
					rotation.x += PI2;
				}
			}

			if(control.isLeftOn()) {
				rotation.x += PLAYER_ROTATION;
				while(rotation.x >= PI2) {
					rotation.x -= PI2;
				}
			}

		} // null checks

		return facade;
	}

}
