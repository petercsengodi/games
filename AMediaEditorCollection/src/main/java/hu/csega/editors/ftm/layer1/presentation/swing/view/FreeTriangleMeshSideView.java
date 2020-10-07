package hu.csega.editors.ftm.layer1.presentation.swing.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

import hu.csega.editors.common.lens.EditorPoint;
import hu.csega.editors.ftm.model.FreeTriangleMeshLine;
import hu.csega.editors.ftm.model.FreeTriangleMeshModel;
import hu.csega.editors.ftm.model.FreeTriangleMeshTriangle;
import hu.csega.editors.ftm.model.FreeTriangleMeshVertex;
import hu.csega.editors.ftm.util.FreeTriangleMeshSphereLineIntersection;
import hu.csega.games.engine.GameEngineFacade;

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

		{
			g.setColor(Color.WHITE);

			for(int x = -400; x <= 400; x += 20) {
				EditorPoint p1 = transformToScreen(new EditorPoint(x, -400, 0.0, 1.0));
				EditorPoint p2 = transformToScreen(new EditorPoint(x, 400, 0.0, 1.0));
				drawLine(g, p1, p2);
			}

			for(int y = -400; y <= 400; y += 20) {
				EditorPoint p1 = transformToScreen(new EditorPoint(-400, y, 0.0, 1.0));
				EditorPoint p2 = transformToScreen(new EditorPoint(400, y, 0.0, 1.0));
				drawLine(g, p1, p2);
			}

			EditorPoint p1 = transformToScreen(new EditorPoint(-10, -10, 0.0, 1.0));
			EditorPoint p2 = transformToScreen(new EditorPoint(10, 10, 0.0, 1.0));
			drawRectangle(g, p1, p2);
		}

		FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();
		List<Object> selectedObjects = model.getSelectedObjects();

		List<FreeTriangleMeshVertex> vertices = model.getVertices();
		List<FreeTriangleMeshTriangle> triangles = model.getTriangles();

		g.setColor(Color.darkGray);
		for(FreeTriangleMeshTriangle triangle : triangles) {
			if(model.enabled(triangle)) {
				EditorPoint p1 = transformToScreen(transformVertexToPoint(vertices.get(triangle.getVertex1())));
				EditorPoint p2 = transformToScreen(transformVertexToPoint(vertices.get(triangle.getVertex2())));
				EditorPoint p3 = transformToScreen(transformVertexToPoint(vertices.get(triangle.getVertex3())));
				drawLine(g, p1, p2);
				drawLine(g, p2, p3);
				drawLine(g, p3, p1);
			}
		}

		for(FreeTriangleMeshVertex vertex : vertices) {
			if(model.enabled(vertex)) {
				if(selectedObjects.contains(vertex)) {
					g.setColor(Color.red);
				} else {
					g.setColor(Color.black);
				}

				EditorPoint p = transformVertexToPoint(vertex);
				EditorPoint transformed = transformToScreen(p);
				g.drawRect((int)transformed.getX() - 2, (int)transformed.getY() - 2, 5, 5);
			}
		}


		g.translate(-widthDiv2, -heightDiv2);

		Rectangle selectionBox = calculateSelectionBox();
		if(selectionBox != null) {
			g.setColor(Color.red);
			calculateSelectionBox();
			g.drawRect(selectionBox.x, selectionBox.y, selectionBox.width, selectionBox.height);
		}
	}

	private EditorPoint transformToScreen(EditorPoint p) {
		return lenses.fromModelToScreen(p.getX(), -p.getY(), 0.0);
	}

	private void drawLine(Graphics2D g, EditorPoint end1, EditorPoint end2) {
		g.drawLine((int)end1.getX(), (int)end1.getY(), (int)end2.getX(), (int)end2.getY());
	}

	private void drawRectangle(Graphics2D g, EditorPoint end1, EditorPoint end2) {
		g.drawRect((int)end1.getX(), (int)end1.getY(), (int)(end2.getX() - end1.getX()), (int)(end2.getY() - end1.getY()));
	}

	private static final long serialVersionUID = 1L;
}
