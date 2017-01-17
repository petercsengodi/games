package hu.csega.superstition.t3dcreator.operations;

public class AddMeshOperation : Operation
{
	private CModel model;
	private CFigure[] figures;

	public AddMeshOperation(CModel model, CFigure[] figures)
	{
		this.model = model;
		this.figures = figures;
	}

	public AddMeshOperation(CModel model, CFigure figure)
	{
		this.model = model;
		this.figures = new CFigure[]{figure};
	}

	public override void OnTransform()
	{
		foreach(CFigure figure in figures)
		{
			model.figures.Add(figure);
		}
	}

	public override void OnInvert()
	{
		foreach(CFigure figure in figures)
		{
			model.figures.Remove(figure);
		}
	}

}
