package hu.csega.superstition.t3dcreator.operations;

import hu.csega.superstition.t3dcreator.CFigure;
import hu.csega.superstition.t3dcreator.CModel;
import hu.csega.superstition.t3dcreator.InitialFigure;

public class AddFigure extends Operation {

	private CModel model;
	private InitialFigure initial;
	private CFigure figure;

	public AddFigure(CModel model, InitialFigure initial) {
		this.model = model;
		this.initial = initial;
		this.figure = null;
	}

	@Override
	public void OnTransform() {
		figure = new CFigure(initial);
		model.figures.add(figure);
		model.setSelected(figure);
	}

	@Override
	public void OnInvert() {
		model.figures.remove(figure);
		model.setSelected(null);
	}

}