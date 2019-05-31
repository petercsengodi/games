package hu.csega.editors.anm;

import java.util.List;

import hu.csega.editors.anm.model.AnimatorModel;
import hu.csega.editors.ftm.FreeTriangleMeshMouseController;
import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.editors.ftm.model.FreeTriangleMeshTriangle;
import hu.csega.editors.ftm.model.FreeTriangleMeshVertex;
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

public class AnimatorRenderStep implements GameEngineCallback {

	private GameObjectHandler convertedModel = null;
	private GameObjectLocation modelLocation = new GameObjectLocation();

	// private FreeTriangleMeshMouseController mouseController = null;

	public void setMouseController(FreeTriangleMeshMouseController mouseController) {
		// this.mouseController = mouseController;
	}

	@Override
	public Object call(GameEngineFacade facade) {
		AnimatorModel model = (AnimatorModel) facade.model();
		if(model == null)
			return facade;

		// Rendering
		GameGraphics g = facade.graphics();

		return facade;
	}

}
