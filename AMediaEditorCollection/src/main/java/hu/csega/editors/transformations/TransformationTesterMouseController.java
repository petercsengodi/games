package hu.csega.editors.transformations;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import hu.csega.editors.transformations.model.TransformationTesterModel;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectLocation;

public class TransformationTesterMouseController implements MouseListener, MouseMotionListener, MouseWheelListener{

	public static final double[] ZOOM_VALUES = { 0.0001, 0.001, 0.01, 0.1, 0.2, 0.3, 0.5, 0.75, 1.0, 1.25, 1.50, 2.0, 3.0, 4.0, 5.0, 10.0, 100.0, 200.0, 500.0 };
	public static final int DEFAULT_ZOOM_INDEX = ZOOM_VALUES.length - 2;

	private int zoomIndex = DEFAULT_ZOOM_INDEX;

	private double alfa;
	private double beta;

	private boolean mouseRightPressed = false;
	private Point mouseRightAt = new Point(0, 0);

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
		double py = distance * Math.sin(beta);
		double projectedDistance = distance * Math.cos(beta);
		double px = projectedDistance * Math.sin(alfa);
		double pz = projectedDistance * Math.cos(alfa);

		TransformationTesterModel model = (TransformationTesterModel) facade.model();
		GameObjectLocation camera = model.getCamera();

		/*
		camera.position.set((float)px, (float)py, (float)pz);
		camera.rotation.set((float)(beta), (float)(Math.PI-alfa), 0f);
		 */

		camera.position.set(0f, 0f, 0f);
		camera.rotation.set((float)(beta), (float)(alfa), 0f);

		facade.window().repaintEverything();
	}

	private static final double PI2 = 2*Math.PI;
	private static final double BETA_LIMIT = Math.PI / 2;
}
