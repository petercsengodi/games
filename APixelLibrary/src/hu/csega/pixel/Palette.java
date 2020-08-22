package hu.csega.pixel;

public class Palette {

	public static int pixelIndexOf(int ri, int gi, int bi) {
		return ((ri * TONES + gi) * TONES) + bi;
	}

	public static int indexOf(int r, int g, int b, int a) {
		if(a < 255) {
			return INDEX_OF_TRANSPARENT;
		}

		int ri = toneIndexOf(r);
		int gi = toneIndexOf(g);
		int bi = toneIndexOf(b);
		return ((ri * TONES) + gi) * TONES + bi;
	}

	public static int toneIndexOf(int component) {
		if(component <= 0) {
			return 0;
		}

		if(component >= 255) {
			return TONES - 1;
		}

		for(int i = 1; i < TONES; i++) {
			if(component <= INT_TO_COLOR_TONE[i]) {
				return i;
			}
		}

		return 0;
	}

	public static Pixel get(int r, int g, int b, int a) {
		return PIXELS[indexOf(r, g, b, a)];
	}

	public static final int[] INT_TO_COLOR_TONE = new int[] { 0, 31, 63, 95, 127, 159, 191, 223, 255};
	public static final int TONES = INT_TO_COLOR_TONE.length;
	public static final int INDEX_OF_TRANSPARENT = TONES * TONES * TONES;
	public static final int NUMBER_OF_COLORS = INDEX_OF_TRANSPARENT + 1;
	public static final Pixel[] PIXELS = new Pixel[NUMBER_OF_COLORS];

	static {
		int index = 0;
		int pixelIndex;

		for(int ri = 0; ri < TONES; ri++) {
			for(int gi = 0; gi < TONES; gi++) {
				for(int bi = 0; bi < TONES; bi++) {
					Pixel pixel = new Pixel();
					pixel.red = INT_TO_COLOR_TONE[ri];
					pixel.green = INT_TO_COLOR_TONE[gi];
					pixel.blue = INT_TO_COLOR_TONE[bi];
					pixel.alpha = 255;

					pixelIndex = pixelIndexOf(ri, gi, bi);
					PIXELS[pixelIndex] = pixel;
					index++;
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
