package hu.csega.wizardgame;

import hu.csega.games.common.ApplicationStarter;
import hu.csega.games.common.Connector;
import hu.csega.toolshed.logging.Level;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;
import hu.csega.wizardgame.engine.WizardGameOpenGLConnector;

// A Fizz-buzz way of running the game

/**
 * Responsible for running the whole game.
 * A single instance in the JVM, accessible by any components, and
 * links all the components together.
 * However, not static methods are used, as writing a testable code is prio 1!
 */
public class WizardGameStarter {

	private static final Level LOGGING_LEVEL = Level.TRACE;
	private static Logger logger;

	public static void main(String[] args) {
		LoggerFactory.setDefaultLevel(LOGGING_LEVEL);
		logger = LoggerFactory.createLogger(WizardGameStarter.class);
		logger.info("Starting game.");

		Connector connector = new WizardGameOpenGLConnector();
		ApplicationStarter starter = new ApplicationStarter(connector);
		starter.start(args);
	}
}
