package hu.csega.superstition.game;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectDirection;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameObjectVertex;
import hu.csega.games.engine.g3d.GameTexturePosition;
import hu.csega.superstition.FreeTriangleMeshToolStarter;

public class SuperstitionGamePlayElements {

	GameObjectHandler groundTexture;
	GameObjectHandler groundHandler;

	public void loadElements(GameEngineFacade facade) {
		GameModelStore store = facade.store();

		buildGround(store);
	}

	private void buildGround(GameModelStore store) {
		GameModelBuilder groundBuilder = new GameModelBuilder();

		groundTexture = store.loadTexture(FreeTriangleMeshToolStarter.TEXTURE_FILE);
		groundBuilder.setTextureHandler(groundTexture);

		GameObjectPosition p;
		GameObjectDirection d;
		GameTexturePosition tex;

		d = new GameObjectDirection(0f, 1f, 0f);

		p = new GameObjectPosition(-10f, 0f, -10f);
		tex = new GameTexturePosition(0f, 0f);
		groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

		p = new GameObjectPosition(10f, 0f, -10f);
		tex = new GameTexturePosition(1f, 0f);
		groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

		p = new GameObjectPosition(10f, 0f, 10f);
		tex = new GameTexturePosition(1f, 1f);
		groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

		p = new GameObjectPosition(-10f, 0f, 10f);
		tex = new GameTexturePosition(0f, 1f);
		groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

		groundBuilder.getIndices().add(0);
		groundBuilder.getIndices().add(1);
		groundBuilder.getIndices().add(2);
		groundBuilder.getIndices().add(0);
		groundBuilder.getIndices().add(2);
		groundBuilder.getIndices().add(3);

		groundHandler = store.buildModel(groundBuilder);
	}

}
