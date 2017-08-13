package hu.csega.superstition.states.menu;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectVertex;
import hu.csega.superstition.states.SuperstitionModel;

public class SuperstitionMainMenuBuilder implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model == null)
			return facade;

		GameModelStore store = facade.store();
		GameObjectHandler texture = store.loadTexture("res/example/texture.png");

		GameModelBuilder builder = new GameModelBuilder();
		builder.setTextureHandler(texture);

		builder.getVertices().add(new GameObjectVertex(-0.5f, -0.5f, 0f, 0f, 0f, 1f, 0f, 0f));
		builder.getVertices().add(new GameObjectVertex(-0.5f, +0.5f, 0f, 0f, 0f, 1f, 0f, 1f));
		builder.getVertices().add(new GameObjectVertex(+0.5f, +0.5f, 0f, 0f, 0f, 1f, 1f, 1f));
		builder.getVertices().add(new GameObjectVertex(+0.5f, -0.5f, 0f, 0f, 0f, 1f, 1f, 0f));

		builder.getIndices().add(0);
		builder.getIndices().add(1);
		builder.getIndices().add(3);
		builder.getIndices().add(1);
		builder.getIndices().add(2);
		builder.getIndices().add(3);

		GameObjectHandler splash = store.buildModel(builder);
		SuperstitionMainMenuModel mainMenu = (SuperstitionMainMenuModel) model.getMainMenu().getModel();
		mainMenu.setSplash(splash);

		return facade;
	}

}
