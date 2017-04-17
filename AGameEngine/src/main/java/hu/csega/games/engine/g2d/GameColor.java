package hu.csega.games.engine.g2d;

public class GameColor {

	public GameColor() {
	}

	public GameColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 255;
	}

	public GameColor(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public int r;
	public int g;
	public int b;
	public int a;

}
