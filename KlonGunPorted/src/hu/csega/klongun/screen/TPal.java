package hu.csega.klongun.screen;

public class TPal {

	public static final int COLORS = 256;
	public static final int COMPONENTS = 3;

	public int get(int color, int component) {
		return content[color * COMPONENTS + component];
	}

	public void set(int color, int component, int value) {
		content[color * COMPONENTS + component] = value;
	}

	public int[] content = new int[COLORS * COMPONENTS];

	public void copyTo(TPal other) {
		System.arraycopy(content, 0, other.content, 0, content.length);
	}
}
