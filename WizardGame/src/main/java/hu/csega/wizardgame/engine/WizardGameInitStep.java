package hu.csega.wizardgame.engine;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.wizardgame.model.WizardGameModel;

public class WizardGameInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		GameControl control = facade.control();
		control.registerKeyListener(new WizardGameKeyListener());
		control.registerMouseListener(new WizardGameMouseListener());

		WizardGameModel model = new WizardGameModel();
		facade.setModel(model);

		return facade;
	}

}
