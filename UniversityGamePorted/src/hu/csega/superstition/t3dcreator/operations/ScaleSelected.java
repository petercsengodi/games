package hu.csega.superstition.t3dcreator.operations;

public class ScaleSelected : Operation
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

	public override void OnTransform()
	{
		part.scale(matrix);
	}

	public override void OnInvert()
	{
		part.scale(inverse);
	}

}