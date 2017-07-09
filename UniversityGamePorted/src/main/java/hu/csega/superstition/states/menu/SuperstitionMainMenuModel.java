package hu.csega.superstition.states.menu;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectLocation;

public class SuperstitionMainMenuModel implements GameEngineCallback {

	private GameObjectHandler splash;

	private GameObjectLocation cameraLocation;

	public SuperstitionMainMenuModel() {
		cameraLocation = new GameObjectLocation();
		cameraLocation.position.set(0f, 0f, -10f);
		cameraLocation.forward.set(0f, 0f, 1f);
		cameraLocation.up.set(0f, 1f, 0f);
	}

	@Override
	public Object call(GameEngineFacade facade) {
		return facade;
	}

	public GameObjectHandler getSplash() {
		return splash;
	}

	public void setSplash(GameObjectHandler splash) {
		this.splash = splash;
	}

	public GameObjectLocation camera() {
		return cameraLocation;
	}

}
