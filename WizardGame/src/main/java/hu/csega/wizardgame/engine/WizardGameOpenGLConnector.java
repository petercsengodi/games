package hu.csega.wizardgame.engine;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import hu.csega.games.adapters.opengl.OpenGLGameAdapter;
import hu.csega.games.common.Connector;
import hu.csega.games.engine.env.Environment;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameDescriptor;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.games.engine.intf.GameWindowListener;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class WizardGameOpenGLConnector implements Connector, GameWindow {

	private List<GameWindowListener> listeners = new ArrayList<>();

	@Override
	public void initialize() {
	}

	@Override
	public void run(Environment env) {
		logger.info(className() + " start run()");

		startGameEngine();

		logger.info(className() + " end run()");
	}

	@Override
	public void register(GameWindowListener listener) {
		listeners.add(listener);
	}

	@Override
	public void add(GameCanvas arg0, Container arg1) {
	}

	@Override
	public void setFullScreen(boolean fullScreen) {
	}

	@Override
	public void showWindow() {
	}

	@Override
	public void closeWindow() {
		for(GameWindowListener listener: listeners) {
			listener.onFinishingWork();
		}
	}

	@Override
	public void closeApplication() {
		for(GameWindowListener listener: listeners) {
			listener.onFinishingWork();
		}

		System.exit(0);
	}

	@Override
	public void dispose() {
		logger.info(className() + " start dispose()");

		logger.info(className() + " end dispose()");
	}

	@Override
	public void repaintEverything() {
	}

	private GameEngine startGameEngine() {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("wizard");
		descriptor.setTitle("Untitled Wizard Game Project");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("I always wanted to create something like this.");

		GameAdapter adapter = new OpenGLGameAdapter();
		GameEngine engine = GameEngine.create(descriptor, adapter);

		engine.step(GameEngineStep.INIT, new WizardGameInitStep());
		engine.step(GameEngineStep.MODIFY, new WizardGameModifyStep());
		engine.step(GameEngineStep.RENDER, new WizardGameRenderStep());

		engine.startInNewWindow();
		return engine;
	}

	private String className() {
		return getClass().getSimpleName();
	}

	private static final Logger logger = LoggerFactory.createLogger(WizardGameOpenGLConnector.class);
}
