package hu.csega.superstition.t3dcreator.operations;

public class DeleteFigure : Operation
{
	private CModel model;
	private CFigure figure;

	public DeleteFigure(CModel model, CFigure figure)
	{
		this.model = model;
		this.figure = figure;
	}

	public override void OnTransform()
	{
		model.figures.Remove(figure);
	}

	public override void OnInvert()
	{
		model.figures.Add(figure);
	}


}
