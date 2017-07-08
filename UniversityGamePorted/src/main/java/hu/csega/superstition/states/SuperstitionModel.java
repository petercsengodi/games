package hu.csega.superstition.states;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.superstition.states.gameplay.SuperstitionGamePlayModel;
import hu.csega.superstition.states.gameplay.SuperstitionGamePlayRenderer;
import hu.csega.superstition.states.menu.SuperstitionMainMenuModel;
import hu.csega.superstition.states.menu.SuperstitionMainMenuRenderer;

public class SuperstitionModel {

	private SuperstitionGameRenderingOptions renderingOptions = new SuperstitionGameRenderingOptions();
	private List<SuperstitionState> states = new ArrayList<>();
	private SuperstitionState mainMenu = new SuperstitionState(new SuperstitionMainMenuModel(), new SuperstitionMainMenuRenderer());
	private SuperstitionState gamePlay = new SuperstitionState(new SuperstitionGamePlayModel(), new SuperstitionGamePlayRenderer());

	public SuperstitionModel() {
		states.add(mainMenu);
	}

	public GameEngineCallback currentModel() {
		SuperstitionState state = states.get(states.size() - 1);
		return state.getModel();
	}

	public GameEngineCallback currentRenderer() {
		SuperstitionState state = states.get(states.size() - 1);
		return state.getRenderer();
	}

	public SuperstitionGameRenderingOptions getRenderingOptions() {
		return renderingOptions;
	}

	public SuperstitionState getMainMenu() {
		return mainMenu;
	}

	public SuperstitionState getGamePlay() {
		return gamePlay;
	}
}
