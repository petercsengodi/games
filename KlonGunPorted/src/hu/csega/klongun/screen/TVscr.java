package hu.csega.klongun.screen;

public class TVscr {

	public static final int WIDTH = 320;
	public static final int HEIGHT = 200;
	public int[] content = new int[HEIGHT * WIDTH];

	public int get(int x, int y) {
		if(x >= WIDTH || y >= HEIGHT) {
			return -1;
		}
		return content[y * WIDTH + x];
	}

	public void set(int x, int y, int color) {
		if(x >= WIDTH || y >= HEIGHT) {
			return;
		}
		content[y * WIDTH + x] = color;
	}

	public void copyTo(TVscr other) {
		System.arraycopy(content, 0, other.content, 0, content.length);
	}
}
