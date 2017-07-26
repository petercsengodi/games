package hu.csega.superstition.ftm.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.superstition.ftm.model.FreeTriangleMeshModel;
import hu.csega.superstition.ftm.model.FreeTriangleMeshTriangle;
import hu.csega.superstition.ftm.model.FreeTriangleMeshVertex;

public abstract class FreeTriangleMeshCanvas extends JPanel implements GameCanvas, MouseListener, MouseMotionListener {

	public static final Dimension PREFERRED_SIZE = new Dimension(400, 300);

	private GameEngineFacade facade;

	private BufferedImage buffer = null;
	private Dimension lastSize = new Dimension(PREFERRED_SIZE.width, PREFERRED_SIZE.height);

	private boolean mouseLeftPressed = false;
	private boolean mouseRightPressed = false;
	private Point mouseLeftAt = new Point(0, 0);
	private Point mouseRightAt = new Point(0, 0);

	private boolean selectionBoxEnabled = false;
	private Point selectionStart = new Point();
	private Point selectionEnd = new Point();
	private Rectangle selectionBox = new Rectangle();

	public FreeTriangleMeshCanvas(GameEngineFacade facade) {
		this.facade = facade;
		setPreferredSize(PREFERRED_SIZE);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public Component getRealCanvas() {
		return this;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		Dimension size = getSize();
		if(buffer == null || !lastSize.equals(size)) {
			buffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
			lastSize.width = size.width;
			lastSize.height = size.height;
		}

		Graphics2D g2d = (Graphics2D)buffer.getGraphics();
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, lastSize.width, lastSize.height);
		g2d.setColor(Color.black);

		paint2d(g2d);

		g.drawImage(buffer, 0, 0, null);
	}

	public void repaintEverything() {
		facade.window().repaintEverything();
	}

	protected abstract void translate(double x, double y);

	protected abstract void zoom(double delta);

	protected abstract void selectAll(int x1, int y1, int x2, int y2, boolean add);

	protected abstract void selectFirst(int x, int y, int radius, boolean add);

	protected abstract void createVertexAt(int x, int y);

	protected abstract void moveSelected(int x, int y);

	protected abstract Point transformVertexToPoint(FreeTriangleMeshVertex vertex);

	protected FreeTriangleMeshModel getModel() {
		FreeTriangleMeshModel model = (FreeTriangleMeshModel) facade.model();
		return model;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());

		if(mouseLeftPressed) {
			int dx = mouseLeftAt.x - p.x;
			int dy = mouseLeftAt.y - p.y;
			if(selectionBoxEnabled) {
				selectionEnd.x -= dx;
				selectionEnd.y -= dy;
				repaint();
			} else if(e.isControlDown()) {
				moveSelected(-dx, dy);
				repaintEverything();
			}
			mouseLeftAt.x = p.x;
			mouseLeftAt.y = p.y;
		}

		if(mouseRightPressed) {
			int dx = mouseRightAt.x - p.x;
			int dy = mouseRightAt.y - p.y;
			translate(dx, dy);
			repaint();
			mouseRightAt.x = p.x;
			mouseRightAt.y = p.y;
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());

		if(mouseRightPressed) {

			GameControl control = facade.control();
			if(control.isControlOn()) {
				double midX = this.getWidth() / 2.0;
				double midY = this.getHeight() / 2.0;
				double oldDistance = distance(midX, midY, mouseRightAt.x, mouseRightAt.y);
				double newDistance = distance(midX, midY, p.x, p.y);
				double diffDistance = newDistance - oldDistance;
				zoom(diffDistance);
			} else {
				translate(mouseRightAt.x - p.x, mouseRightAt.y - p.y);
			}

			mouseRightAt.x = p.x;
			mouseRightAt.y = p.y;
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(e.isControlDown()) {
				// create new vertex
				Point p = transformToModel(e.getX(), e.getY());
				createVertexAt(p.x, p.y);
				repaintEverything();
			} else {
				// select one vertex
				Point p = transformToModel(e.getX(), e.getY());
				selectFirst(p.x, p.y, 5, e.isShiftDown());
				repaintEverything();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			mouseLeftPressed = true;
			mouseLeftAt = new Point(e.getX(), e.getY());
			if(!e.isControlDown()) {
				selectionBoxEnabled = true;
				selectionStart.x = selectionEnd.x = mouseLeftAt.x;
				selectionStart.y = selectionEnd.y = mouseLeftAt.y;
			}
		}

		if(e.getButton() == 3) {
			mouseRightPressed = true;
			mouseRightAt = new Point(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == 1) {
			mouseLeftPressed = false;
			if(selectionBoxEnabled) {
				calculateSelectionBox();
				Point p1 = transformToModel(selectionStart.x, selectionStart.y);
				Point p2 = transformToModel(selectionEnd.x, selectionEnd.y);
				selectAll(p1.x, p1.y, p2.x, p2.y, e.isShiftDown());
				selectionBoxEnabled = false;
				repaintEverything();
			}
		} else if(e.getButton() == 3) {
			mouseRightPressed = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private void paint2d(Graphics2D g) {
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

		if(selectionBoxEnabled) {
			g.setColor(Color.red);
			calculateSelectionBox();
			g.drawRect(selectionBox.x, selectionBox.y, selectionBox.width, selectionBox.height);
		}
	}

	private Point transformToModel(Point p) {
		return transformToModel(p.x, p.y);
	}

	private Point transformToModel(int x, int y) {
		int widthDiv2 = lastSize.width / 2;
		int heightDiv2 = lastSize.height / 2;

		Point ret = new Point();
		ret.x = x - widthDiv2;
		ret.y = heightDiv2 - y;
		return ret;
	}

	private Point transformToScreen(Point p) {
		Point ret = new Point();
		ret.x = p.x;
		ret.y = -p.y;
		return ret;
	}

	private double distance(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double ret = Math.sqrt(dx*dx + dy*dy);
		return ret;
	}

	private void calculateSelectionBox() {
		selectionBox.x = Math.min(selectionStart.x, selectionEnd.x);
		selectionBox.y = Math.min(selectionStart.y, selectionEnd.y);
		selectionBox.width = Math.abs(selectionStart.x - selectionEnd.x);
		selectionBox.height = Math.abs(selectionStart.y - selectionEnd.y);
	}

	private static final long serialVersionUID = 1L;
}
