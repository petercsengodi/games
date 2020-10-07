package hu.csega.editors.transformations.layer1.presentation.opengl;

import hu.csega.editors.transformations.layer4.data.TransformationTesterModel;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;

public class TransformationTesterInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		TransformationTesterModel model = new TransformationTesterModel();
		facade.setModel(model);
		return facade;
	}

}
