package hu.csega.superstition.states;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.superstition.states.menu.SuperstitionMainMenuModel;
import hu.csega.superstition.states.menu.SuperstitionMainMenuRenderer;

public class SuperstitionModel {

	private List<SuperstitionState> states = new ArrayList<>();

	public SuperstitionModel() {
		states.add(new SuperstitionState(new SuperstitionMainMenuModel(), new SuperstitionMainMenuRenderer()));
	}

	public GameEngineCallback currentModel() {
		SuperstitionState state = states.get(states.size() - 1);
		return state.getModel();
	}

	public GameEngineCallback currentRenderer() {
		SuperstitionState state = states.get(states.size() - 1);
		return state.getRenderer();
	}

}
