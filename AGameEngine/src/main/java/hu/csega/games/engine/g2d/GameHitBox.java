package hu.csega.games.engine.g2d;

public class GameHitBox extends GameHitShape {

	public GameHitBox() {
	}

	public GameHitBox(double minX, double minY, double maxX, double maxY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public double minX;
	public double minY;
	public double maxX;
	public double maxY;

}
