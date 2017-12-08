package hu.csega.editors.swing.transformations;

import java.util.Arrays;
import java.util.List;

import hu.csega.games.engine.g2d.GamePoint;

public class TransformationPipeline extends Transformation {

	public static TransformationPipeline createPipeline(Transformation... transformations) {
		TransformationPipeline ret = new TransformationPipeline();
		ret.transformations = Arrays.asList(transformations);
		return ret;
	}

	private List<Transformation> transformations;

	private TransformationPipeline() {
	}

	@Override
	protected void fromModelToCanvasInPlace(GamePoint ret) {
		if(transformations != null) {
			for(Transformation transformation : transformations)
				transformation.fromModelToCanvasInPlace(ret);
		}
	}

	@Override
	protected void fromCanvasToModelInPlace(GamePoint ret) {
		if(transformations != null) {
			for(Transformation transformation : transformations)
				transformation.fromCanvasToModelInPlace(ret);
		}
	}

}
