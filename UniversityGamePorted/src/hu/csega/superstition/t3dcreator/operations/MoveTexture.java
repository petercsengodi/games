package hu.csega.superstition.t3dcreator.operations;

public class MoveTexture : Operation
{
	private Vector2 translation;
	private IPart part;

	public MoveTexture(IPart part, Vector2 translation)
	{
		this.translation = translation;
		this.part = part;
	}

	public override void OnTransform()
	{
		part.moveTexture(translation);
	}

	public override void OnInvert()
	{
		part.moveTexture(-translation);
	}

}
