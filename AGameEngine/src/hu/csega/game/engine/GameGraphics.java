package hu.csega.game.engine;

public interface GameGraphics {

	void crossHair(double x, double y);
	void drawTriangleStrip(GameColor color, GamePoint... gamePoints);
	void drawTriangles(GameColor[] colors, GamePoint[] gamePoints);

}
