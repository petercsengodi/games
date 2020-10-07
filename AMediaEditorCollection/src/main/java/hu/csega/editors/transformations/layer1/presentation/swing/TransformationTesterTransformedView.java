package hu.csega.editors.transformations.layer1.presentation.swing;

import hu.csega.editors.transformations.layer4.data.TransformationTesterModel;
import hu.csega.games.engine.GameEngineFacade;

public class TransformationTesterTransformedView extends TransformationTesterSideView {

	public TransformationTesterTransformedView(GameEngineFacade facade) {
		super(facade);
	}

	@Override
	protected void translate(double x, double y) {
		TransformationTesterModel model = getModel();
		model.addCustomViewTranslateX(x);
		model.addCustomViewTranslateY(y);

		// FIXME: rotation
	}

	@Override
	protected void zoom(double delta) {
		TransformationTesterModel model = getModel();
		model.addCustomViewZoom(delta);
	}

	@Override
	protected void calculateTransformationMatrices(double screenWidth, double screenHeight) {
		TransformationTesterModel model = getModel();
		double scale = model.getCustomViewZoom();
		double translateX = model.getCustomViewTranslateX();
		double translateY = model.getCustomViewTranslateY();

		// Calculate model -> screen matrix
		this.screenTransformationMatrix.identity();

		// VIEW -> SCREEN
		this.screenTransformationMatrix.translate(screenWidth / 2.0, screenHeight / 2.0, 0.0);

		// MODEL -> VIEW
		this.screenTransformationMatrix
		.translate(translateX, translateY, 0.0)
		.scale(scale);


		// Calculate screen -> model matrix
		this.screenTransformationMatrix.invertAffine(this.inverseTransformationMatrix);
	}

	private static final long serialVersionUID = 1L;
}