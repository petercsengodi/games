package hu.csega.editors.transformations;

import java.util.List;

import hu.csega.editors.transformations.model.TransformationTesterModel;
import hu.csega.editors.transformations.model.TransformationTesterVertex;
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

public class TransformationTesterRenderStep implements GameEngineCallback {

	private GameObjectHandler convertedModel = null;
	private GameObjectLocation modelLocation = new GameObjectLocation();

	private TransformationTesterMouseController mouseController = null;

	public void setMouseController(TransformationTesterMouseController mouseController) {
		this.mouseController = mouseController;
	}

	@Override
	public Object call(GameEngineFacade facade) {
		TransformationTesterModel model = (TransformationTesterModel) facade.model();
		if(model == null)
			return facade;

		if(convertedModel == null) {
			List<TransformationTesterVertex> vertices = model.getVertices();

			GameModelStore store = facade.store();

			GameModelBuilder builder = new GameModelBuilder();

			String textureFilename = "res/textures/wood-texture.jpg";

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

		double alfa = 0.0;
		double beta = 0.0;
		double distance = 100.0;

		if(mouseController != null) {
			double scaling = mouseController.getScaling();
			distance *= scaling;
			alfa = mouseController.getAlfa();
			beta = mouseController.getBeta();
		}

		// Rendering
		GameGraphics g = facade.graphics();

		GameObjectLocation cameraLocation = new GameObjectLocation();
		cameraLocation.position.x = 0;
		cameraLocation.position.y = 0;
		cameraLocation.position.z = -(float)distance;
		g.placeCamera(cameraLocation);

		// g.crossHair(0, 0);

		if(convertedModel != null) {
			modelLocation.rotation.y = (float)alfa;
			modelLocation.rotation.x = (float)beta;
			g.drawModel(convertedModel, modelLocation);
		}

		return facade;
	}

}
