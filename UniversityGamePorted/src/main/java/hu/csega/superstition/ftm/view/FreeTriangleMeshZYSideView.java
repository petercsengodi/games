package hu.csega.superstition.ftm.view;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshCube;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;

public class FreeTriangleMeshZYSideView extends FreeTriangleMeshSideView {

	public FreeTriangleMeshZYSideView(GameEngineFacade facade) {
		super(facade);
	}

	@Override
	protected void translate(double x, double y) {
		FreeTriangleMeshModel model = getModel();
		model.setCanvasZYTranslateX(x);
		model.setCanvasZYTranslateY(y);
	}

	@Override
	protected void zoom(double delta) {
		FreeTriangleMeshModel model = getModel();
		model.setCanvasZYZoom(model.getCanvasZYZoom() + delta);
	}

	@Override
	protected void select(int x1, int y1, int x2, int y2) {
		int vz1 = Math.min(x1, x2);
		int vz2 = Math.max(x1, x2);
		int vy1 = Math.min(y1, y2);
		int vy2 = Math.max(y1, y2);

		FreeTriangleMeshCube cube = new FreeTriangleMeshCube();
		cube.setX1(Double.MIN_VALUE);
		cube.setX2(Double.MAX_VALUE);
		cube.setY1(vy1);
		cube.setY2(vy2);
		cube.setZ1(vz1);
		cube.setZ2(vz2);

		FreeTriangleMeshModel model = getModel();
		model.select(cube);
	}

	private static final long serialVersionUID = 1L;
}