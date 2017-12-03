package hu.csega.superstition.swing.transformations;

import hu.csega.games.engine.g2d.GamePoint;

public class ScalingTransformation implements Transformation {

	double factor = 1.0;

	ScalingTransformation() {
	}

	@Override
	public GamePoint fromModelToCanvas(GamePoint source) {
		GamePoint ret = new GamePoint();
		ret.x = source.x * 2.0;
		ret.y = source.y * 2.0;
		return ret;
	}

	@Override
	public GamePoint fromCanvasToModel(GamePoint source) {
		GamePoint ret = new GamePoint();
		ret.x = source.x / 2.0;
		ret.y = source.y / 2.0;
		return ret;
	}

}
