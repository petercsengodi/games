package hu.csega.games.library.network;

public class HostData extends GameObjectData {

	public int ID;
	public int player_count;
	public int limit;
	public String information;

	public HostData() {
		description = "Host Data";
	}

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
}