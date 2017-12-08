package hu.csega.games.library.animationdata;

import org.joml.Vector3f;

public class CNamedConnection {
	public String name;
	public int object_index;
	public int connection_index;
	public Vector3f point;

	public CNamedConnection() {
		this.name = null;
		this.object_index = -1;
		this.connection_index = -1;
		this.point = new Vector3f(0f, 0f, 0f);
	}

	public CNamedConnection(String name, Vector3f point, int object_index, int connection_index) {
		this.name = name;
		this.point = point;
		this.object_index = object_index;
		this.connection_index = connection_index;
	}
}