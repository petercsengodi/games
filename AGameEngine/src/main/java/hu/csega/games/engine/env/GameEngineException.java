package hu.csega.games.engine.env;

public class GameEngineException extends RuntimeException {

	public GameEngineException() {
	}

	public GameEngineException(String message) {
		super(message);
	}

	public GameEngineException(Throwable cause) {
		super(cause);
	}

	public GameEngineException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameEngineException description(String desc) {
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
