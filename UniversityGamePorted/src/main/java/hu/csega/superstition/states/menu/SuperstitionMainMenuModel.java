package hu.csega.superstition.states.menu;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;

public class SuperstitionMainMenuModel implements GameEngineCallback {

	private GameObjectHandler splash;

	private GameObjectPlacement cameraPlacement;

	public SuperstitionMainMenuModel() {
		cameraPlacement = new GameObjectPlacement();
		cameraPlacement.position.set(0f, 0f, -10f);
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

	public GameObjectPlacement camera() {
		return cameraPlacement;
	}

}
