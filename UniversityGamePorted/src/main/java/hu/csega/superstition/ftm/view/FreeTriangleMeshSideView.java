package hu.csega.superstition.ftm.view;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshLine;
import hu.csega.superstition.ftm.util.FreeTriangleMeshSphereLineIntersection;

public abstract class FreeTriangleMeshSideView extends FreeTriangleMeshCanvas {

	protected FreeTriangleMeshLine selectionLine = new FreeTriangleMeshLine();
	protected FreeTriangleMeshSphereLineIntersection intersection = new FreeTriangleMeshSphereLineIntersection();

	public FreeTriangleMeshSideView(GameEngineFacade facade) {
		super(facade);
	}

	private static final long serialVersionUID = 1L;
}
