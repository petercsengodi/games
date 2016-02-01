package hu.csega.dyn;

public enum RestartLevel {

	/**
	 * Completely shutting down the whole system.
	 */
	LEVEL_0,
	
	/**
	 * Shutting down the inner process, and tries to start a brand new process.
	 */
	LEVEL_1,
	
	/**
	 * Drops the old classes, loads the new ones, but all memory content is destroyed.
	 */
	LEVEL_2,
	
	/**
	 * Drops the old classes, loads the new ones, marked memory content is preserved.
	 */
	LEVEL_3;
}
