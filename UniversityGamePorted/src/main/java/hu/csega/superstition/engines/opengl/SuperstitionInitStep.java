package hu.csega.superstition.engines.opengl;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.states.SuperstitionModel;

public class SuperstitionInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {

		SuperstitionModel model = new SuperstitionModel();
		facade.setModel(model);

		model.buildAllStates(facade);

		return facade;
	}

}
