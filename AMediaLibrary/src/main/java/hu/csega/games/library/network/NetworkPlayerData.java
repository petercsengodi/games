package hu.csega.games.library.network;

public class NetworkPlayerData extends GameObjectData {

	public SVector3f position, difference;

	public NetworkPlayerData() {
		description = "NetPlayer";
	}

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
}
