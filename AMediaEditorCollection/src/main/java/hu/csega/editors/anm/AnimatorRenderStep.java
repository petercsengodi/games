package hu.csega.editors.anm;

import hu.csega.editors.anm.model.AnimatorModel;
import hu.csega.editors.ftm.FreeTriangleMeshMouseController;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.intf.GameGraphics;

public class AnimatorRenderStep implements GameEngineCallback {

	private GameObjectHandler convertedModel = null;
	private GameObjectPlacement modelLocation = new GameObjectPlacement();

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
