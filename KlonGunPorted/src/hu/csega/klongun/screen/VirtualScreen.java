package hu.csega.klongun.screen;

import java.util.Arrays;

public class VirtualScreen {

	public static final int WIDTH = 320;
	public static final int HEIGHT = 200;

	public int get(int x, int y) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
			return -1;
		}
		return content[y * WIDTH + x];
	}

	public void set(int x, int y, int color) {
		if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
			return;
		}
		content[y * WIDTH + x] = color;
	}

	public void copyTo(VirtualScreen other) {
		System.arraycopy(content, 0, other.content, 0, content.length);
	}

    public void clear(){
    	clear(0);
    }

    public void clear(int col){
    	Arrays.fill(content, col);
    }

    public int[] getContent() {
    	return content;
    }

	private int[] content = new int[HEIGHT * WIDTH];
}
