package hu.csega.superstition.states.gameplay;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.states.SuperstitionModel;

public class SuperstitionGamePlayBuilder implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model == null)
			return facade;

		SuperstitionGamePlayModel gamePlayModel = (SuperstitionGamePlayModel) model.currentModel();
		gamePlayModel.elements.loadElements(facade);
		return facade;
	}

}
