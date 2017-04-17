package hu.csega.games.engine;

import java.awt.image.BufferedImage;

import hu.csega.games.engine.g2d.GameColor;
import hu.csega.games.engine.g2d.GameHitShape;
import hu.csega.games.engine.g2d.GamePoint;
import hu.csega.games.engine.g2d.GameSprite;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameObjectHandler;

public interface GameGraphics {

	void rotate(double angle);
	void translate(double tx, double ty);

	void crossHair(double x, double y);
	void drawTriangleStrip(GameColor color, GamePoint... gamePoints);
	void drawTriangles(GameColor[] colors, GamePoint[] gamePoints);
	void drawSprite(GameSprite sprite, double x, double y);
	void drawSprite(BufferedImage image, double x, double y);
	void drawHitShape(GameHitShape hitShape, double x, double y, GameColor color);

	GameObjectHandler loadTexture(String filename);
	GameObjectHandler buildModel(GameModelBuilder builder);
	GameObjectHandler loadModel(String filename);

	void startFrame();
	void placeCamera()
	void drawModel()

	void dispose(GameObjectHandler handler);
	void disposeAll();
}
