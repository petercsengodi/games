package hu.csega.games.library.network;

public class UserInfo extends GameObjectData {

	public long userID;
	public int game_port;
	public int client_port;

	public UserInfo() {
		description = "User Info";
	}

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
}