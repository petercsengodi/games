package hu.csega.pixel;

import java.awt.image.BufferedImage;

public class SpriteLibrary {

	public static SpriteLibrary create(PixelLibrary library, int zoom, PixelSpriteResizePolicy policy) {
		SpriteLibrary ret = new SpriteLibrary();
		ret.size = library.size();
		ret.images = library.convertToImages(zoom, policy);
		return ret;
	}

	public BufferedImage get(int index) {
		if(index < 0 || index >= size)
			return null;
		return images[index];
	}

	private SpriteLibrary() {
	}

	private int size;
	private BufferedImage[] images;
}
