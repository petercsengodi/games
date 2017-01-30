package hu.csega.klongun.screen;

public class Picture {

	public Picture(int width, int height) {
		this.width = width;
		this.height = height;
		this.content = new int[height * width];
		this.offset = 0;
	}

	public Picture(int width, int height, int offset, int[] content) {
		this.width = width;
		this.height = height;
		this.offset = offset;
		this.content = content;
	}

	public int get(int x, int y) {
		if(x >= width || y >= height) {
			return -1;
		}

		return content[y * width + x + offset];
	}

	public void set(int x, int y, int v) {
		if(x >= width || y >= height) {
			return;
		}

		content[y * width + x + offset] = v;
	}

	public int width;
	public int height;
	public int[] content;
	public int offset;
}
