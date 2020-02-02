package hu.csega.editors.anm;

import java.util.List;

import hu.csega.editors.anm.components.Component3DView;
import hu.csega.editors.anm.model.AnimatorModel;
import hu.csega.editors.anm.view3d.AnimatorSet;
import hu.csega.editors.anm.view3d.AnimatorSetPart;
import hu.csega.editors.ftm.FreeTriangleMeshMouseController;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameTransformation;
import hu.csega.games.engine.intf.GameGraphics;
import hu.csega.games.units.UnitStore;

public class AnimatorRenderStep implements GameEngineCallback {

	private Component3DView view;

	// private FreeTriangleMeshMouseController mouseController = null;

	public AnimatorRenderStep() {
		this.view = UnitStore.instance(Component3DView.class);
	}

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

		AnimatorSet set = view.provide();
		if(set != null) {
			GameObjectPlacement camera = set.getCamera();
			if(camera != null) {
				g.placeCamera(camera);
			}

			List<AnimatorSetPart> parts = set.getParts();
			if(parts != null && parts.size() > 0) {
				for(AnimatorSetPart part : parts) {
					GameObjectHandler modelObject = part.getModel();
					GameTransformation modelTransformation = part.getTransformation();
					g.drawModel(modelObject, modelTransformation);
				}
			}
		}

		return facade;
	}

}
