package hu.csega.superstition;

import hu.csega.superstition.common.ApplicationStarter;
import hu.csega.superstition.engines.connector.Connector;
import hu.csega.superstition.engines.opengl.SuperstitionOpenGLConnector;
import hu.csega.toolshed.logging.Level;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

// A Fizz-buzz way of running the game

/**
 * Responsible for running the whole game.
 * A single instance in the JVM, accessible by any components, and
 * links all the components together.
 * However, not static methods are used, as writing a testable code is prio 1!
 */
public class SuperstitionGameStarter {

	private static final Level LOGGING_LEVEL = Level.TRACE;
	private static Logger logger;

	public static void main(String[] args) {
		LoggerFactory.setDefaultLevel(LOGGING_LEVEL);
		logger = LoggerFactory.createLogger(SuperstitionGameStarter.class);
		logger.info("Starting game.");

		Connector connector = new SuperstitionOpenGLConnector();
		ApplicationStarter starter = new ApplicationStarter(connector);
		starter.start(args);
	}
}
