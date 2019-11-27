package hu.csega.editors.anm.swing.old;

import hu.csega.games.engine.GameEngineFacade;

public class AnimatorTopDownViewPort extends AnimatorAbstractViewPort {

	public AnimatorTopDownViewPort(GameEngineFacade facade) {
		super(facade);
	}

	@Override
	protected void translate(double x, double y) {
	}

	@Override
	protected void zoom(double delta) {
	}

	@Override
	protected void selectAll(double x1, double y1, double x2, double y2, boolean add) {
	}

	@Override
	protected void selectFirst(double x, double y, double radius, boolean add) {
	}

	@Override
	protected void createVertexAt(double x, double y) {
	}

	@Override
	protected void moveSelected(double x, double y) {
	}

	private static final long serialVersionUID = 1L;
}
