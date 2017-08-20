package hu.csega.superstition.engines.opengl;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.superstition.states.SuperstitionModel;

public class SuperstitionInitStep implements GameEngineCallback {

	@Override
	public Object call(GameEngineFacade facade) {
		GameControl control = facade.control();
		control.registerKeyListener(new SuperstitionKeyListener());
		control.registerMouseListener(new SuperstitionMouseListener());

		SuperstitionModel model = new SuperstitionModel();
		facade.setModel(model);

		model.buildAllStates(facade);

		return facade;
	}

}
