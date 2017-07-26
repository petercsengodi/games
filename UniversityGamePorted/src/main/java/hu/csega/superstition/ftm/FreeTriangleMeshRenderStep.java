package hu.csega.superstition.ftm;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;

public class FreeTriangleMeshRenderStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();
		if(model == null)
			return facade;

		// Rendering

		return facade;
	}

}
