package hu.csega.superstition.gamelib.legacy.migration;

public class MigrationException extends Exception {

	public MigrationException() {
	}

	public MigrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public MigrationException(String message) {
		super(message);
	}

	public MigrationException(Throwable cause) {
		super(cause);
	}

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

}
