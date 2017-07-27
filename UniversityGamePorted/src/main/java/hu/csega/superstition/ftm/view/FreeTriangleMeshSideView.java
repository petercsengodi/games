package hu.csega.superstition.ftm.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.superstition.ftm.model.FreeTriangleMeshLine;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;
import hu.csega.superstition.ftm.model.FreeTriangleMeshTriangle;
import hu.csega.superstition.ftm.model.FreeTriangleMeshVertex;
import hu.csega.superstition.ftm.util.FreeTriangleMeshSphereLineIntersection;

public abstract class FreeTriangleMeshSideView extends FreeTriangleMeshCanvas {

	protected FreeTriangleMeshLine selectionLine = new FreeTriangleMeshLine();
	protected FreeTriangleMeshSphereLineIntersection intersection = new FreeTriangleMeshSphereLineIntersection();

	public FreeTriangleMeshSideView(GameEngineFacade facade) {
		super(facade);
	}

	@Override
	protected void paint2d(Graphics2D g) {
		int widthDiv2 = lastSize.width / 2;
		int heightDiv2 = lastSize.height / 2;
		g.translate(widthDiv2, heightDiv2);

		//		SwingGraphics graphics = new SwingGraphics(g, lastSize.width, heightDiv2);
		//		gameEngine.runStep(GameEngineStep.RENDER, graphics);

		FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();
		List<Object> selectedObjects = model.getSelectedObjects();

		List<FreeTriangleMeshVertex> vertices = model.getVertices();
		List<FreeTriangleMeshTriangle> triangles = model.getTriangles();

		g.setColor(Color.darkGray);
		for(FreeTriangleMeshTriangle triangle : triangles) {
			Point p1 = transformToScreen(transformVertexToPoint(vertices.get(triangle.getVertex1())));
			Point p2 = transformToScreen(transformVertexToPoint(vertices.get(triangle.getVertex2())));
			Point p3 = transformToScreen(transformVertexToPoint(vertices.get(triangle.getVertex3())));
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
			g.drawLine(p2.x, p2.y, p3.x, p3.y);
			g.drawLine(p3.x, p3.y, p1.x, p1.y);
		}

		for(FreeTriangleMeshVertex vertex : vertices) {

			if(selectedObjects.contains(vertex)) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.black);
			}

			Point p = transformVertexToPoint(vertex);
			Point transformed = transformToScreen(p);
			g.drawRect(transformed.x - 2, transformed.y - 2, 5, 5);
		}


		g.translate(-widthDiv2, -heightDiv2);

		Rectangle selectionBox = calculateSelectionBox();
		if(selectionBox != null) {
			g.setColor(Color.red);
			calculateSelectionBox();
			g.drawRect(selectionBox.x, selectionBox.y, selectionBox.width, selectionBox.height);
		}
	}

	private Point transformToScreen(Point p) {
		Point ret = new Point();
		ret.x = p.x;
		ret.y = -p.y;
		return ret;
	}

	private static final long serialVersionUID = 1L;
}
