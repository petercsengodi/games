package hu.csega.superstition.states.gameplay;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.game.SuperstitionGameElements;
import hu.csega.superstition.game.SuperstitionGameRenderer;
import hu.csega.superstition.game.SuperstitionSerializableModel;
import hu.csega.superstition.states.SuperstitionModel;

public class SuperstitionGamePlayRenderer implements GameEngineCallback {

	private SuperstitionGameRenderer universeRenderer = new SuperstitionGameRenderer();

	@Override
	public Object call(GameEngineFacade facade) {
		SuperstitionModel model = (SuperstitionModel) facade.model();
		if(model == null)
			return facade;

		SuperstitionGamePlayModel gamePlayModel = (SuperstitionGamePlayModel) model.currentModel();
		SuperstitionSerializableModel universe = gamePlayModel.getSerializableModel();
		SuperstitionGameElements elements = gamePlayModel.getElements();
		universeRenderer.renderGame(facade, universe, elements);

		return facade;
	}

}
