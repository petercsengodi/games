package hu.csega.alpoc.terrain;

import hu.csega.alpoc.frame.AlpocCanvas;

public class ScreenConversion {

	public static ScreenConversion withDefaults(int width, int height) {
		ScreenConversion ret = new ScreenConversion(width, height);
		ret.pixelSizes(0.002, 0.002);
		ret.distance(0.1);
		ret.calculateRemaining();
		return ret;
	}

	public ScreenConversion(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void pixelSizes(double px, double py) {
		this.px = px;
		this.py = py;
	}

	public void distance(double d) {
		this.d = d;
	}

	public void calculateRemaining() {
		xmax = width * px / 2.0;
		xmin = -xmax;
		ymax = height * py / 2.0;
		ymin = -ymax;

		d2 = d*d;
	}

	public void calculateSee() {
		sdx = sx * px + xmin;
		sdy = sy * py + ymin;

		// calculate real distance from the screen pixel
		srd2 = sdx*sdx + sdy*sdy;
		rsd = Math.sqrt(srd2 + d2);

		// calculating, if the terrain would be flat,
		// which distance would belong to the pixel of screen.
		seez = C.y * d / sdy;

		seex = seez * sdx / d;

		// inverse world transformation
		seeposx = C.r11*seex + C.r21*seez - C.x;
		seeposz = C.r12*seex + C.r22*seez - C.z;

		tdist = Math.sqrt(seex*seex + seez*seez);
	}

	public void calculateResult() {
		resulty = (C.y - ty) * rsd / tdist;
		screeny = (resulty + ymax) / py;
	}

	public int width;
	public int height;
	public double px;
	public double py;
	public double d;
	public double d2;

	public double xmin, ymin;
	public double xmax, ymax;


	// these always change for calculations
	public static Camera C = Camera.CAMERA; // input
	public int sx, sy; // input
	public double sdx, sdy; // semi-result - double typed coordinates on screen
	public double srx, sry, srd2; // semi-result - double typed coordinates if the screen were a real screen in the space
	public double rsd; // real screen distance in real space
	public double seed; // see distance
	public double seex, seey, seez; // output
	public double seeposx, seeposz;

	public double tdist, ty;
	public double resulty;
	public double screeny;

	public static ScreenConversion SC = withDefaults(
			AlpocCanvas.PREFERRED_SIZE.width,
			AlpocCanvas.PREFERRED_SIZE.height);
}
