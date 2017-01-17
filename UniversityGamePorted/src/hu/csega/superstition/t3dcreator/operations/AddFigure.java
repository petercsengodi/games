package hu.csega.superstition.t3dcreator.operations;

class AddFigure : Operation
{
	private CModel model;
	private InitialFigure initial;
	private CFigure figure;

	public AddFigure(CModel model, InitialFigure initial)
	{
		this.model = model;
		this.initial = initial;
		this.figure = null;
	}

	public override void OnTransform()
	{
		figure = new CFigure(initial);
		model.figures.Add(figure);
		model.Selected = figure;
	}

	public override void OnInvert()
	{
		model.figures.Remove(figure);
		model.Selected = null;
	}

}