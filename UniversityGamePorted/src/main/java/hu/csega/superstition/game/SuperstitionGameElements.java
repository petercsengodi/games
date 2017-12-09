package hu.csega.superstition.game;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectDirection;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameObjectVertex;
import hu.csega.games.engine.g3d.GameTexturePosition;

public class SuperstitionGameElements {

	private static final float GOUND_DEPTH = -50f;
	private static final float GOUND_SIZE = 200f;

	GameObjectHandler groundTexture;
	GameObjectHandler groundHandler;
	GameObjectHandler boxModel;

	public void loadElements(GameEngineFacade facade) {
		GameModelStore store = facade.store();

		groundHandler = buildGround(store, "res/textures/z_other/grass-texture.png");

		boxModel = buildBox(store, -30f, -30f, -30f, 30f, 30f, 30f, "res/textures/z_other/wood-texture.jpg");
	}

	private GameObjectHandler buildGround(GameModelStore store, String texture) {
		GameModelBuilder groundBuilder = new GameModelBuilder();

		groundTexture = store.loadTexture(texture);
		groundBuilder.setTextureHandler(groundTexture);

		GameObjectPosition p;
		GameObjectDirection d;
		GameTexturePosition tex;

		d = new GameObjectDirection(0f, 1f, 0f);

		int counter = 0;

		for(float x = -1000f; x < 1000f; x += GOUND_SIZE) {
			for(float y = -1000f; y < 1000f; y += GOUND_SIZE) {

				p = new GameObjectPosition(x, GOUND_DEPTH, y);
				tex = new GameTexturePosition(0f, 0f);
				groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

				p = new GameObjectPosition(x + GOUND_SIZE, GOUND_DEPTH, y);
				tex = new GameTexturePosition(1f, 0f);
				groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

				p = new GameObjectPosition(x + GOUND_SIZE, GOUND_DEPTH, y + GOUND_SIZE);
				tex = new GameTexturePosition(1f, 1f);
				groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

				p = new GameObjectPosition(x,GOUND_DEPTH, y + GOUND_SIZE);
				tex = new GameTexturePosition(0f, 1f);
				groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

				groundBuilder.getIndices().add(counter + 0);
				groundBuilder.getIndices().add(counter + 1);
				groundBuilder.getIndices().add(counter + 2);
				groundBuilder.getIndices().add(counter + 0);
				groundBuilder.getIndices().add(counter + 3);
				groundBuilder.getIndices().add(counter + 2);

				counter += 4;
			}
		}

		return store.buildModel(groundBuilder);
	}

	private GameObjectHandler buildBox(GameModelStore store, float x1, float y1, float z1, float x2, float y2, float z2, String texture) {
		GameObjectHandler textureHandler = store.loadTexture(texture);
		GameModelBuilder builder = new GameModelBuilder();
		builder.setTextureHandler(textureHandler);

		int offset = 0;

		offset = buildRectangle(builder, offset, x1, y1, z1, x2, y1, z1, x2, y2, z1, x1, y2, z1, 0f, 0f, -1f);
		offset = buildRectangle(builder, offset, x1, y1, z2, x2, y1, z2, x2, y2, z2, x1, y2, z2, 0f, 0f, 1f);
		offset = buildRectangle(builder, offset, x1, y1, z1, x1, y2, z1, x1, y2, z2, x1, y1, z2, -1f, 0f, 0f);
		offset = buildRectangle(builder, offset, x2, y1, z1, x2, y2, z1, x2, y2, z2, x2, y1, z2, 1f, 0f, 0f);
		offset = buildRectangle(builder, offset, x1, y1, z1, x2, y1, z1, x2, y1, z2, x1, y1, z2, 0f, -1f, 0f);
		offset = buildRectangle(builder, offset, x1, y2, z1, x2, y2, z1, x2, y2, z2, x1, y2, z2, 0f, 1f, 0f);

		return store.buildModel(builder);
	}

	private int buildRectangle(GameModelBuilder builder, int offset, float x1, float y1, float z1, float x2, float y2, float z2,
			float x3, float y3, float z3, float x4, float y4, float z4, float nx, float ny, float nz) {

		GameObjectPosition p;
		GameTexturePosition tex;
		GameObjectDirection d = new GameObjectDirection(nx, ny, nz);

		p = new GameObjectPosition(x1, y1, z1);
		tex = new GameTexturePosition(0f, 0f);
		builder.getVertices().add(new GameObjectVertex(p, d, tex));

		p = new GameObjectPosition(x2, y2, z2);
		tex = new GameTexturePosition(1f, 0f);
		builder.getVertices().add(new GameObjectVertex(p, d, tex));

		p = new GameObjectPosition(x3, y3, z3);
		tex = new GameTexturePosition(1f, 1f);
		builder.getVertices().add(new GameObjectVertex(p, d, tex));

		p = new GameObjectPosition(x4, y4, z4);
		tex = new GameTexturePosition(0f, 1f);
		builder.getVertices().add(new GameObjectVertex(p, d, tex));

		builder.getIndices().add(offset + 0);
		builder.getIndices().add(offset + 1);
		builder.getIndices().add(offset + 2);
		builder.getIndices().add(offset + 0);
		builder.getIndices().add(offset + 3);
		builder.getIndices().add(offset + 2);

		offset += 4;
		return offset;
	}
}
