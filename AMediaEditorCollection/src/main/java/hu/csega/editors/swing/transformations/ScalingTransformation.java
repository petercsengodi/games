package hu.csega.editors.swing.transformations;

import hu.csega.games.engine.g2d.GamePoint;

public class ScalingTransformation extends Transformation {

	private double factorX = 1.0;
	private double factorY = 1.0;

	public ScalingTransformation() {
	}

	public void scale(double factor) {
		this.scale(factor, factor);
	}

	public void scale(double factorX, double factorY) {
		this.factorX = factorX;
		this.factorY = factorY;
	}

	@Override
	protected void fromModelToCanvasInPlace(GamePoint ret) {
		ret.x *= factorX;
		ret.y *= factorY;
	}

	@Override
	protected void fromCanvasToModelInPlace(GamePoint ret) {
		ret.x /= factorX;
		ret.y /= factorY;
	}

}
