package hu.csega.superstition.swing.transformations;

import hu.csega.games.engine.g2d.GamePoint;

public interface Transformation {

	GamePoint fromModelToCanvas(GamePoint source);

	GamePoint fromCanvasToModel(GamePoint source);

}
