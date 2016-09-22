package hu.csega.pixel;

public class Palette {

	public static int indexOf(int r, int g, int b, int a) {
		if(a < 255) {
			return INDEX_OF_TRANSPARENT;
		}

		int divider = 256 / (TONES - 1);

		int ri = r + 1 / divider;
		if(ri > TONES - 1)
			ri = TONES - 1;
		if(ri < 0)
			ri = 0;

		int gi = g + 1 / divider;
		if(gi > TONES - 1)
			gi = TONES - 1;
		if(gi < 0)
			gi = 0;

		int bi = b + 1 / divider;
		if(bi > TONES - 1)
			bi = TONES - 1;
		if(bi < 0)
			bi = 0;

		return ((r * TONES) + g) * TONES + b;
	}

	public static Pixel get(int r, int g, int b, int a) {
		return PIXELS[indexOf(r, g, b, a)];
	}

	public static final int TONES = 5;
	public static final int[] INT_TO_COLOR_TONE = new int[] { 0, 63, 127, 191, 255};
	public static final int INDEX_OF_TRANSPARENT = TONES * TONES * TONES;
	public static final int NUMBER_OF_COLORS = INDEX_OF_TRANSPARENT + 1;
	public static final Pixel[] PIXELS = new Pixel[NUMBER_OF_COLORS];

	static {
		int index = 0;
		for(int r = 0; r < TONES; r++) {
			for(int g = 0; g < TONES; g++) {
				for(int b = 0; b < TONES; b++) {
					Pixel pixel = new Pixel();
					pixel.red = INT_TO_COLOR_TONE[r];
					pixel.green = INT_TO_COLOR_TONE[g];
					pixel.blue = INT_TO_COLOR_TONE[b];
					pixel.alpha = 255;
					PIXELS[index++] = pixel;
				}
			}
		}

		Pixel pixel = new Pixel();
		pixel.red = 0;
		pixel.green = 0;
		pixel.blue = 0;
		pixel.alpha = 0;
		PIXELS[index++] = pixel;
	}
}
