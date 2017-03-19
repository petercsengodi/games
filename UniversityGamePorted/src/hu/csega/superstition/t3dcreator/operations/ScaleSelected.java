package hu.csega.superstition.t3dcreator.operations;

public class ScaleSelected extends Operation
{
	private Matrix matrix;
	private Matrix inverse;
	private IPart part;

	public ScaleSelected(IPart part, Matrix matrix)
	{
		this.part = part;
		this.matrix = matrix;
		this.inverse = matrix;
		this.inverse.Invert();
	}

	@Override
	public void OnTransform()
	{
		part.scale(matrix);
	}

	@Override
	public void OnInvert()
	{
		part.scale(inverse);
	}

}