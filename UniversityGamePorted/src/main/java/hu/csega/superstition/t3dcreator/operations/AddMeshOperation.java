package hu.csega.superstition.t3dcreator.operations;

import hu.csega.superstition.t3dcreator.CFigure;
import hu.csega.superstition.t3dcreator.CModel;

public class AddMeshOperation extends Operation {

	private CModel model;
	private CFigure[] figures;

	public AddMeshOperation(CModel model, CFigure[] figures) {
		this.model = model;
		this.figures = figures;
	}

	public AddMeshOperation(CModel model, CFigure figure) {
		this.model = model;
		this.figures = new CFigure[]{figure};
	}

	@Override
	public void OnTransform() {

		for(CFigure figure : figures) {
			model.figures.add(figure);
		}
	}

	@Override
	public void OnInvert() {
		for(CFigure figure : figures) {
			model.figures.remove(figure);
		}
	}

}
