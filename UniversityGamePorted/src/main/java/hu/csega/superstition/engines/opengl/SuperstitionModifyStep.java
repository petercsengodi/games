package hu.csega.superstition.engines.opengl;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.states.SuperstitionModel;

public class SuperstitionModifyStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model == null)
			return facade;

		GameEngineCallback realModel = model.currentModel();
		return realModel.call(facade);
	}

}
