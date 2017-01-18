package hu.csega.superstition.util;

import org.joml.Vector3f;

public class StaticMathLibrary {

	public static boolean inSquare(float x, float y, float x1, float y1, float x2, float y2) {
		if ((x < x1) || (x > x2))
			return false;
		if ((y < y1) || (y > y2))
			return false;

		return true;
	}

	public static boolean InBox(Vector3f p, Vector3f c1, Vector3f c2) {
		if ((p.x < c1.x) || (p.x > c2.x))
			return false;
		if ((p.y < c1.y) || (p.y > c2.y))
			return false;
		if ((p.z < c1.z) || (p.z > c2.z))
			return false;

		return true;
	}

	public static Vector3f center(Vector3f _corner1, Vector3f _corner2) {
		return new Vector3f(
				(_corner1.x + _corner2.x) / 2f,
				(_corner1.y + _corner2.y) / 2f,
				(_corner1.z + _corner2.z) / 2f);
	}

	private StaticMathLibrary() {
	}
}