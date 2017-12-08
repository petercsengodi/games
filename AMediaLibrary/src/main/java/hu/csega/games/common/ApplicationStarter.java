package hu.csega.games.common;

import hu.csega.games.engine.env.EnvironmentImpl;
import hu.csega.games.engine.env.GameEngineException;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class ApplicationStarter {

	private Connector connector;
	private Phase phase = Phase.ZERO;
	private EnvironmentImpl env;

	public ApplicationStarter(Connector connector) {
		this.connector = connector;
	}

	public void start(String[] args) {
		logger.info("Creating and setting environment.");

		EnvironmentImpl env = new EnvironmentImpl();
		this.setEnvironment(env);

		try {

			this.initialize();

			this.run();

		} catch(GameEngineException ex) {

			handleGameException(ex, this);

		} catch(Throwable t) {

			logger.error("FATAL error!", t);

		} finally {

			try {

				this.finish();

			} catch(GameEngineException ex) {

				handleGameException(ex, this);

			} catch(Throwable t) {

				logger.error("FATAL error!", t);

			}
		}

		System.exit(0);
	}

	public static void handleGameException(GameEngineException ex, ApplicationStarter superstition) {
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
		connector.initialize();
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

	private static final String SEPARATOR = "\n-----------------------";

	private static final Logger logger = LoggerFactory.createLogger(ApplicationStarter.class);
}
