package hu.csega.editors.transformations.layer1.presentation.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.joml.Matrix4d;
import org.joml.Vector4d;

import hu.csega.editors.common.lens.EditorPoint;
import hu.csega.editors.transformations.layer4.data.TransformationTesterModel;
import hu.csega.editors.transformations.layer4.data.TransformationTesterVertex;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameWindow;

public abstract class TransformationTesterCanvas extends JPanel implements GameCanvas, MouseListener, MouseMotionListener, MouseWheelListener {

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

	protected int zoomIndex = DEFAULT_ZOOM_INDEX;

	protected Matrix4d screenTransformationMatrix = new Matrix4d();
	protected Matrix4d inverseTransformationMatrix = new Matrix4d();

	public TransformationTesterCanvas(GameEngineFacade facade) {
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

		calculateTransformationMatrices(size.width, size.height);

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

	protected abstract void calculateTransformationMatrices(double screenWidth, double screenHeight);

	protected TransformationTesterVertex screenTransformation(TransformationTesterVertex vertex) {
		Vector4d v = new Vector4d(vertex.getPX(), vertex.getPY(), vertex.getPZ(), 1.0f);
		Vector4d result = screenTransformationMatrix.transform(v);
		double w = result.w();
		return new TransformationTesterVertex(result.x() / w, result.y() / w, result.z() / w);
	}

	protected TransformationTesterVertex screenTransformation(double x, double y, double z) {
		Vector4d v = new Vector4d(x, y, z, 1.0f);
		Vector4d result = screenTransformationMatrix.transform(v);
		double w = result.w();
		return new TransformationTesterVertex(result.x() / w, result.y() / w, result.z() / w);
	}

	protected TransformationTesterVertex inverseTransformation(TransformationTesterVertex vertex) {
		Vector4d v = new Vector4d(vertex.getPX(), vertex.getPY(), vertex.getPZ(), 1.0f);
		Vector4d result = inverseTransformationMatrix.transform(v);
		double w = result.w();
		return new TransformationTesterVertex(result.x() / w, result.y() / w, result.z() / w);
	}

	protected TransformationTesterVertex inverseTransformation(double x, double y, double z) {
		Vector4d v = new Vector4d(x, y, z, 1.0f);
		Vector4d result = inverseTransformationMatrix.transform(v);
		double w = result.w();
		return new TransformationTesterVertex(result.x() / w, result.y() / w, result.z() / w);
	}

	protected EditorPoint transformVertexToPoint(TransformationTesterVertex vertex) {
		EditorPoint ret = new EditorPoint();

		ret.setX(vertex.getPX());
		ret.setY(vertex.getPY());

		return ret;
	}

	protected TransformationTesterModel getModel() {
		TransformationTesterModel model = (TransformationTesterModel) facade.model();
		return model;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());

		if(mouseLeftPressed) {
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
		} else if(e.getButton() == 3) {
			mouseRightPressed = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();

		TransformationTesterVertex from = new TransformationTesterVertex(x, y, -1000);
		TransformationTesterVertex to = new TransformationTesterVertex(x, y, 1000);

		TransformationTesterVertex clickStart = inverseTransformation(from);
		TransformationTesterVertex clickEnd = inverseTransformation(to);

		TransformationTesterModel model = getModel();
		model.setClickStart(clickStart);
		model.setClickEnd(clickEnd);
		repaintEverything();
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

	protected double distance(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2;
		double dy = y1 - y2;
		double ret = Math.sqrt(dx*dx + dy*dy);
		return ret;
	}

	protected abstract void paint2d(Graphics2D g);

	private static final long serialVersionUID = 1L;
}
