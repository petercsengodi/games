package hu.csega.games.engine.impl;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.games.engine.intf.GameGraphics;
import hu.csega.games.engine.intf.GameTimer;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class GameEngineFacadeImpl implements GameEngineFacade {

	private GameEngine engine;

	public GameEngineFacadeImpl(GameEngine engine) {
		this.engine = engine;
	}

	@Override
	public Object model() {
		return engine.getModel();
	}

	@Override
	public Object setModel(Object model) {
		engine.setModel(model);
		return model;
	}

	@Override
	public GameEngineFacade requestRebuild() {
		return engine.requestRebuild();
	}

	@Override
	public GameGraphics graphics() {
		return engine.getGraphics();
	}

	@Override
	public GameModelStore store() {
		return engine.getStore();
	}

	@Override
	public GameTimer timer() {
		return engine.getTimer();
	}

	@Override
	public GameControl control() {
		return engine.getControl();
	}

	@Override
	public GameWindow window() {
		return engine.getWindow();
	}

	@Override
	public Logger logger() {
		return commonLogger;
	}

	private static final Logger commonLogger = LoggerFactory.createLogger(CommonLogger.class);
}
