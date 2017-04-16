package hu.csega.games.engine;

public class GameField {

	public GameField() {
	}

	public GameField(double minX, double maxX, double minY, double maxY) {
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
