package hu.csega.games.engine.intf;

import java.awt.image.BufferedImage;

import hu.csega.games.engine.g2d.GameColor;
import hu.csega.games.engine.g2d.GameHitShape;
import hu.csega.games.engine.g2d.GamePoint;
import hu.csega.games.engine.g2d.GameSprite;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectLocation;

public interface GameGraphics {

	int screenWidth();
	int screenHeight();

	void rotate(double angle);
	void translate(double tx, double ty);

	void crossHair(double x, double y);
	void drawTriangleStrip(GameColor color, GamePoint... gamePoints);
	void drawTriangles(GameColor[] colors, GamePoint[] gamePoints);
	void drawSprite(GameSprite sprite, double x, double y);
	void drawSprite(BufferedImage image, double x, double y);
	void drawHitShape(GameHitShape hitShape, double x, double y, GameColor color);

	void startFrame();
	void placeCamera(GameObjectLocation cameraLocation);
	void drawModel(GameObjectHandler modelReference, GameObjectLocation modelLocation);
	void drawAnimation(GameObjectHandler animationReference, int state, GameObjectLocation modelLocation);
	void endFrame();
}
