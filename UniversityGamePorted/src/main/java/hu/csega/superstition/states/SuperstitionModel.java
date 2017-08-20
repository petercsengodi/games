package hu.csega.superstition.states;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameKeyListener;
import hu.csega.games.engine.intf.GameMouseListener;
import hu.csega.superstition.states.gameplay.SuperstitionGamePlayBuilder;
import hu.csega.superstition.states.gameplay.SuperstitionGamePlayKeyListener;
import hu.csega.superstition.states.gameplay.SuperstitionGamePlayModel;
import hu.csega.superstition.states.gameplay.SuperstitionGamePlayMouseListener;
import hu.csega.superstition.states.gameplay.SuperstitionGamePlayRenderer;
import hu.csega.superstition.states.menu.SuperstitionMainMenuBuilder;
import hu.csega.superstition.states.menu.SuperstitionMainMenuKeyListener;
import hu.csega.superstition.states.menu.SuperstitionMainMenuModel;
import hu.csega.superstition.states.menu.SuperstitionMainMenuMouseListener;
import hu.csega.superstition.states.menu.SuperstitionMainMenuRenderer;

public class SuperstitionModel {

	private SuperstitionGameRenderingOptions renderingOptions = new SuperstitionGameRenderingOptions();
	private List<SuperstitionState> states = new ArrayList<>();

	private SuperstitionState mainMenu = new SuperstitionState(
			new SuperstitionMainMenuModel(),
			new SuperstitionMainMenuBuilder(),
			new SuperstitionMainMenuRenderer(),
			new SuperstitionMainMenuKeyListener(),
			new SuperstitionMainMenuMouseListener());

	private SuperstitionState gamePlay = new SuperstitionState(
			new SuperstitionGamePlayModel(),
			new SuperstitionGamePlayBuilder(),
			new SuperstitionGamePlayRenderer(),
			new SuperstitionGamePlayKeyListener(),
			new SuperstitionGamePlayMouseListener());

	public SuperstitionModel() {
		states.add(mainMenu);
		states.add(gamePlay);
	}

	public GameEngineCallback currentModel() {
		SuperstitionState state = states.get(states.size() - 1);
		return state.getModel();
	}

	public GameEngineCallback currentRenderer() {
		SuperstitionState state = states.get(states.size() - 1);
		return state.getRenderer();
	}

	public GameKeyListener currentKeyListener() {
		SuperstitionState state = states.get(states.size() - 1);
		return state.getKeyListener();
	}

	public GameMouseListener currentMouseListener() {
		SuperstitionState state = states.get(states.size() - 1);
		return state.getMouseListener();
	}

	public void buildAllStates(GameEngineFacade facade) {
		for(SuperstitionState state : states) {
			state.getBuilder().call(facade);
		}
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
