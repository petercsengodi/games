package hu.csega.superstition.swing.transformations;

import hu.csega.games.engine.g2d.GameHitBox;
import hu.csega.games.engine.g2d.GamePoint;

public class StretchTransformation implements Transformation {

	void setBoundaryDifferences(GameHitBox from, GameHitBox to) {
		this.x0 = to.minX - from.minX;
		this.x1 = (to.maxX - to.minX) / (from.maxX - from.minX);
		this.y0 = to.minY - from.minY;
		this.y1 = (to.maxY - to.minY) / (from.maxY - from.minY);
	}

	double x0;
	double x1;
	double y0;
	double y1;

	@Override
	public GamePoint fromModelToCanvas(GamePoint source) {
		GamePoint ret = new GamePoint();
		ret.x = x0 + source.x * x1;
		ret.y = y0 + source.y * y1;
		return ret;
	}

	@Override
	public GamePoint fromCanvasToModel(GamePoint source) {
		GamePoint ret = new GamePoint();
		ret.x = (source.x - x0) / x1;
		ret.y = (source.y - y0) / y1;
		return ret;
	}

}
