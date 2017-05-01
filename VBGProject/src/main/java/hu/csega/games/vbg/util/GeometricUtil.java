package hu.csega.games.vbg.util;

import java.awt.Rectangle;

public class GeometricUtil {

	public static Rectangle normalizeRectangle(Rectangle original) {
		Rectangle rect = new Rectangle();

		if (original.width > 0) {
			rect.x = original.x;
			rect.width = original.width;
		} else {
			rect.x = original.x + original.width;
			rect.width = -original.width;
		}

		if (original.height > 0) {
			rect.y = original.y;
			rect.height = original.height;
		} else {
			rect.y = original.y + original.height;
			rect.height = -original.height;
		}

		return rect;
	}

	public static double distance(int x1, int y1, int x2, int y2) {
		double d1 = (x2 - x1) * (x2 - x1);
		double d2 = (y2 - y1) * (y2 - y1);
		return Math.sqrt(d1 + d2);
	}

	public static boolean almostEquals(double d1, double d2) {
		return Math.abs(d1 - d2) < 0.0001;
	}

	public static boolean inRect(int x, int y, Rectangle rect) {
		Rectangle r2;

		if(rect.width < 0 || rect.height < 0)
			r2 = normalizeRectangle(rect);
		else
			r2 = rect;

		return inRect(x, y, r2.x, r2.y, r2.x + r2.width, r2.y + r2.height);
	}

	public static boolean inRect(int x, int y, int x1, int y1, int x2, int y2) {
		return (x >= x1 && x <= x2 && y >= y1 && y <= y2);
	}
}
