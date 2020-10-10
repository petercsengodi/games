package hu.csega.game.rush.layer4.data;

import java.util.List;

import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectDirection;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameObjectVertex;
import hu.csega.games.engine.g3d.GameTexturePosition;

public class RushBox {

	public static GameObjectHandler mesh(GameModelStore store, String textureFilename, double x1, double y1, double z1, double x2, double y2, double z2) {
		GameModelBuilder builder = new GameModelBuilder();

		GameObjectHandler textureHandler = store.loadTexture(textureFilename);
		builder.setTextureHandler(textureHandler);

		// 1. side: z = z1 // FRONT
		put(builder, x2, y2, z1, 0, 0, -1, 1, 1);
		put(builder, x2, y1, z1, 0, 0, -1, 1, 0);
		put(builder, x1, y2, z1, 0, 0, -1, 0, 1);

		put(builder, x2, y1, z1, 0, 0, -1, 1, 0);
		put(builder, x1, y2, z1, 0, 0, -1, 0, 1);
		put(builder, x1, y1, z1, 0, 0, -1, 0, 0);

		// 2. side: z = z2 // BACK
		put(builder, x2, y2, z2, 0, 0, 1, 1, 1);
		put(builder, x2, y1, z2, 0, 0, 1, 1, 0);
		put(builder, x1, y2, z2, 0, 0, 1, 0, 1);

		put(builder, x2, y1, z2, 0, 0, 1, 1, 0);
		put(builder, x1, y2, z2, 0, 0, 1, 0, 1);
		put(builder, x1, y1, z2, 0, 0, 1, 0, 0);

		// 3. side: x = x1 // LEFT
		put(builder, x1, y2, z2, -1, 0, 0, 1, 1);
		put(builder, x1, y2, z1, -1, 0, 0, 0, 0);
		put(builder, x1, y1, z2, -1, 0, 0, 1, 0);

		put(builder, x1, y2, z1, -1, 0, 0, 0, 1);
		put(builder, x1, y1, z2, -1, 0, 0, 1, 0);
		put(builder, x1, y1, z1, -1, 0, 0, 0, 0);

		// 4. side: x = x2 // RIGHT
		put(builder, x2, y2, z2, 1, 0, 0, 1, 1);
		put(builder, x2, y2, z1, 1, 0, 0, 0, 1);
		put(builder, x2, y1, z2, 1, 0, 0, 1, 0);

		put(builder, x2, y2, z1, 1, 0, 0, 0, 1);
		put(builder, x2, y1, z2, 1, 0, 0, 1, 0);
		put(builder, x2, y1, z1, 1, 0, 0, 0, 0);

		// 5. side: y = y1 // TOP
		put(builder, x2, y1, z2, 0, -1, 0, 1, 1);
		put(builder, x2, y1, z1, 0, -1, 0, 1, 0);
		put(builder, x1, y1, z2, 0, -1, 0, 0, 1);

		put(builder, x2, y1, z1, 0, -1, 0, 1, 0);
		put(builder, x1, y1, z2, 0, -1, 0, 0, 1);
		put(builder, x1, y1, z1, 0, -1, 0, 0, 0);

		// 6. side: y = y2 // BOTTOM
		put(builder, x2, y2, z2, 0, 1, 0, 1, 1);
		put(builder, x2, y2, z1, 0, 1, 0, 1, 0);
		put(builder, x1, y2, z2, 0, 1, 0, 0, 1);

		put(builder, x2, y2, z1, 0, 1, 0, 1, 0);
		put(builder, x1, y2, z2, 0, 1, 0, 0, 1);
		put(builder, x1, y2, z1, 0, 1, 0, 0, 0);

		return store.buildModel(builder);
	}

	private static void put(GameModelBuilder builder, double px, double py, double pz, double dx, double dy, double dz, double tx, double ty) {
		GameObjectPosition p = new GameObjectPosition((float)px, (float)py, (float)pz);
		GameObjectDirection d = new GameObjectDirection((float)dx, (float)dy, (float)dz);
		GameTexturePosition tex = new GameTexturePosition((float)tx, (float)ty);
		builder.getVertices().add(new GameObjectVertex(p, d, tex));

		List<Integer> indices = builder.getIndices();
		indices.add(indices.size());
	}

}
