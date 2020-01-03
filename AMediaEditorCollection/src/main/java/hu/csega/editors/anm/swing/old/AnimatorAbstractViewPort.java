package hu.csega.editors.anm.swing.old;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import hu.csega.editors.anm.model.old.AnimatorModel;
import hu.csega.editors.anm.model.old.AnimatorPartPlacement;
import hu.csega.editors.anm.model.old.AnimatorPartPlacementChild;
import hu.csega.editors.anm.model.old.AnimatorScene;
import hu.csega.editors.anm.model.old.parts.AnimatorJoint;
import hu.csega.editors.anm.model.old.parts.AnimatorPart;
import hu.csega.editors.anm.model.old.parts.AnimatorPartModel;
import hu.csega.editors.anm.model.old.parts.AnimatorPartTriangle;
import hu.csega.editors.anm.model.old.parts.AnimatorPartVertex;
import hu.csega.editors.anm.model.old.parts.AnimatorPosition;
import hu.csega.editors.common.EditorPoint;
import hu.csega.editors.common.EditorTransformation;
import hu.csega.editors.common.TransformationStack;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameWindow;

public abstract class AnimatorAbstractViewPort extends JPanel implements GameCanvas, MouseListener, MouseMotionListener, MouseWheelListener {

	protected GameEngineFacade facade;

	public static final Dimension PREFERRED_SIZE = new Dimension(400, 300);
	protected Dimension lastSize = new Dimension(PREFERRED_SIZE.width, PREFERRED_SIZE.height);

	public static final double[] ZOOM_VALUES = { 0.0001, 0.001, 0.01, 0.1, 0.2, 0.3, 0.5, 0.75, 1.0, 1.25, 1.50, 2.0, 3.0, 4.0, 5.0, 10.0, 100.0 };
	public static final int DEFAULT_ZOOM_INDEX = 8;

	private BufferedImage buffer = null;

	private boolean mouseLeftPressed = false;
	private boolean mouseRightPressed = false;
	private Point mouseLeftAt = new Point(0, 0);
	private Point mouseRightAt = new Point(0, 0);

	private boolean selectionBoxEnabled = false;
	private Point selectionStart = new Point();
	private Point selectionEnd = new Point();
	private Rectangle selectionBox = new Rectangle();

	protected int zoomIndex = DEFAULT_ZOOM_INDEX;

	protected EditorTransformation transformation = new EditorTransformation();
	protected TransformationStack transformationStack = new TransformationStack();

	public AnimatorAbstractViewPort(GameEngineFacade facade) {
		this.facade = facade;
		setPreferredSize(PREFERRED_SIZE);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);

		GameWindow window = facade.window();
		KeyListener keyListener = (KeyListener) window;
		addKeyListener(keyListener);
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

	protected void paint2d(Graphics2D g) {
		EditorPoint topLeft = new EditorPoint(0, 0, 0, 1);
		EditorPoint bottomRight = new EditorPoint(lastSize.width, lastSize.height, 0, 1);

		transformation.fromScreenToModel(topLeft);
		transformation.fromScreenToModel(bottomRight);

		AnimatorModel model = getModel();
		AnimatorScene scene = model.scenes.get(model.currentScene);

		transformationStack.push(transformation.transformation);

		for(String rootKey : scene.roots) {
			AnimatorPartPlacement root = scene.partPlacements.get(rootKey);
			render(g, model, root);
		}

		transformationStack.pop();
	}

	protected void render(Graphics2D g, AnimatorModel model, AnimatorPartPlacement partPlacement) {
		transformationStack.push(partPlacement.transformation);

		AnimatorPart part = model.parts.get(partPlacement.part);
		renderWires(g, Color.GRAY, part.model);

		for(AnimatorJoint joint : part.joints) {
			renderPosition(g, Color.GRAY, joint.location.position);
		}

		for(AnimatorPartPlacementChild child : partPlacement.children) {
			render(g, model, child.partPlacement);
		}

		transformationStack.pop();
	}

	private void renderPosition(Graphics2D g, Color color, AnimatorPosition position) {
		EditorPoint p = new EditorPoint();
		p.setX(position.x);
		p.setY(position.y);
		p.setZ(position.z);
		p.setW(1.0);

		transformation.fromModelToScreen(p);
		drawPoint(g, color, p);
	}

	private void renderWires(Graphics2D g, Color color, AnimatorPartModel model) {
		List<AnimatorPartVertex> vertices = model.getVertices();
		int numberOfVertices = vertices.size();
		List<EditorPoint> points = new ArrayList<>(numberOfVertices);

		for(AnimatorPartVertex vertex : vertices) {
			EditorPoint p = fromVertex(vertex);
			transformation.fromModelToScreen(p);
			points.add(p);
		}

		for(AnimatorPartTriangle triangle : model.triangles) {
			EditorPoint p1 = points.get(triangle.getVertex1());
			EditorPoint p2 = points.get(triangle.getVertex2());
			EditorPoint p3 = points.get(triangle.getVertex3());
			drawTriangle(g, color, p1, p2, p3);
		}
	}

	protected void drawPoint(Graphics2D g, Color color, EditorPoint p) {
		g.setColor(color);
		g.drawOval((int)p.getX() - 3, (int)p.getY() - 3, 7, 7);
	}

	protected void drawTriangle(Graphics2D g, Color color, EditorPoint p1, EditorPoint p2, EditorPoint p3) {
		g.setColor(color);
		g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
		g.drawLine((int)p2.getX(), (int)p2.getY(), (int)p3.getX(), (int)p3.getY());
		g.drawLine((int)p3.getX(), (int)p3.getY(), (int)p1.getX(), (int)p1.getY());
	}

	protected void drawLine(Graphics2D g, Color color, EditorPoint p1, EditorPoint p2) {
		g.setColor(color);
		g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
	}

	public void repaintEverything() {
		facade.window().repaintEverything();
	}

	protected abstract void translate(double x, double y);

	protected abstract void zoom(double delta);

	protected abstract void selectAll(double x1, double y1, double x2, double y2, boolean add);

	protected abstract void selectFirst(double x, double y, double radius, boolean add);

	protected abstract void createVertexAt(double x, double y);

	protected abstract void moveSelected(double x, double y);

	// protected abstract EditorPoint transformVertexToPoint(FreeTriangleMeshVertex vertex);

	protected AnimatorModel getModel() {
		AnimatorModel model = (AnimatorModel) facade.model();
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
				moveSelected(-dx, -dy);
				repaintEverything();
			}
			mouseLeftAt.x = p.x;
			mouseLeftAt.y = p.y;
		}

		if(mouseRightPressed) {
			int dx = p.x - mouseRightAt.x;
			int dy = p.y - mouseRightAt.y;
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
			translate(p.x - mouseRightAt.x, p.y - mouseRightAt.y);
			mouseRightAt.x = p.x;
			mouseRightAt.y = p.y;
			repaint();
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
				EditorPoint p1 = transformToModel(selectionStart.x, selectionStart.y);
				EditorPoint p2 = transformToModel(selectionEnd.x, selectionEnd.y);
				selectAll(p1.getX(), p1.getY(), p2.getX(), p2.getY(), e.isShiftDown());
				selectionBoxEnabled = false;
				repaintEverything();
			}

			AnimatorModel model = getModel();
			// model.editorModel.finalizeMove();
		} else if(e.getButton() == 3) {
			mouseRightPressed = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(e.isControlDown()) {
				// create new vertex
				EditorPoint p = transformToModel(e.getX(), e.getY());
				createVertexAt(p.getX(), p.getY());
				repaintEverything();
			} else {
				// select one vertex
				EditorPoint p = transformToModel(e.getX(), e.getY());
				selectFirst(p.getX(), p.getY(), 5, e.isShiftDown());
				repaintEverything();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int numberOfRotations = e.getWheelRotation();
		zoomIndex += numberOfRotations;
		if(zoomIndex < 0)
			zoomIndex = 0;
		else if(zoomIndex >= ZOOM_VALUES.length)
			zoomIndex = ZOOM_VALUES.length - 1;
		repaint();
	}

	protected Rectangle calculateSelectionBox() {
		if(selectionBoxEnabled) {
			selectionBox.x = Math.min(selectionStart.x, selectionEnd.x);
			selectionBox.y = Math.min(selectionStart.y, selectionEnd.y);
			selectionBox.width = Math.abs(selectionStart.x - selectionEnd.x);
			selectionBox.height = Math.abs(selectionStart.y - selectionEnd.y);
			return selectionBox;
		} else {
			return null;
		}
	}

	private EditorPoint transformToModel(int x, int y) {
		int widthDiv2 = lastSize.width / 2;
		int heightDiv2 = lastSize.height / 2;
		return fromScreenToModel(x - widthDiv2, heightDiv2 - y);
	}

	private EditorPoint fromScreenToModel(int i, int j) {
		return new EditorPoint(); // FIXME
	}

	private EditorPoint fromVertex(AnimatorPartVertex vertex) {
		EditorPoint p = new EditorPoint();
		p.setX(vertex.getPx());
		p.setY(vertex.getPy());
		p.setZ(vertex.getPz());
		p.setW(1.0);
		return p;
	}

	protected double distance(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double ret = Math.sqrt(dx*dx + dy*dy);
		return ret;
	}

	private static final long serialVersionUID = 1L;
}
