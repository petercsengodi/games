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
	public void OnTransform()
	{
		model.figures.Remove(figure);
	}

	@Override
	public void OnInvert()
	{
		model.figures.Add(figure);
	}


}
