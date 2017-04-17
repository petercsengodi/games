package hu.csega.games.engine.impl;

import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameThread;
import hu.csega.games.engine.GameWindowListener;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class DefaultGameWindowListener implements GameWindowListener {

	private GameThread thread;
	private GameEngine engine;

	public DefaultGameWindowListener(GameThread thread, GameEngine engine) {
		this.thread = thread;
		this.engine = engine;
	}

	@Override
	public void onFinishingWork() {
		logger.info("Interrupting thread.");
		thread.interrupt();

		logger.info("Disposing engine.");
		engine.dispose();

		logger.info("Default game window listener finished.");
	}

	private static final Logger logger = LoggerFactory.createLogger(DefaultGameWindowListener.class);
}
