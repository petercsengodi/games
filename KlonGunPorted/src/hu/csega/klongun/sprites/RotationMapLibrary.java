package hu.csega.klongun.sprites;

public class RotationMapLibrary {

	public static RotationMap get(double alfa) {
		int index = (int)(alfa / DIVISION);
		while(index >= LENGTH)
			index -= LENGTH;
		while(index < 0)
			index += LENGTH;

		return maps[index];
	}

	public static int rotateX(int x, int y, double alfa) {
		return (int)(Math.cos(alfa) * x + Math.sin(alfa) * y);
	}

	public static int rotateY(int x, int y, double alfa) {
		return (int)(-Math.sin(alfa) * x + Math.cos(alfa) * y);
	}

	public static final double PI2 = 2.0 * Math.PI;
	public static final int LENGTH = 100;
	public static final double DIVISION = PI2 / LENGTH;
	public static final int ROTATION_MAP_SIZE = 40;
	public static final int ROTATION_MAP_DIAMETER = ROTATION_MAP_SIZE * 2 + 1;

	private static RotationMap[] maps = new RotationMap[LENGTH];

	static {

		for(int i = 0; i < LENGTH; i++) {
			double alfa = i * DIVISION;
			RotationMap map = new RotationMap();
			map.calculate(alfa);
			maps[i] = map;
		}

	} // end of static initialization
}
