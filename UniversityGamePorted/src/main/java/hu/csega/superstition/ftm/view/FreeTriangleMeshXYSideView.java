package hu.csega.superstition.ftm.view;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshCube;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;

public class FreeTriangleMeshXYSideView extends FreeTriangleMeshSideView {

	public FreeTriangleMeshXYSideView(GameEngineFacade facade) {
		super(facade);
	}

	@Override
	protected void translate(double x, double y) {
		FreeTriangleMeshModel model = getModel();
		model.setCanvasXYTranslateX(x);
		model.setCanvasXYTranslateY(y);
	}

	@Override
	protected void zoom(double delta) {
		FreeTriangleMeshModel model = getModel();
		model.setCanvasXYZoom(model.getCanvasXYZoom() + delta);
	}

	@Override
	protected void select(int x1, int y1, int x2, int y2) {
		int vx1 = Math.min(x1, x2);
		int vx2 = Math.max(x1, x2);
		int vy1 = Math.min(y1, y2);
		int vy2 = Math.max(y1, y2);

		FreeTriangleMeshCube cube = new FreeTriangleMeshCube();
		cube.setX1(vx1);
		cube.setX2(vx2);
		cube.setY1(vy1);
		cube.setY2(vy2);
		cube.setZ1(Double.MIN_VALUE);
		cube.setZ2(Double.MAX_VALUE);

		FreeTriangleMeshModel model = getModel();
		model.select(cube);
	}

	private static final long serialVersionUID = 1L;
}