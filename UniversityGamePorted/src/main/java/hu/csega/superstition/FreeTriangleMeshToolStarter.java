package hu.csega.superstition;

import hu.csega.games.engine.env.EnvironmentImpl;
import hu.csega.games.engine.env.GameEngineException;
import hu.csega.superstition.engines.Phase;
import hu.csega.superstition.engines.connector.Connector;
import hu.csega.superstition.ftm.FreeTriangleMeshConnector;
import hu.csega.toolshed.logging.Level;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

/**
 * Responsible for running the Free Triangle Mesh tool.
 * A single instance in the JVM, accessible by any components, and
 * links all the components together.
 * However, not static methods are used, as writing a testable code is prio 1!
 */
public class FreeTriangleMeshToolStarter {

	public static final String TEXTURE_FILE = "res/textures/z_other/face-texture.jpg";

	private Connector connector;
	private Phase phase = Phase.ZERO;
	private EnvironmentImpl env;

	private static Logger logger;

	private static final Level LOGGING_LEVEL = Level.TRACE;
	private static final String SEPARATOR = "\n-----------------------";

	public static void main(String[] args) {
		LoggerFactory.setDefaultLevel(LOGGING_LEVEL);
		logger = LoggerFactory.createLogger(FreeTriangleMeshToolStarter.class);
		logger.info("Starting tool.");

		EnvironmentImpl env = new EnvironmentImpl();
		FreeTriangleMeshToolStarter tool = new FreeTriangleMeshToolStarter();
		tool.setEnvironment(env);

		try {

			tool.initialize();

			tool.run();

		} catch(GameEngineException ex) {

			handleGameException(ex, tool);

		} catch(Throwable t) {

			logger.error("FATAL error!", t);

		} finally {

			try {

				tool.finish();

			} catch(GameEngineException ex) {

				handleGameException(ex, tool);

			} catch(Throwable t) {

				logger.error("FATAL error!", t);

			}
		}

		System.exit(0);
	}

	public static void handleGameException(GameEngineException ex, FreeTriangleMeshToolStarter superstition) {
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
		logger.info("Creating connector for example program.");
		connector = new FreeTriangleMeshConnector();
		phase = Phase.INITIALIZATION_OVER;
	}

	private void run() {
		phase = Phase.GAME;
		logger.info("Starting environment.");
		connector.run(env);

		logger.info("Waiting for tool to finish.");
		env.waitForExitting();
		logger.info("Exitting tool.");
		phase = Phase.GAME_OVER;
	}

	private void finish() {
		logger.info("Finalization started.");
		phase = Phase.FINALIZATION;
		env.finish();
		connector.dispose();
		logger.info("Finalization finished.");
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
