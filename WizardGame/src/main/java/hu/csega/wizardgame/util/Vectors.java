package hu.csega.wizardgame.util;

import org.joml.Vector4f;

public class Vectors {

	public static void minimize(Vector4f a, Vector4f b, Vector4f to) {
		to.x = Math.min(a.x, b.x);
		to.y = Math.min(a.y, b.y);
		to.z = Math.min(a.z, b.z);
		to.w = Math.min(a.w, b.w);
	}

	public static void maximize(Vector4f a, Vector4f b, Vector4f to) {
		to.x = Math.max(a.x, b.x);
		to.y = Math.max(a.y, b.y);
		to.z = Math.max(a.z, b.z);
		to.w = Math.max(a.w, b.w);
	}

	public static float distance(Vector4f a, Vector4f b) {
		return a.distance(b);
	}
}
