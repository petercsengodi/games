package hu.csega.update.test;

import hu.csega.alpoc.terrain.ScreenConversion;
import hu.csega.alpoc.terrain.TerrainEquation;

public class ScreenConversionTest {

	public static void main(String[] args) {
		ScreenConversion sc = ScreenConversion.withDefaults(800, 600);
		sc.sx = 400;
		sc.sy = 500;

		sc.calculateSee();

		System.out.println(sc.seeposx);
		System.out.println(sc.seeposz);

		double color = TerrainEquation.color(sc.seeposx, sc.seeposz);
		System.out.println(color);
	}

}
