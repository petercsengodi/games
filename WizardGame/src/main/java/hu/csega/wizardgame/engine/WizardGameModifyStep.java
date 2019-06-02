package hu.csega.wizardgame.engine;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.wizardgame.model.WizardGameModel;

public class WizardGameModifyStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		WizardGameModel model = (WizardGameModel) facade.model();
		if(model == null)
			return facade;

		return null;
	}

}
