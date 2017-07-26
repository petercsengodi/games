package hu.csega.superstition.ftm.view;

import java.awt.Point;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshCube;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;
import hu.csega.superstition.ftm.model.FreeTriangleMeshVertex;

public class FreeTriangleMeshZYSideView extends FreeTriangleMeshSideView {

	public FreeTriangleMeshZYSideView(GameEngineFacade facade) {
		super(facade);

		this.selectionLine.setX1(-1000.0);
		this.selectionLine.setX2(1000.0);
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
	protected void selectAll(int x1, int y1, int x2, int y2, boolean add) {
		int vz1 = Math.min(x1, x2);
		int vz2 = Math.max(x1, x2);
		int vy1 = Math.min(y1, y2);
		int vy2 = Math.max(y1, y2);

		FreeTriangleMeshCube cube = new FreeTriangleMeshCube();
		cube.setX1(Double.NEGATIVE_INFINITY);
		cube.setX2(Double.POSITIVE_INFINITY);
		cube.setY1(vy1);
		cube.setY2(vy2);
		cube.setZ1(vz1);
		cube.setZ2(vz2);

		FreeTriangleMeshModel model = getModel();
		model.selectAll(cube, add);
	}

	@Override
	protected void selectFirst(int x, int y, int radius, boolean add) {
		selectionLine.setZ1(x);
		selectionLine.setZ2(x);
		selectionLine.setY1(y);
		selectionLine.setY2(y);

		FreeTriangleMeshModel model = getModel();
		model.selectFirst(intersection, selectionLine, radius, add);
	}

	@Override
	protected void createVertexAt(int x, int y) {
		FreeTriangleMeshModel model = getModel();
		model.createVertexAt(0, y, x);
	}

	@Override
	protected void moveSelected(int x, int y) {
		FreeTriangleMeshModel model = getModel();
		model.moveSelected(0, y, x);
	}

	@Override
	protected Point transformVertexToPoint(FreeTriangleMeshVertex vertex) {
		Point ret = new Point();

		ret.x = (int)vertex.getPZ();
		ret.y = (int)vertex.getPY();

		return ret;
	}

	private static final long serialVersionUID = 1L;
}