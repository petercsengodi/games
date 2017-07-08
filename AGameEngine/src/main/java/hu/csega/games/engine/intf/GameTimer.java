package hu.csega.games.engine.intf;

/**
 * Helper for the modifying step to read easily, how much time is spent
 * sincs the last modifying step.
 */
public interface GameTimer {

	/** Measurement in seconds. */
	double elapsedTime();

	/** Measurement in nanoseconds. */
	long getNanoTimeOnLastCall();

	/** Measurement in nanoseconds. */
	long getNanoTimeNow();

}
