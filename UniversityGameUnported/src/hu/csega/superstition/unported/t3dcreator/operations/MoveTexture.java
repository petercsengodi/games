package hu.csega.superstition.unported.t3dcreator.operations;

import org.joml.Vector2f;

import hu.csega.superstition.model.IPart;

public class MoveTexture extends Operation
{
	private Vector2f translation;
	private IPart part;

	public MoveTexture(IPart part, Vector2f translation) {
		this.translation = translation;
		this.part = part;
	}

	@Override
	public void OnTransform() {
		part.moveTexture(translation);
	}

	@Override
	public void OnInvert() {
		part.moveTexture(-translation);
	}

}
