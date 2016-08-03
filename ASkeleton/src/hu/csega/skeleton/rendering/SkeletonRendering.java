package hu.csega.skeleton.rendering;

import static hu.csega.skeleton.parts.SkeletonConstants.DIMENSIONS;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import hu.csega.skeleton.parts.Point;

public class SkeletonRendering {

	public static Point add(Point a, Point b) {
		Point res = new Point();

		for(int d = 0; d < DIMENSIONS; d++) {
			res.position[d] = a.position[d] + b.position[d];
		}

		return res;
	}

	public static Point invert(Point p) {
		Point res = new Point();

		for(int d = 0; d < DIMENSIONS; d++) {
			res.position[d] = - p.position[d];
		}

		return res;
	}

	public static Point rotate(Point point, double[] angles) {
		Point res1 = new Point();
		res1.position[0] = rotateX(point.position[0], point.position[1], angles[0]);
		res1.position[1] = rotateY(point.position[0], point.position[1], angles[0]);
		res1.position[2] = point.position[2];

		Point res2 = new Point();
		res2.position[0] = rotateX(res1.position[0], res1.position[2], angles[1]);
		res2.position[1] = res1.position[1];
		res2.position[2] = rotateY(res1.position[0], res1.position[2], angles[1]);

		Point res3 = new Point();
		res3.position[0] = res2.position[0];
		res3.position[1] = rotateX(res2.position[1], res2.position[2], angles[2]);
		res3.position[2] = rotateY(res2.position[1], res2.position[2], angles[2]);

		return res3;
	}

	private static double rotateX(double x, double y, double angle) {
		return x * cos(angle) - y * sin(angle);
	}

	private static double rotateY(double x, double y, double angle) {
		return x * sin(angle) + y * cos(angle);
	}

}
