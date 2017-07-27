package hu.csega.superstition.ftm.view;

import java.awt.Point;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshCube;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;
import hu.csega.superstition.ftm.model.FreeTriangleMeshVertex;

public class FreeTriangleMeshXYSideView extends FreeTriangleMeshSideView {

	public FreeTriangleMeshXYSideView(GameEngineFacade facade) {
		super(facade);

		this.selectionLine.setZ1(-1000.0);
		this.selectionLine.setZ2(1000.0);
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
	protected void selectAll(int x1, int y1, int x2, int y2, boolean add) {
		int vx1 = Math.min(x1, x2);
		int vx2 = Math.max(x1, x2);
		int vy1 = Math.min(y1, y2);
		int vy2 = Math.max(y1, y2);

		FreeTriangleMeshCube cube = new FreeTriangleMeshCube();
		cube.setX1(vx1);
		cube.setX2(vx2);
		cube.setY1(vy1);
		cube.setY2(vy2);
		cube.setZ1(Double.NEGATIVE_INFINITY);
		cube.setZ2(Double.POSITIVE_INFINITY);

		FreeTriangleMeshModel model = getModel();
		model.selectAll(cube, add);
	}

	@Override
	protected void selectFirst(int x, int y, int radius, boolean add) {
		selectionLine.setX1(x);
		selectionLine.setX2(x);
		selectionLine.setY1(y);
		selectionLine.setY2(y);

		FreeTriangleMeshModel model = getModel();
		model.selectFirst(intersection, selectionLine, radius, add);
	}

	@Override
	protected void createVertexAt(int x, int y) {
		FreeTriangleMeshModel model = getModel();
		model.createVertexAt(x, y, 0);
	}

	@Override
	protected void moveSelected(int x, int y) {
		FreeTriangleMeshModel model = getModel();
		model.moveSelected(x, -y, 0);
	}

	@Override
	protected Point transformVertexToPoint(FreeTriangleMeshVertex vertex) {
		Point ret = new Point();

		ret.x = (int)vertex.getPX();
		ret.y = (int)vertex.getPY();

		return ret;
	}

	private static final long serialVersionUID = 1L;
}