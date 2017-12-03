package hu.csega.superstition.swing.transformations;

import hu.csega.games.engine.g2d.GamePoint;

public class TranslationTransformation implements Transformation {

	double tx;
	double ty;

	void setTranslation(double tx, double ty) {
		this.tx = tx;
		this.ty = ty;
	}

	@Override
	public GamePoint fromModelToCanvas(GamePoint source) {
		GamePoint ret = new GamePoint();
		ret.x = source.x + tx;
		ret.y = source.y + ty;
		return ret;
	}

	@Override
	public GamePoint fromCanvasToModel(GamePoint source) {
		GamePoint ret = new GamePoint();
		ret.x = source.x - tx;
		ret.y = source.y - ty;
		return ret;
	}

}
