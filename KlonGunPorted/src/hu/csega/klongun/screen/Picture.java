package hu.csega.klongun.screen;

public class Picture {

	public Picture(int width, int height, int[] content) {
		this(width, height, 0, content);
	}

	public Picture(int width, int height, int offset, int[] content) {
		if(content.length < width * height + offset)
			throw new IllegalArgumentException("Content array is too small!");

		this.width = width;
		this.height = height;
		this.offset = offset;
		this.content = content;

		calculateMiddleColor();
	}

	public int get(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height) {
			return -1;
		}

		return content[y * width + x + offset];
	}

	public void set(int x, int y, int v) {
		if(x < 0 || x >= width || y < 0 || y >= height) {
			return;
		}

		content[y * width + x + offset] = v;
	}

	public int getMiddleColor() {
		// average would be better, maybe, but not easy to
		// calculate because of using palettes
		return middleColor;
	}

	private void calculateMiddleColor() {
		//		int xx = width / 2;
		//		int yy = height / 2;
		//		middleColor = get(xx, yy);
		middleColor = 7;
	}

	private int width;
	private int height;
	private int[] content;
	private int offset;
	private int middleColor;
}
