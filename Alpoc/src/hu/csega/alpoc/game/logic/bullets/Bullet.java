package hu.csega.alpoc.game.logic.bullets;

import java.awt.Graphics2D;
import java.awt.Point;

import hu.csega.alpoc.game.graphics.Calculations;
import hu.csega.alpoc.game.logic.GameObject;

public abstract class Bullet extends GameObject {

	@Override
	public void render(Graphics2D g, double t) {
		double distance = Calculations.distance(startLocation, endLocation);
		double maxTime = startTime + (distance / speed);
		if(startTime <= t && t <= maxTime) {
			double percentage = (t - startTime) / maxTime;
			Point currentPosition = Calculations.interpolation(startLocation, endLocation, percentage);
			g.translate(-currentPosition.x, -currentPosition.y);
			draw(g);
			g.translate(currentPosition.x, currentPosition.y);
		}
	}
	
	private Point startLocation;
	private Point endLocation;
	private double startTime;
	private double speed;
}
