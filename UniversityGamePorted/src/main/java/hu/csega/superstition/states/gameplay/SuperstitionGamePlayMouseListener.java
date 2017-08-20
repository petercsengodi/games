package hu.csega.superstition.states.gameplay;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameMouseListener;
import hu.csega.superstition.game.SuperstitionGameModify;
import hu.csega.superstition.game.SuperstitionSerializableModel;
import hu.csega.superstition.states.SuperstitionModel;

public class SuperstitionGamePlayMouseListener implements GameMouseListener {

	@Override
	public void pressed(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model != null) {
			SuperstitionGamePlayModel gamePlayModel = (SuperstitionGamePlayModel) model.currentModel();
			SuperstitionSerializableModel universe = gamePlayModel.getSerializableModel();
			SuperstitionGameModify.pressed(universe, x, y, leftMouse, rightMouse);
		}
	}

	@Override
	public void released(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model != null) {
			SuperstitionGamePlayModel gamePlayModel = (SuperstitionGamePlayModel) model.currentModel();
			SuperstitionSerializableModel universe = gamePlayModel.getSerializableModel();
			SuperstitionGameModify.released(universe, x, y, leftMouse, rightMouse);
		}
	}

	@Override
	public void clicked(GameEngineFacade facade, int x, int y, boolean leftMouse, boolean rightMouse) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model != null) {
			SuperstitionGamePlayModel gamePlayModel = (SuperstitionGamePlayModel) model.currentModel();
			SuperstitionSerializableModel universe = gamePlayModel.getSerializableModel();
			SuperstitionGameModify.clicked(universe, x, y, leftMouse, rightMouse);
		}
	}

	@Override
	public void moved(GameEngineFacade facade, int deltaX, int deltaY, boolean leftMouseDown, boolean rightMouseDown) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model != null) {
			SuperstitionGamePlayModel gamePlayModel = (SuperstitionGamePlayModel) model.currentModel();
			SuperstitionSerializableModel universe = gamePlayModel.getSerializableModel();
			SuperstitionGameModify.moved(universe, deltaX, deltaY, leftMouseDown, rightMouseDown);
		}
	}

}
