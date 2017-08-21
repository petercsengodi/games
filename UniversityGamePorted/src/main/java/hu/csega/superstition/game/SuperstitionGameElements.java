package hu.csega.superstition.game;

import java.util.List;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectDirection;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameObjectVertex;
import hu.csega.games.engine.g3d.GameTexturePosition;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;
import hu.csega.superstition.ftm.model.FreeTriangleMeshTriangle;
import hu.csega.superstition.ftm.model.FreeTriangleMeshVertex;
import hu.csega.superstition.ftm.util.FreeTriangleMeshSnapshots;

public class SuperstitionGameElements {

	private static final float GOUND_DEPTH = -50f;
	private static final float GOUND_SIZE = 200f;

	GameObjectHandler groundTexture;
	GameObjectHandler groundHandler;
	GameObjectHandler testFTMModel;
	GameObjectHandler figureFTMModel;
	GameObjectHandler faceFTMModel;

	public void loadElements(GameEngineFacade facade) {
		GameModelStore store = facade.store();

		buildGround(store);

		testFTMModel = buildFTM(store, "res/ftm/test.ftm", "res/textures/z_other/metal-texture.jpg");
		figureFTMModel = buildFTM(store, "res/ftm/figure.ftm", "res/textures/z_other/wood-texture.jpg");
		faceFTMModel = buildFTM(store, "res/ftm/face.ftm", "res/textures/z_other/face-texture.jpg");
	}

	private void buildGround(GameModelStore store) {
		GameModelBuilder groundBuilder = new GameModelBuilder();

		groundTexture = store.loadTexture("res/textures/z_other/grass-texture.png");
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

		groundHandler = store.buildModel(groundBuilder);
	}

	private GameObjectHandler buildFTM(GameModelStore store, String ftmFile, String ftmTexture) {
		byte[] serialized = FreeTriangleMeshSnapshots.readAllBytes(ftmFile);
		FreeTriangleMeshModel model = (FreeTriangleMeshModel)FreeTriangleMeshSnapshots.deserialize(serialized);
		GameObjectHandler textureHandler = store.loadTexture(ftmTexture);

		GameModelBuilder builder = new GameModelBuilder();
		builder.setTextureHandler(textureHandler);

		List<FreeTriangleMeshVertex> vertices = model.getVertices();
		for(FreeTriangleMeshVertex v : vertices) {
			GameObjectPosition p = new GameObjectPosition((float)v.getPX(), (float)v.getPY(), (float)v.getPZ());
			GameObjectDirection d = new GameObjectDirection((float)v.getNX(), (float)v.getNY(), (float)v.getNZ());
			GameTexturePosition tex = new GameTexturePosition((float)v.getTX(), (float)v.getTY());
			builder.getVertices().add(new GameObjectVertex(p, d, tex));
		}

		List<FreeTriangleMeshTriangle> triangles = model.getTriangles();
		for(FreeTriangleMeshTriangle t : triangles) {
			builder.getIndices().add(t.getVertex1());
			builder.getIndices().add(t.getVertex2());
			builder.getIndices().add(t.getVertex3());
		}

		return store.buildModel(builder);
	}

}
