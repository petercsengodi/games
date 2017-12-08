package hu.csega.editors.swing.transformations;

import hu.csega.games.engine.g2d.GameHitBox;
import hu.csega.games.engine.g2d.GamePoint;

public class StretchTransformation extends Transformation {

	private double x0;
	private double x1;
	private double y0;
	private double y1;

	public void setBoundaryDifferences(GameHitBox from, GameHitBox to) {
		this.x0 = to.minX - from.minX;
		this.x1 = (to.maxX - to.minX) / (from.maxX - from.minX);
		this.y0 = to.minY - from.minY;
		this.y1 = (to.maxY - to.minY) / (from.maxY - from.minY);
	}

	@Override
	protected void fromModelToCanvasInPlace(GamePoint ret) {
		ret.x = x0 + ret.x * x1;
		ret.y = y0 + ret.y * y1;
	}

	@Override
	protected void fromCanvasToModelInPlace(GamePoint ret) {
		ret.x = (ret.x - x0) / x1;
		ret.y = (ret.y - y0) / y1;
	}

}
