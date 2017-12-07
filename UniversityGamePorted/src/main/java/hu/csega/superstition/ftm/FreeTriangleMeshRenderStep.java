package hu.csega.superstition.ftm;

import java.util.List;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectDirection;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameObjectVertex;
import hu.csega.games.engine.g3d.GameTexturePosition;
import hu.csega.games.engine.intf.GameGraphics;
import hu.csega.superstition.FreeTriangleMeshToolStarter;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;
import hu.csega.superstition.ftm.model.FreeTriangleMeshTriangle;
import hu.csega.superstition.ftm.model.FreeTriangleMeshVertex;

public class FreeTriangleMeshRenderStep implements GameEngineCallback {

	private GameObjectHandler convertedModel = null;
	private GameObjectLocation modelLocation = new GameObjectLocation();;

	@Override
	public Object call(GameEngineFacade facade) {
		FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();
		if(model == null)
			return facade;

		if(model.isInvalid()) {
			List<FreeTriangleMeshVertex> vertices = model.getVertices();
			List<FreeTriangleMeshTriangle> triangles = model.getTriangles();

			GameModelStore store = facade.store();

			if(convertedModel != null) {
				store.dispose(convertedModel);
				convertedModel = null;
			}

			if(!triangles.isEmpty()) {
				GameModelBuilder builder = new GameModelBuilder();

				String textureFilename = model.getTextureFilename();
				if(textureFilename == null || textureFilename.isEmpty())
					textureFilename = FreeTriangleMeshToolStarter.TEXTURE_FILE;

				GameObjectHandler textureHandler = store.loadTexture(textureFilename);
				builder.setTextureHandler(textureHandler);

				for(FreeTriangleMeshVertex v : vertices) {
					GameObjectPosition p = new GameObjectPosition((float)v.getPX(), (float)v.getPY(), (float)v.getPZ());
					GameObjectDirection d = new GameObjectDirection((float)v.getNX(), (float)v.getNY(), (float)v.getNZ());
					GameTexturePosition tex = new GameTexturePosition((float)v.getTX(), (float)v.getTY());
					builder.getVertices().add(new GameObjectVertex(p, d, tex));
				}

				for(FreeTriangleMeshTriangle t : triangles) {
					builder.getIndices().add(t.getVertex1());
					builder.getIndices().add(t.getVertex2());
					builder.getIndices().add(t.getVertex3());
				}

				convertedModel = store.buildModel(builder);
			}

			model.setInvalid(false);
		}

		// Rendering
		GameGraphics g = facade.graphics();

		GameObjectLocation cameraLocation = new GameObjectLocation();
		cameraLocation.position.x = 0;
		cameraLocation.position.y = 0;
		cameraLocation.position.z = -100;
		g.placeCamera(cameraLocation);

		// g.crossHair(0, 0);

		if(convertedModel != null) {
			g.drawModel(convertedModel, modelLocation);
		}

		return facade;
	}

}
