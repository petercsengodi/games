package hu.csega.superstition.util;

import org.joml.Vector4f;

public class StaticMathLibrary {

	public static boolean inSquare(float x, float y, float x1, float y1, float x2, float y2) {
		if ((x < x1) || (x > x2))
			return false;

		if ((y < y1) || (y > y2))
			return false;

		return true;
	}

	public static boolean InBox(Vector4f p, Vector4f c1, Vector4f c2) {
		if(p.w != 1f || c1.w != 1f || c2.w != 1f)
			throw new RuntimeException("Vector weights must be 1f");

		if ((p.x < c1.x) || (p.x > c2.x))
			return false;

		if ((p.y < c1.y) || (p.y > c2.y))
			return false;

		if ((p.z < c1.z) || (p.z > c2.z))
			return false;

		// weights should be 1f

		return true;
	}

	public static Vector4f center(Vector4f corner1, Vector4f corner2, Vector4f ret) {
		if(corner1.w != 1f || corner2.w != 1f)
			throw new RuntimeException("Vector weights must be 1f");

		ret.set(
				(corner1.x + corner2.x) / 2f,
				(corner1.y + corner2.y) / 2f,
				(corner1.z + corner2.z) / 2f,
				1f);

		return ret;
	}

	private StaticMathLibrary() {
	}
}