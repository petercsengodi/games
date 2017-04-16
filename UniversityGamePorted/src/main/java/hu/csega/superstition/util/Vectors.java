package hu.csega.superstition.util;

import org.joml.Vector3f;

public class Vectors {

	public static void minimize(Vector3f a, Vector3f b, Vector3f to) {
		to.x = Math.min(a.x, b.x);
		to.y = Math.min(a.y, b.y);
		to.z = Math.min(a.z, b.z);
	}

	public static void maximize(Vector3f a, Vector3f b, Vector3f to) {
		to.x = Math.max(a.x, b.x);
		to.y = Math.max(a.y, b.y);
		to.z = Math.max(a.z, b.z);
	}

}
