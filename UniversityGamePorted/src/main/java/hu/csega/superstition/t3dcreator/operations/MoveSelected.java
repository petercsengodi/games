package hu.csega.superstition.t3dcreator.operations;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import hu.csega.superstition.model.IPart;

public class MoveSelected extends Operation {

	private Vector3f translation;
	private IPart part;

	public MoveSelected(IPart part, Matrix4f matrix) {
		this.part = part;
		this.translation = Vector3f.TransformCoordinate(new Vector3f(0f, 0f, 0f), matrix);
	}

	@Override
	public void OnTransform() {
		part.move(translation);
	}

	@Override
	public void OnInvert() {
		part.move(-translation);
	}

}
