package hu.csega.games.engine.intf;

import java.awt.image.BufferedImage;

import hu.csega.games.engine.g2d.GameColor;
import hu.csega.games.engine.g2d.GameHitShape;
import hu.csega.games.engine.g2d.GamePoint;
import hu.csega.games.engine.g2d.GameSprite;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameSelectionLine;
import hu.csega.games.engine.g3d.GameTransformation;

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
	void placeCamera(GameObjectPlacement cameraLocation);
	void drawModel(GameObjectHandler modelReference, GameObjectPlacement modelLocation);
	void drawModel(GameObjectHandler modelReference, GameTransformation transformation);
	void drawAnimation(GameObjectHandler animationReference, int state, GameObjectPlacement modelLocation);
	void endFrame();

	void setBaseMatricesAndViewPort(GameSelectionLine selectionLine);

}
