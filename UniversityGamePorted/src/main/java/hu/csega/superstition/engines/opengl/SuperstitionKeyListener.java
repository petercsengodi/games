package hu.csega.superstition.engines.opengl;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameKeyListener;
import hu.csega.superstition.states.SuperstitionModel;

public class SuperstitionKeyListener implements GameKeyListener {

	@Override
	public void hit(GameEngineFacade facade, char key) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model != null) {
			GameKeyListener realListener = model.currentKeyListener();
			realListener.hit(facade, key);
		}
	}

}
