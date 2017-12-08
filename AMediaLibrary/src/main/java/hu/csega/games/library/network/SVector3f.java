package hu.csega.games.library.network;

import java.io.Serializable;

import org.joml.Vector3f;

public class SVector3f implements Serializable {

	public static SVector3f fromVector3f(Vector3f v) {
		SVector3f ret = new SVector3f();
		ret.x = v.x();
		ret.y = v.y();
		ret.z = v.z();
		return ret;
	}

	public Vector3f toVector3f() {
		Vector3f ret = new Vector3f(x, y, z);
		return ret;
	}

	private float x, y, z;

	/** Default serial version UID. */
	private static final long serialVersionUID = -1l;
}
