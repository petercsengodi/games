package hu.csega.editors.ftm.layer1.presentation.swing.view;

import hu.csega.editors.common.lens.EditorPoint;
import hu.csega.editors.ftm.model.FreeTriangleMeshCube;
import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.editors.ftm.model.FreeTriangleMeshVertex;
import hu.csega.games.engine.GameEngineFacade;

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
		lenses.addTranslation(x, y, 0.0);
	}

	@Override
	protected void zoom(double delta) {
		FreeTriangleMeshModel model = getModel();
		model.setCanvasXYZoom(model.getCanvasXYZoom() + delta);
	}

	@Override
	protected void selectAll(double x1, double y1, double x2, double y2, boolean add) {
		double vx1 = Math.min(x1, x2);
		double vx2 = Math.max(x1, x2);
		double vy1 = Math.min(y1, y2);
		double vy2 = Math.max(y1, y2);

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
	protected void selectFirst(double x, double y, double radius, boolean add) {
		selectionLine.setX1(x);
		selectionLine.setX2(x);
		selectionLine.setY1(y);
		selectionLine.setY2(y);

		FreeTriangleMeshModel model = getModel();
		model.selectFirst(intersection, selectionLine, radius, add);
	}

	@Override
	protected void createVertexAt(double x, double y) {
		FreeTriangleMeshModel model = getModel();
		model.createVertexAt(x, y, 0);
	}

	@Override
	protected void moveSelected(double x, double y) {
		FreeTriangleMeshModel model = getModel();
		model.moveSelected(x, -y, 0);
	}

	@Override
	protected EditorPoint transformVertexToPoint(FreeTriangleMeshVertex vertex) {
		EditorPoint ret = new EditorPoint();

		ret.setX(vertex.getPX());
		ret.setY(vertex.getPY());

		return ret;
	}

	private static final long serialVersionUID = 1L;
}