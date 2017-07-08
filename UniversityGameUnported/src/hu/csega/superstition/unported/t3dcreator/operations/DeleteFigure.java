package hu.csega.superstition.unported.t3dcreator.operations;

import hu.csega.superstition.unported.t3dcreator.CFigure;
import hu.csega.superstition.unported.t3dcreator.CModel;

public class DeleteFigure extends Operation {

	private CModel model;
	private CFigure figure;

	public DeleteFigure(CModel model, CFigure figure) {
		this.model = model;
		this.figure = figure;
	}

	@Override
	public void OnTransform() {
		model.figures.remove(figure);
	}

	@Override
	public void OnInvert() {
		model.figures.add(figure);
	}

}
