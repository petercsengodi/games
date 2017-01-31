package hu.csega.klongun.screen;

public class Palette {

	public static final int COLORS = 256;
	public static final int COMPONENTS = 3;

	public Palette() {
	}

	public Palette(byte[] paletteBytes) {
		int cursor = 0;
		for(int c = 0; c < Palette.COLORS; c++) {
			for(int component = 0; component < Palette.COMPONENTS; component++) {
				content[c * COMPONENTS + component] = (paletteBytes[cursor++] & 0xFF);
			}
		}
	}

	public int get(int color, int component) {
		if(color < 0 || color >= COLORS || component < 0 || component >= COMPONENTS) {
			return -1;
		}

		return content[color * COMPONENTS + component];
	}

	public void set(int color, int component, int value) {
		if(color < 0 || color >= COLORS || component < 0 || component >= COMPONENTS) {
			return;
		}

		content[color * COMPONENTS + component] = value;
	}

	public void copyTo(Palette other) {
		System.arraycopy(content, 0, other.content, 0, content.length);
	}

	private int[] content = new int[COLORS * COMPONENTS];
}
