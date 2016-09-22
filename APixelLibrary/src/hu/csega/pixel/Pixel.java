package hu.csega.pixel;

import java.awt.Color;
import java.io.Serializable;

public class Pixel implements Serializable {

	public int red;
	public int green;
	public int blue;
	public int alpha;

	public Color convertToColor() {
		Color color = new Color(red, green, blue, alpha);
		return color;
	}

	public void copyValuesInto(Pixel pixel) {
		pixel.red = this.red;
		pixel.green = this.green;
		pixel.blue = this.blue;
		pixel.alpha = this.alpha;

	}

	public Pixel copy() {
		Pixel ret = new Pixel();
		copyValuesInto(ret);
		return ret;
	}

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
}
