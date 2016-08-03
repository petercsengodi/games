package hu.csega.skeleton.parts;

import static hu.csega.skeleton.parts.SkeletonConstants.DIMENSIONS;

public class Point {

	public Point() {
		position = new double[DIMENSIONS];
	}

	public Point(double x, double y, double z) {
		position = new double[DIMENSIONS];
		position[0] = x;
		position[1] = y;
		position[2] = z;
	}

	public double[] position;

}
