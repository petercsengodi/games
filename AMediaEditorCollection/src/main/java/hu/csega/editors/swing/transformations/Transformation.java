package hu.csega.editors.swing.transformations;

import hu.csega.games.engine.g2d.GamePoint;

public abstract class Transformation {

	public final GamePoint fromModelToCanvas(GamePoint source) {
		GamePoint ret = clone(source);
		fromModelToCanvasInPlace(ret);
		return ret;
	}

	public final GamePoint fromCanvasToModel(GamePoint source) {
		GamePoint ret = clone(source);
		fromCanvasToModelInPlace(ret);
		return ret;
	}

	protected abstract void fromModelToCanvasInPlace(GamePoint ret);

	protected abstract void fromCanvasToModelInPlace(GamePoint ret);

	protected final GamePoint clone(GamePoint gp) {
		return new GamePoint(gp.x, gp.y);
	}
}
