package hu.csega.game.engine;

public class GameHitBox extends GameHitShape {

	public GameHitBox() {
	}

	public GameHitBox(double minX, double maxX, double minY, double maxY) {
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}

	public double minX;
	public double maxX;
	public double minY;
	public double maxY;

}
