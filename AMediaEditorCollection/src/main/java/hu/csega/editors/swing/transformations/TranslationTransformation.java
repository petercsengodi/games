package hu.csega.editors.swing.transformations;

import hu.csega.games.engine.g2d.GamePoint;

public class TranslationTransformation extends Transformation {

	private double tx;
	private double ty;

	public void setTranslation(double tx, double ty) {
		this.tx = tx;
		this.ty = ty;
	}

	@Override
	protected void fromModelToCanvasInPlace(GamePoint ret) {
		ret.x = ret.x + tx;
		ret.y = ret.y + ty;
	}

	@Override
	protected void fromCanvasToModelInPlace(GamePoint ret) {
		ret.x = ret.x - tx;
		ret.y = ret.y - ty;
	}

}
