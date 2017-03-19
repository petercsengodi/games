package hu.csega.superstition.t3dcreator.operations;

public class MoveTexture extends Operation
{
	private Vector2 translation;
	private IPart part;

	public MoveTexture(IPart part, Vector2 translation)
	{
		this.translation = translation;
		this.part = part;
	}

	@Override
	public void OnTransform()
	{
		part.moveTexture(translation);
	}

	@Override
	public void OnInvert()
	{
		part.moveTexture(-translation);
	}

}
