package hu.csega.superstition.unported.t3dcreator.operations;

import org.joml.Matrix4f;

import hu.csega.superstition.model.IPart;

public class ScaleSelected extends Operation {

	private Matrix4f matrix;
	private Matrix4f inverse;
	private IPart part;

	public ScaleSelected(IPart part, Matrix4f matrix) {
		this.part = part;
		this.matrix = new Matrix4f().set(matrix);
		this.inverse = new Matrix4f().set(matrix);
		this.inverse.invert();
	}

	@Override
	public void OnTransform() {
		part.scale(matrix);
	}

	@Override
	public void OnInvert() {
		part.scale(inverse);
	}

}