package hu.csega.wizardgame.engine;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameKeyListener;
import hu.csega.wizardgame.model.WizardGameModel;

public class WizardGameKeyListener implements GameKeyListener {

	@Override
	public void hit(GameEngineFacade facade, char key) {
		if(key == 'q' || key == 'Q' || key == 27)
			facade.window().closeApplication();

		WizardGameModel model = (WizardGameModel) facade.model();
		GameKeyListener realListener = model.currentKeyListener();
		if(realListener != null) {
			realListener.hit(facade, key);
		}
	}

}
