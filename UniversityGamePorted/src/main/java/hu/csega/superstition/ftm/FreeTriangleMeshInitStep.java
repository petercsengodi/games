package hu.csega.superstition.ftm;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;

public class FreeTriangleMeshInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		FreeTriangleMeshModel model = new FreeTriangleMeshModel();
		facade.setModel(model);
		return facade;
	}

}
