package hu.csega.editors.anm;

import java.util.List;

import hu.csega.editors.anm.layer1.view3d.AnimatorSet;
import hu.csega.editors.anm.layer1.view3d.AnimatorSetPart;
import hu.csega.editors.ftm.layer1.presentation.opengl.FreeTriangleMeshMouseController;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameTransformation;
import hu.csega.games.engine.intf.GameGraphics;

public class AnimatorRenderStep implements GameEngineCallback {

	private AnimatorSet set;

	// private FreeTriangleMeshMouseController mouseController = null;

	public AnimatorRenderStep() {
	}

	public void setMouseController(FreeTriangleMeshMouseController mouseController) {
		// this.mouseController = mouseController;
	}

	public void setAnimatorSet(AnimatorSet set) {
		this.set = set;
	}

	@Override
	public Object call(GameEngineFacade facade) {
		GameGraphics g = facade.graphics();

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
