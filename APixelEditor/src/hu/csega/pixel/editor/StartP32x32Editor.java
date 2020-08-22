package hu.csega.pixel.editor;

import hu.csega.pixel.editor.p32x32.P32x32Editor;

public class StartP32x32Editor {

	public static final String PIX_FILE = "tmp.p32";
	public static final int MAXIMUM_NUMBER_OF_SHEETS = 100;

	public static void main(String[] args) {
		P32x32Editor editor = new P32x32Editor(PIX_FILE, MAXIMUM_NUMBER_OF_SHEETS, 32, 32);
		editor.startEditor();
	}

}
