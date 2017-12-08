package hu.csega.editors.ftm.util;

import hu.csega.editors.ftm.model.FreeTriangleMeshPoint;

public class FreeTriangleMeshMathLibrary {

	public static double distance(FreeTriangleMeshPoint p1, FreeTriangleMeshPoint p2) {
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		double dz = p1.getZ() - p2.getZ();

		return Math.sqrt(dx*dx + dy*dy + dz*dz);
	}

	public static FreeTriangleMeshPoint avg(FreeTriangleMeshPoint p1, FreeTriangleMeshPoint p2) {
		double px = p1.getX() + p2.getX();
		double py = p1.getY() + p2.getY();
		double pz = p1.getZ() + p2.getZ();

		return new FreeTriangleMeshPoint(px / 2.0, py / 2.0, pz / 2.0);
	}

	public static FreeTriangleMeshPoint avg(FreeTriangleMeshPoint p1, FreeTriangleMeshPoint p2, FreeTriangleMeshPoint p3) {
		double px = p1.getX() + p2.getX() + p3.getX();
		double py = p1.getY() + p2.getY() + p3.getY();
		double pz = p1.getZ() + p2.getZ() + p3.getZ();

		return new FreeTriangleMeshPoint(px / 3.0, py / 3.0, pz / 3.0);
	}
}
