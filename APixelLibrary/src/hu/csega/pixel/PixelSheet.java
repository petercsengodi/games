package hu.csega.pixel;

import java.io.Serializable;

public class PixelSheet implements Serializable {

	public PixelSheet() {
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				pixels[x][y] = new Pixel();
			}
		}
	}

	public void copyValuesInto(PixelSheet sheet) {
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				this.pixels[x][y].copyValuesInto(sheet.pixels[x][y]);
				sheet.hits[x][y] = this.hits[x][y];
			}
		}
	}

	public Pixel[][] pixels = new Pixel[WIDTH][HEIGHT];
	public boolean[][] hits = new boolean[WIDTH][HEIGHT];

	public static final int WIDTH = 64;
	public static final int HEIGHT = 64;

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
}
