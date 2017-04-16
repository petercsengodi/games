package hu.csega.superstition.env;

public class GameException extends RuntimeException {

	public GameException() {
	}

	public GameException(String message) {
		super(message);
	}

	public GameException(Throwable cause) {
		super(cause);
	}

	public GameException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameException description(String desc) {
		if(desc == null || desc.isEmpty())
			this.description = null;
		else
			this.description = desc;

		return this;
	}

	public String getDescription() {
		return description;
	}

	private String description;

	/** Default */
	private static final long serialVersionUID = 1L;

}
