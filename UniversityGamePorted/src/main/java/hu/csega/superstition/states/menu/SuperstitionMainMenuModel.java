package hu.csega.superstition.states.menu;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;

public class SuperstitionMainMenuModel implements GameEngineCallback {

	private GameObjectHandler splash;

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

}
