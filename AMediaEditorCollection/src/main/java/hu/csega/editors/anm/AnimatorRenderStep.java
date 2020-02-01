package hu.csega.editors.anm;

import hu.csega.editors.anm.components.ComponentOpenGLTransformer;
import hu.csega.editors.anm.model.AnimatorModel;
import hu.csega.editors.ftm.FreeTriangleMeshMouseController;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.intf.GameGraphics;
import hu.csega.games.units.UnitStore;

public class AnimatorRenderStep implements GameEngineCallback {

	private GameObjectPlacement modelPlacement = null;
	private GameObjectPlacement cameraPlacement = null;
	private GameObjectHandler modelObject = null;

	private ComponentOpenGLTransformer animatorModelBuilder;

	// private FreeTriangleMeshMouseController mouseController = null;

	public AnimatorRenderStep() {
		this.animatorModelBuilder = UnitStore.instance(ComponentOpenGLTransformer.class);
	}

	public void setMouseController(FreeTriangleMeshMouseController mouseController) {
		// this.mouseController = mouseController;
	}

	@Override
	public Object call(GameEngineFacade facade) {
		AnimatorModel model = (AnimatorModel) facade.model();
		if(model == null)
			return facade;


		if(modelPlacement == null) {
			modelPlacement = new GameObjectPlacement();
			modelPlacement.target.set(0f, 0f, 1f);
			modelPlacement.up.set(0f, 1f, 0f);
		}

		if(cameraPlacement == null) {
			cameraPlacement = new GameObjectPlacement();
			cameraPlacement.position.x = 0;
			cameraPlacement.position.y = 0;
			cameraPlacement.position.z = 100;
			cameraPlacement.target.x = 0;
			cameraPlacement.target.y = 0;
			cameraPlacement.target.z = 0;
			cameraPlacement.up.x = 0;
			cameraPlacement.up.y = -1;
			cameraPlacement.up.z = 0;
		}


		if(modelObject == null) {
			GameModelBuilder builder = animatorModelBuilder.transform(model);
			modelObject = facade.store().buildModel(builder);
		}

		// Rendering
		GameGraphics g = facade.graphics();
		g.placeCamera(cameraPlacement);
		g.drawModel(modelObject, modelPlacement);

		return facade;
	}

}
