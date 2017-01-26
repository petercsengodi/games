package hu.csega.superstition;

import java.io.OutputStreamWriter;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import hu.csega.superstition.env.Environment;
import hu.csega.superstition.env.EnvironmentImpl;
import hu.csega.superstition.env.GameException;
import hu.csega.superstition.env.Phase;

// A Fizz-buzz way of running the game

/**
 * Responsible for running the whole game.
 * A single instance in the JVM, accessible by any components, and
 * links all the components together.
 * However, not static methods are used, as writing a testable code is prio 1!
 */
public class Superstition {

	public static void main(String[] args) {
		EnvironmentImpl env = new EnvironmentImpl();
		Superstition superstition = new Superstition();
		superstition.setEnvironment(env);

		try {

			superstition.initialize();

			superstition.run();

			throw new GameException("Hello", new Exception()).description("This is description.");

		} catch(GameException ex) {

			handleGameException(ex, superstition);

		} catch(Throwable t) {

			logger.fatal("Fatal error during " + superstition.phaseString() + " phase", t);

		} finally {

			try {

				superstition.setPhase(Phase.FINALIZATION);
				env.finish();
				superstition.setPhase(Phase.FINALIZATION_OVER);

			} catch(GameException ex) {

				handleGameException(ex, superstition);

			} catch(Throwable t) {

				logger.fatal("Fatal error during " + superstition.phaseString() + " phase", t);

			}
		}
	}

	public static void handleGameException(GameException ex, Superstition superstition) {
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
		// TODO Auto-generated method stub

		phase = Phase.INITIALIZATION_OVER;
	}

	private void run() {
		phase = Phase.GAME;
		// TODO Auto-generated method stub

		phase = Phase.GAME_OVER;
	}

	private void setEnvironment(Environment env) {
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

	private Phase phase = Phase.ZERO;
	private Environment env;

	private static final String SEPARATOR = "\n-----------------------";
	private static final Logger logger = Logger.getLogger(Superstition.class);

	static {
		ConsoleAppender appender = new ConsoleAppender();
		appender.setName("Simple Console Appender");
		appender.setTarget(ConsoleAppender.SYSTEM_OUT);
		appender.setWriter(new OutputStreamWriter(System.out));
		appender.setLayout(new PatternLayout("%-5p [%t]: %m%n"));
		Logger.getRootLogger().addAppender(appender);
	}
}
