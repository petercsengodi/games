package hu.csega.superstition.swing.transformations;

import hu.csega.games.engine.g2d.GamePoint;

/**
 * Converts position so that (0;0) point falls to the center of the canvas.
 * This also means, that the original (0;0) position of a canvas
 * of the size 200x100 becomes (-100;-50) (approximately).
 */
public class CenterTransformation implements Transformation {

	private double centerX;
	private double centerY;

	CenterTransformation() {
	}

	void setCanvasSize(double canvasWidth, double canvasHeight) {
		this.centerX = canvasWidth / 2.0;
		this.centerY = canvasHeight / 2.0;
	}

	@Override
	public GamePoint fromModelToCanvas(GamePoint source) {
		GamePoint ret = new GamePoint();
		ret.x = source.x + centerX;
		ret.y = source.y + centerY;
		return ret;
	}

	@Override
	public GamePoint fromCanvasToModel(GamePoint source) {
		GamePoint ret = new GamePoint();
		ret.x = source.x - centerX;
		ret.y = source.y - centerY;
		return ret;
	}

}
