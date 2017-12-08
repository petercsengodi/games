package hu.csega.games.library.network;

public class PublishHost extends GameObjectData {

	public int max_players;
	public MapBuffer map;

	public PublishHost() {
		description = "Publish Host";
	}

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
}
