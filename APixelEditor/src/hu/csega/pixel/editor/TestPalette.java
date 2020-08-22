package hu.csega.pixel.editor;

import hu.csega.pixel.Palette;
import hu.csega.pixel.Pixel;

public class TestPalette {

	public static void main(String[] args) throws Exception {
		int r = 92;
		int g = 203;
		int b = 255;

		int index = Palette.indexOf(r, g, b, 255);
		System.out.println("Index: " + index);

		Pixel pixel = Palette.get(r, g, b, 255);
		System.out.println("Pixel: " + pixel);
	}

}
