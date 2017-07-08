package hu.csega.superstition.states.menu;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.states.SuperstitionModel;

public class SuperstitionMainMenuRenderer implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model == null)
			return facade;

		return facade;
	}

}
