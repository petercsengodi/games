package hu.csega.game.engine;

import java.awt.image.BufferedImage;

public interface GameGraphics {

	void crossHair(double x, double y);
	void drawTriangleStrip(GameColor color, GamePoint... gamePoints);
	void drawTriangles(GameColor[] colors, GamePoint[] gamePoints);
	void drawSprite(GameSprite sprite, double x, double y);
	void drawSprite(BufferedImage image, double x, double y);
	void drawHitShape(GameHitShape hitShape, double x, double y, GameColor color);

}
