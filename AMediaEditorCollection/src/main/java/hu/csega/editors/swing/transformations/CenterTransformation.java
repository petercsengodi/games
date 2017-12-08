package hu.csega.editors.swing.transformations;

import hu.csega.games.engine.g2d.GamePoint;

/**
 * Converts position so that (0;0) point falls to the center of the canvas.
 * This also means, that the original (0;0) position of a canvas
 * of the size 200x100 becomes (-100;-50) (approximately).
 */
public class CenterTransformation extends Transformation {

	private double centerX;
	private double centerY;

	public CenterTransformation() {
	}

	public void setCanvasSize(double canvasWidth, double canvasHeight) {
		this.centerX = canvasWidth / 2.0;
		this.centerY = canvasHeight / 2.0;
	}

	@Override
	protected void fromModelToCanvasInPlace(GamePoint ret) {
		ret.x += centerX;
		ret.y += centerY;
	}

	@Override
	protected void fromCanvasToModelInPlace(GamePoint ret) {
		ret.x -= centerX;
		ret.y -= centerY;
	}

}
