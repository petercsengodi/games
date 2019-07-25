package hu.csega.editors.transformations.view;

import org.joml.Matrix4d;

import hu.csega.editors.transformations.model.TransformationTesterModel;
import hu.csega.games.engine.GameEngineFacade;

public class TransformationTesterXYSideView extends TransformationTesterSideView {

	public TransformationTesterXYSideView(GameEngineFacade facade) {
		super(facade);
	}

	@Override
	protected void translate(double x, double y) {
		TransformationTesterModel model = getModel();
		model.addCanvasXYTranslateX(x);
		model.addCanvasXYTranslateY(y);
	}

	@Override
	protected void zoom(double delta) {
		TransformationTesterModel model = getModel();
		model.addCanvasXYZoom(delta);
	}

	@Override
	protected void calculateTransformationMatrices() {
		TransformationTesterModel model = getModel();
		double scale = model.getCanvasXZZoom();
		double translateX = model.getCanvasXYTranslateX();
		double translateY = model.getCanvasXYTranslateY();
		this.screenTransformationMatrix = new Matrix4d().scale(scale).translate(translateX, translateY, 0.0);
		this.screenTransformationMatrix.invertAffine(this.inverseTransformationMatrix);

		System.out.println("SCREEN: " + screenTransformationMatrix);
		System.out.println("INVERSE: " + inverseTransformationMatrix);
	}

	private static final long serialVersionUID = 1L;
}