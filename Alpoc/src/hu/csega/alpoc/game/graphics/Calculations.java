package hu.csega.alpoc.game.graphics;

import java.awt.Point;

public class Calculations {

	public static double distance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p2.x - p1.x, 2.0) + Math.pow(p2.y - p1.y, 2.0)); 
	}
	
	public static Point interpolation(Point start, Point end, double percentage) {
		double newX = start.x + (end.x - start.x) * percentage;
		double newY = start.y + (end.y - start.y) * percentage;
		return new Point((int)newX, (int)newY);
	}
}
