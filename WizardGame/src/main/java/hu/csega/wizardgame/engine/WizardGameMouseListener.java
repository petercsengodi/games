package hu.csega.wizardgame.engine;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameMouseListener;
import hu.csega.wizardgame.model.WizardGameModel;

public class WizardGameMouseListener implements GameMouseListener {

	@Override
	public void pressed(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse) {
		WizardGameModel model = (WizardGameModel) facade.model();
		if(model != null) {
			GameMouseListener realListener = model.currentMouseListener();
			if(realListener != null) {
				realListener.pressed(facade, x, y, leftMouse, rightMouse);
			}
		}
	}

	@Override
	public void released(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse) {
		WizardGameModel model = (WizardGameModel) facade.model();
		if(model != null) {
			GameMouseListener realListener = model.currentMouseListener();
			if(realListener != null) {
				realListener.pressed(facade, x, y, leftMouse, rightMouse);
			}
		}
	}

	@Override
	public void clicked(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse) {
		WizardGameModel model = (WizardGameModel) facade.model();
		if(model != null) {
			GameMouseListener realListener = model.currentMouseListener();
			if(realListener != null) {
				realListener.pressed(facade, x, y, leftMouse, rightMouse);
			}
		}
	}

	@Override
	public void moved(GameEngineFacade facade, int deltaX, int deltaY, boolean leftMouseDown, boolean rightMouseDown) {
		WizardGameModel model = (WizardGameModel) facade.model();
		if(model != null) {
			GameMouseListener realListener = model.currentMouseListener();
			if(realListener != null) {
				realListener.moved(facade, deltaX, deltaY, leftMouseDown, rightMouseDown);
			}
		}
	}

}
