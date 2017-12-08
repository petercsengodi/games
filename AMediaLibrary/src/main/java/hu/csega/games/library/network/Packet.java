package hu.csega.games.library.network;

import java.io.Serializable;

public class Packet implements Serializable {
	public int sender;
	public long counter;
	public GameObjectData data;

	/** Default serial version UID. */
	private static final long serialVersionUID = 1L;
}