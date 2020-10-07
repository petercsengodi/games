package hu.csega.editors.transformations.layer1.presentation.swing;

import hu.csega.editors.transformations.layer4.data.TransformationTesterModel;
import hu.csega.games.engine.GameEngineFacade;

public class TransformationTesterXZSideView extends TransformationTesterSideView {

	public TransformationTesterXZSideView(GameEngineFacade facade) {
		super(facade);
	}

	@Override
	protected void translate(double x, double y) {
		TransformationTesterModel model = getModel();
		model.addCanvasXZTranslateX(x);
		model.addCanvasXZTranslateY(y);
	}

	@Override
	protected void zoom(double delta) {
		TransformationTesterModel model = getModel();
		model.addCanvasXZZoom(delta);
	}

	@Override
	protected void calculateTransformationMatrices(double screenWidth, double screenHeight) {
		TransformationTesterModel model = getModel();
		double scale = model.getCanvasXZZoom();
		double translateX = model.getCanvasXZTranslateX();
		double translateY = model.getCanvasXZTranslateY();

		// Calculate model -> screen matrix
		this.screenTransformationMatrix.identity();

		// VIEW -> SCREEN
		this.screenTransformationMatrix.translate(screenWidth / 2.0, screenHeight / 2.0, 0.0);

		// MODEL -> VIEW
		this.screenTransformationMatrix
		.translate(translateX, translateY, 0.0)
		.scale(scale)
		.rotateX(Math.PI / 2.0);

		// Calculate screen -> model matrix
		this.screenTransformationMatrix.invertAffine(this.inverseTransformationMatrix);
	}

	private static final long serialVersionUID = 1L;
}