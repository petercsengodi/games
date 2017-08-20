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

public class SuperstitionGamePlayElements {

	GameObjectHandler groundTexture;
	GameObjectHandler groundHandler;
	GameObjectHandler testFTMModel;
	GameObjectHandler figureFTMModel;
	GameObjectHandler faceFTMModel;

	public void loadElements(GameEngineFacade facade) {
		GameModelStore store = facade.store();

		buildGround(store);

		testFTMModel = buildFTM(store, "res/ftm/test.ftm", "res/textures/z_other/wood-texture.jpg");
		figureFTMModel = buildFTM(store, "res/ftm/figure.ftm", "res/textures/z_other/metal-texture.jpg");
		faceFTMModel = buildFTM(store, "res/ftm/heart.ftm", "res/textures/z_other/red-texture.jpg");
	}

	private void buildGround(GameModelStore store) {
		GameModelBuilder groundBuilder = new GameModelBuilder();

		groundTexture = store.loadTexture("res/textures/z_other/bunny-texture.jpg");
		groundBuilder.setTextureHandler(groundTexture);

		GameObjectPosition p;
		GameObjectDirection d;
		GameTexturePosition tex;

		d = new GameObjectDirection(0f, 1f, 0f);

		p = new GameObjectPosition(-1000f, -50f, -1000f);
		tex = new GameTexturePosition(0f, 0f);
		groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

		p = new GameObjectPosition(1000f, -50f, -1000f);
		tex = new GameTexturePosition(1f, 0f);
		groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

		p = new GameObjectPosition(1000f, -50f, 1000f);
		tex = new GameTexturePosition(1f, 1f);
		groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

		p = new GameObjectPosition(-1000f, -50f, 1000f);
		tex = new GameTexturePosition(0f, 1f);
		groundBuilder.getVertices().add(new GameObjectVertex(p, d, tex));

		groundBuilder.getIndices().add(0);
		groundBuilder.getIndices().add(1);
		groundBuilder.getIndices().add(2);
		groundBuilder.getIndices().add(0);
		groundBuilder.getIndices().add(3);
		groundBuilder.getIndices().add(2);

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
