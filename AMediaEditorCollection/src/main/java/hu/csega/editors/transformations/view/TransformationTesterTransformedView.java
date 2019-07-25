package hu.csega.editors.transformations.view;

import org.joml.Matrix4d;

import hu.csega.editors.transformations.model.TransformationTesterModel;
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
	protected void calculateTransformationMatrices() {
		TransformationTesterModel model = getModel();
		double scale = model.getCustomViewZoom();
		double translateX = model.getCustomViewTranslateX();
		double translateY = model.getCustomViewTranslateY();
		this.screenTransformationMatrix = new Matrix4d().rotateY(Math.PI / 8.0).scale(scale).translate(translateX, translateY, 0.0);
		this.screenTransformationMatrix.invertAffine(this.inverseTransformationMatrix);

		System.out.println("SCREEN: " + screenTransformationMatrix);
		System.out.println("INVERSE: " + inverseTransformationMatrix);
	}

	private static final long serialVersionUID = 1L;
}