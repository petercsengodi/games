package hu.csega.editors.transformations.layer1.presentation.opengl;

import java.util.List;

import hu.csega.editors.transformations.layer4.data.TransformationTesterModel;
import hu.csega.editors.transformations.layer4.data.TransformationTesterVertex;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectDirection;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameObjectVertex;
import hu.csega.games.engine.g3d.GameTexturePosition;
import hu.csega.games.engine.intf.GameGraphics;

public class TransformationTesterRenderStep implements GameEngineCallback {

	private GameObjectHandler convertedModel = null;
	private GameObjectPlacement modelPlacement = null;

	@Override
	public Object call(GameEngineFacade facade) {
		TransformationTesterModel model = (TransformationTesterModel) facade.model();
		if(model == null)
			return facade;

		if(modelPlacement == null) {
			modelPlacement = new GameObjectPlacement();
			modelPlacement.target.set(0f, 0f, -1f);
			modelPlacement.up.set(0f, 1f, 0f);
		}

		if(convertedModel == null) {
			List<TransformationTesterVertex> vertices = model.getVertices();

			GameModelStore store = facade.store();

			GameModelBuilder builder = new GameModelBuilder();

			String textureFilename = "res/textures/labels.jpg";

			GameObjectHandler textureHandler = store.loadTexture(textureFilename);
			builder.setTextureHandler(textureHandler);

			for(TransformationTesterVertex v : vertices) {
				GameObjectPosition p = new GameObjectPosition((float)v.getPX(), (float)v.getPY(), (float)v.getPZ());
				GameObjectDirection d = new GameObjectDirection((float)v.getNX(), (float)v.getNY(), (float)v.getNZ());
				GameTexturePosition tex = new GameTexturePosition((float)v.getTX(), (float)v.getTY());
				builder.getVertices().add(new GameObjectVertex(p, d, tex));
			}

			for(int i = 0; i < vertices.size(); i++) {
				builder.getIndices().add(i);
			}

			convertedModel = store.buildModel(builder);
		}


		// Rendering
		GameGraphics g = facade.graphics();

		GameObjectPlacement cameraLocation = model.getCamera();
		g.placeCamera(cameraLocation);

		// g.crossHair(0, 0);

		if(convertedModel != null) {
			g.drawModel(convertedModel, modelPlacement);
		}

		return facade;
	}

}
