package hu.csega.superstition.ftm.view;

import java.awt.Point;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshCube;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;
import hu.csega.superstition.ftm.model.FreeTriangleMeshVertex;

public class FreeTriangleMeshXZSideView extends FreeTriangleMeshSideView {

	public FreeTriangleMeshXZSideView(GameEngineFacade facade) {
		super(facade);

		this.selectionLine.setY1(-1000.0);
		this.selectionLine.setY2(1000.0);
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
	protected void selectAll(int x1, int y1, int x2, int y2) {
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
		model.selectAll(cube);
	}

	@Override
	protected void selectFirst(int x, int y, int radius, boolean add) {
		selectionLine.setX1(x);
		selectionLine.setX2(x);
		selectionLine.setZ1(y);
		selectionLine.setZ2(y);

		FreeTriangleMeshModel model = getModel();
		model.selectFirst(intersection, selectionLine, radius, add);
	}

	@Override
	protected void createVertexAt(int x, int y) {
		FreeTriangleMeshModel model = getModel();
		model.createVertexAt(x, 0, y);
	}

	@Override
	protected Point transformVertexToPoint(FreeTriangleMeshVertex vertex) {
		Point ret = new Point();

		ret.x = (int)vertex.getPX();
		ret.y = (int)vertex.getPZ();

		return ret;
	}

	private static final long serialVersionUID = 1L;
}