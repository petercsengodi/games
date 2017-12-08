package hu.csega.editors.ftm;

import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;

public class FreeTriangleMeshInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		FreeTriangleMeshModel model = new FreeTriangleMeshModel();
		facade.setModel(model);
		return facade;
	}

}
