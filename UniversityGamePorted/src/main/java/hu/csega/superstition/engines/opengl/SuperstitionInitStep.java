package hu.csega.superstition.engines.opengl;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectHandler;

public class SuperstitionInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		GameModelStore store = facade.store();
		store.loadTexture("res/example/texture.png");

		GameObjectHandler model = store.buildModel(new GameModelBuilder());
		facade.setModel(model);

		return facade;
	}

}
