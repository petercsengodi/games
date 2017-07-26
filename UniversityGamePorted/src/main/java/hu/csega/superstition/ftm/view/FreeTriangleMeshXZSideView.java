package hu.csega.superstition.ftm.view;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshCube;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;

public class FreeTriangleMeshXZSideView extends FreeTriangleMeshSideView {

	public FreeTriangleMeshXZSideView(GameEngineFacade facade) {
		super(facade);
	}

	@Override
	protected void translate(double x, double y) {
		FreeTriangleMeshModel model = getModel();
		model.setCanvasXZTranslateX(x);
		model.setCanvasXZTranslateY(y);
	}

	@Override
	protected void zoom(double delta) {
		FreeTriangleMeshModel model = getModel();
		model.setCanvasXZZoom(model.getCanvasXZZoom() + delta);
	}

	@Override
	protected void select(int x1, int y1, int x2, int y2) {
		int vx1 = Math.min(x1, x2);
		int vx2 = Math.max(x1, x2);
		int vz1 = Math.min(y1, y2);
		int vz2 = Math.max(y1, y2);

		FreeTriangleMeshCube cube = new FreeTriangleMeshCube();
		cube.setX1(vx1);
		cube.setX2(vx2);
		cube.setY1(Double.MIN_VALUE);
		cube.setY2(Double.MAX_VALUE);
		cube.setZ1(vz1);
		cube.setZ2(vz2);

		FreeTriangleMeshModel model = getModel();
		model.select(cube);
	}

	private static final long serialVersionUID = 1L;
}