package hu.csega.games.engine.g2d;

public class GameHitCircle extends GameHitShape {

	public GameHitCircle() {
	}

	public GameHitCircle(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}

	public double x;
	public double y;
	public double r;
}
