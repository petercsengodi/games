package hu.csega.klongun.screen;

public class CustomCharset {

	public static final int CHARACTERS = 256;
	public static final int WIDTH = 9;
	public static final int HEIGHT = 13;

	public CustomCharset(byte[] charsetBytes) {
		if(charsetBytes.length < WIDTH * HEIGHT * CHARACTERS);

		int cursor = 0;
		for(int c = 0; c < CustomCharset.CHARACTERS; c++) {
			for(int y = 0; y < CustomCharset.HEIGHT; y++) {
				for(int x = 0; x < CustomCharset.WIDTH; x++) {
					content[c][y][x] = (charsetBytes[cursor++] & 0xFF);
				}
			}
		}

	} // end of ctr

	public int[][][] content = new int[CHARACTERS][HEIGHT][WIDTH];
}
