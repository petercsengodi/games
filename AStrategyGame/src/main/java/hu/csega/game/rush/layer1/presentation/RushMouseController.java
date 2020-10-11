package hu.csega.game.rush.layer1.presentation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import hu.csega.game.rush.layer4.data.RushGameModel;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameSelectionLine;

public class RushMouseController implements MouseListener, MouseMotionListener, MouseWheelListener {

	private GameEngineFacade facade;

	public RushMouseController(GameEngineFacade facade) {
		this.facade = facade;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			int x = e.getX();
			int y = e.getY();
			System.out.println("Pressed: X=" + x + " Y=" + y);

			RushGameModel model = (RushGameModel) facade.model();
			GameSelectionLine selectionLine = model.getGameSelectionLine();
			boolean valid = selectionLine.initialize(x, y);

			if(valid) {
				model.select();
			}
		}

		if(e.getButton() == 3) {
			// mouseRightPressed = true;
			// mouseRightAt = new Point(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private static final double PI2 = 2*Math.PI;
	private static final double BETA_LIMIT = Math.PI / 2;
}
