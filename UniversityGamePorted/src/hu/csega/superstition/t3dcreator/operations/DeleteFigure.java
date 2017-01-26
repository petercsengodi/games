package hu.csega.superstition.t3dcreator.operations;

public class DeleteFigure extends Operation
{
	private CModel model;
	private CFigure figure;

	public DeleteFigure(CModel model, CFigure figure)
	{
		this.model = model;
		this.figure = figure;
	}

	@Override
	public override void OnTransform()
	{
		model.figures.Remove(figure);
	}

	@Override
	public override void OnInvert()
	{
		model.figures.Add(figure);
	}


}
