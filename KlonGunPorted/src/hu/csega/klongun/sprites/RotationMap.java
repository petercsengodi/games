package hu.csega.klongun.sprites;

import static hu.csega.klongun.sprites.RotationMapLibrary.ROTATION_MAP_DIAMETER;
import static hu.csega.klongun.sprites.RotationMapLibrary.ROTATION_MAP_SIZE;
import static hu.csega.klongun.sprites.RotationMapLibrary.rotateX;
import static hu.csega.klongun.sprites.RotationMapLibrary.rotateY;

public class RotationMap {

	RotationMap() {
		// package private constructor
	}

	public int xOf(int x, int y) {
		return vx[x + ROTATION_MAP_SIZE][y + ROTATION_MAP_SIZE];
	}

	public int yOf(int x, int y) {
		return vy[x + ROTATION_MAP_SIZE][y + ROTATION_MAP_SIZE];
	}

	void calculate(double alfa) {
		alfa = -alfa;
		int px, py;

		for(int x = -ROTATION_MAP_SIZE; x <= ROTATION_MAP_SIZE; x++) {
			for(int y = -ROTATION_MAP_SIZE; y <= ROTATION_MAP_SIZE; y++) {
				px = x + ROTATION_MAP_SIZE;
				py = y + ROTATION_MAP_SIZE;
				vx[px][py] = rotateX(x, y, alfa);
				vy[px][py] = rotateY(x, y, alfa);

			}
		}
	}

	private int[][] vx = new int[ROTATION_MAP_DIAMETER][ROTATION_MAP_DIAMETER];
	private int[][] vy = new int[ROTATION_MAP_DIAMETER][ROTATION_MAP_DIAMETER];
}
