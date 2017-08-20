package hu.csega.superstition.states.gameplay;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameKeyListener;
import hu.csega.superstition.game.SuperstitionGameModify;
import hu.csega.superstition.game.SuperstitionSerializableModel;
import hu.csega.superstition.states.SuperstitionModel;

public class SuperstitionGamePlayKeyListener implements GameKeyListener {

	@Override
	public void hit(GameEngineFacade facade, char key) {
		if(key == 'q' || key == 'Q' || key == 27)
			facade.window().closeApplication();

		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model != null) {
			SuperstitionGamePlayModel gamePlayModel = (SuperstitionGamePlayModel) model.currentModel();
			SuperstitionSerializableModel universe = gamePlayModel.getSerializableModel();
			SuperstitionGameModify.hit(universe, key);
		}
	}

}
