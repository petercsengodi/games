package hu.csega.superstition.t3dcreator.operations;

public class MoveSelected extends Operation
{
	private Vector3 translation;
	private IPart part;

	public MoveSelected(IPart part, Matrix matrix)
	{
		this.part = part;
		this.translation = Vector3.TransformCoordinate(
				new Vector3(0f, 0f, 0f), matrix);
	}

	@Override
	public void OnTransform()
	{
		part.move(translation);
	}

	@Override
	public void OnInvert()
	{
		part.move(-translation);
	}

}
