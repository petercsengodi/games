package hu.csega.alpoc.terrain;

public class TerrainEquation {

	public static double y(double x, double z) {
//		double d2 = (x / 100) * (x / 100) + (z / 100) * (z / 100);
//		return 50*Math.sin(d2) + 50;

		return 125*Math.sin(x/100)*Math.sin(z / 100) + 50;
	}

	public static int colorFromY(double y) {
//		return Math.max(0, Math.min(255, (int)y));

		return (int)(y-50 + 125);
	}

	public static int color(double x, double z) {
		return colorFromY(y(x, z));
	}
}
