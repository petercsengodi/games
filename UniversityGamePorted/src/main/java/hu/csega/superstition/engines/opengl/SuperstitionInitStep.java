package hu.csega.superstition.engines.opengl;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.superstition.states.SuperstitionModel;
import hu.csega.superstition.states.menu.SuperstitionMainMenuModel;

public class SuperstitionInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		GameModelStore store = facade.store();
		store.loadTexture("res/example/texture.png");

		SuperstitionModel model = new SuperstitionModel();
		facade.setModel(model);

		GameObjectHandler splash = store.buildModel(new GameModelBuilder());
		SuperstitionMainMenuModel mainMenu = (SuperstitionMainMenuModel) model.getMainMenu().getModel();
		mainMenu.setSplash(splash);

		return facade;
	}

}
