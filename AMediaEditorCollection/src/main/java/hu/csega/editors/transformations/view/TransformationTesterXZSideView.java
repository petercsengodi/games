package hu.csega.editors.transformations.view;

import org.joml.Matrix4d;

import hu.csega.editors.transformations.model.TransformationTesterModel;
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
	protected void calculateTransformationMatrices() {
		TransformationTesterModel model = getModel();
		double scale = model.getCanvasXZZoom();
		double translateX = model.getCanvasXZTranslateX();
		double translateY = model.getCanvasXZTranslateY();

		// Calculate model -> screen matrix
		this.screenTransformationMatrix = new Matrix4d().scale(scale).translate(translateX, translateY, 0.0);

		// Calculate screen -> model matrix
		this.screenTransformationMatrix.invertAffine(this.inverseTransformationMatrix);
	}

	private static final long serialVersionUID = 1L;
}