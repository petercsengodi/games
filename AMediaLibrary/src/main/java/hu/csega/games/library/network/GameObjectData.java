package hu.csega.games.library.network;

import java.io.Serializable;

public class GameObjectData implements Serializable {

	public GameObjectData() {
		description = null;
	}

	public GameObjectData(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	protected String description;

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;

}