package hu.csega.superstition.engines.opengl;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameMouseListener;
import hu.csega.superstition.states.SuperstitionModel;

public class SuperstitionMouseListener implements GameMouseListener {

	@Override
	public void pressed(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model != null) {
			GameMouseListener realListener = model.currentMouseListener();
			realListener.pressed(facade, x, y, leftMouse, rightMouse);
		}
	}

	@Override
	public void released(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model != null) {
			GameMouseListener realListener = model.currentMouseListener();
			realListener.pressed(facade, x, y, leftMouse, rightMouse);
		}
	}

	@Override
	public void clicked(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model != null) {
			GameMouseListener realListener = model.currentMouseListener();
			realListener.pressed(facade, x, y, leftMouse, rightMouse);
		}
	}

	@Override
	public void moved(GameEngineFacade facade, int deltaX, int deltaY, boolean leftMouseDown, boolean rightMouseDown) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model != null) {
			GameMouseListener realListener = model.currentMouseListener();
			realListener.moved(facade, deltaX, deltaY, leftMouseDown, rightMouseDown);
		}
	}

}
