package hu.csega.superstition;

import hu.csega.games.engine.env.EnvironmentImpl;
import hu.csega.games.engine.env.GameEngineException;
import hu.csega.superstition.engines.Phase;
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

	private Connector connector;
	private Phase phase = Phase.ZERO;
	private EnvironmentImpl env;

	private static Logger logger;

	private static final Level LOGGING_LEVEL = Level.TRACE;
	private static final String SEPARATOR = "\n-----------------------";

	public static void main(String[] args) {
		LoggerFactory.setDefaultLevel(LOGGING_LEVEL);
		logger = LoggerFactory.createLogger(SuperstitionGameStarter.class);
		logger.info("Starting game.");

		EnvironmentImpl env = new EnvironmentImpl();
		SuperstitionGameStarter superstition = new SuperstitionGameStarter();
		superstition.setEnvironment(env);

		try {

			superstition.initialize();

			superstition.run();

		} catch(GameEngineException ex) {

			handleGameException(ex, superstition);

		} catch(Throwable t) {

			logger.error("FATAL error during " + superstition.phaseString() + " phase", t);

		} finally {

			try {

				superstition.finish();

			} catch(GameEngineException ex) {

				handleGameException(ex, superstition);

			} catch(Throwable t) {

				logger.error("FATAL error during " + superstition.phaseString() + " phase", t);

			}
		}

		System.exit(0);
	}

	public static void handleGameException(GameEngineException ex, SuperstitionGameStarter superstition) {
		StringBuilder msg = new StringBuilder();
		msg.append("GameException occurred in phase ");
		msg.append(superstition.phaseString());
		msg.append(";\nMessage: ");
		msg.append(ex.getMessage());

		if(ex.getDescription() != null) {
			msg.append(SEPARATOR).append('\n').append(ex.getDescription()).append(SEPARATOR);
		}

		logger.error(msg.toString(), ex);
	}

	private void initialize() {
		phase = Phase.INITIALIZATION;
		logger.info("Initialization phase started.");

		logger.info("Creating connector for example program.");
		connector = new SuperstitionOpenGLConnector();

		logger.info("Initialization phase finished.");
		phase = Phase.INITIALIZATION_OVER;
	}

	private void run() {
		phase = Phase.GAME;
		logger.info("Game phase started.");

		connector.run(env);

		logger.info("Waiting for game to finish.");
		env.waitForExitting();
		logger.info("Exitting from game.");

		logger.info("Game phase finished.");
		phase = Phase.GAME_OVER;
	}

	private void finish() {
		phase = Phase.FINALIZATION;
		logger.info("Finalization phase started.");

		env.finish();

		connector.dispose();

		logger.info("Finalization phase finished.");
		phase = Phase.FINALIZATION_OVER;
	}

	private void setEnvironment(EnvironmentImpl env) {
		this.env = env;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public String phaseString() {
		return (phase == null ? "null" : phase.name());
	}
}
