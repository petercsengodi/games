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

	private int width;
	private int height;
	private int[] content;
	private int offset;
}
