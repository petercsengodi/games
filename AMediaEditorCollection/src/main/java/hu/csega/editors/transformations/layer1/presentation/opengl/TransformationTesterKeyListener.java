package hu.csega.editors.transformations.layer1.presentation.opengl;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameKeyListener;

public class TransformationTesterKeyListener implements GameKeyListener {

	public TransformationTesterKeyListener() {
	}

	@Override
	public void hit(GameEngineFacade facade, char key) {
		if(key == 27) { // escape
		}

		if(key == 'd' || key == 'D' || key == 127) { // del
		}
	}

}
