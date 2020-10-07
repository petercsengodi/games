package hu.csega.editors.transformations.layer1.presentation.opengl;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import hu.csega.editors.transformations.layer4.data.TransformationTesterModel;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectPlacement;

public class TransformationTesterMouseController implements MouseListener, MouseMotionListener, MouseWheelListener{

	public static final double[] ZOOM_VALUES = { 0.0001, 0.001, 0.01, 0.1, 0.2, 0.3, 0.5, 0.75, 1.0, 1.25, 1.50,
			2.0, 3.0, 4.0, 5.0, 10.0, 20.0, 50.0, 100.0, 200.0, 500.0, 1000.0, 2000.0, 5000.0 };
	public static final int DEFAULT_ZOOM_INDEX = ZOOM_VALUES.length - 2;

	private int zoomIndex = DEFAULT_ZOOM_INDEX;

	private double alfa;
	private double beta;

	private boolean mouseRightPressed = false;
	private Point mouseRightAt = new Point(0, 0);

	private Vector4f position = new Vector4f();
	private Vector4f target = new Vector4f();
	private Vector4f up = new Vector4f();
	private Matrix4f rotation = new Matrix4f();

	private GameEngineFacade facade;

	public TransformationTesterMouseController(GameEngineFacade facade) {
		this.facade = facade;
	}

	public double getScaling() {
		return ZOOM_VALUES[zoomIndex];
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int numberOfRotations = e.getWheelRotation();
		zoomIndex += numberOfRotations;
		if(zoomIndex < 0)
			zoomIndex = 0;
		else if(zoomIndex >= ZOOM_VALUES.length)
			zoomIndex = ZOOM_VALUES.length - 1;
		recalculateAndRepaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		modifyAlfaAndBetaIfNeeded(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		modifyAlfaAndBetaIfNeeded(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 3) {
			mouseRightPressed = true;
			mouseRightAt = new Point(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == 3) {
			mouseRightPressed = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private void modifyAlfaAndBetaIfNeeded(MouseEvent e) {
		if(mouseRightPressed) {
			int dx = mouseRightAt.x - e.getX();
			int dy = mouseRightAt.y - e.getY();

			alfa += dx / 100.0;
			if(alfa < -PI2)
				alfa += PI2;
			else if(alfa > PI2)
				alfa -= PI2;

			beta += dy / 100.0;
			if(beta < -BETA_LIMIT)
				beta = -BETA_LIMIT;
			else if(beta > BETA_LIMIT)
				beta = BETA_LIMIT;

			mouseRightAt.x = e.getX();
			mouseRightAt.y = e.getY();
			recalculateAndRepaint();
		}
	}

	private void recalculateAndRepaint() {
		double distance = ZOOM_VALUES[zoomIndex];

		rotation.identity();
		rotation.rotateAffineXYZ((float)beta, (float)alfa, 0f);

		position.set(0f, 0f, -(float)distance, 1f);
		rotation.transform(position);

		target.set(0f, 0f, 0f, 1f);

		up.set(0f, -1f, 0f, 1f);
		rotation.transform(up);

		TransformationTesterModel model = (TransformationTesterModel) facade.model();
		GameObjectPlacement cameraPlacement = model.getCamera();

		cameraPlacement.position.set(position.x, position.y, position.z);
		cameraPlacement.target.set(target.x, target.y, target.z);
		cameraPlacement.up.set(up.x, up.y, up.z);

		facade.window().repaintEverything();
	}

	private static final double PI2 = 2*Math.PI;
	private static final double BETA_LIMIT = Math.PI / 2;
}
