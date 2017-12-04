package hu.csega.superstition.swing.transformations;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.engine.g2d.GamePoint;

public class TransformationPipeline implements Transformation {

	List<Transformation> transformations = new ArrayList<>();

	@Override
	public GamePoint fromModelToCanvas(GamePoint source) {
		GamePoint tmp = source;

		for(Transformation transformation : transformations)
			tmp = transformation.fromModelToCanvas(tmp);

		return tmp;
	}

	@Override
	public GamePoint fromCanvasToModel(GamePoint source) {
		GamePoint tmp = source;

		for(Transformation transformation : transformations)
			tmp = transformation.fromCanvasToModel(tmp);

		return tmp;
	}
}
