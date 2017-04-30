package hu.csega.brp.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class BRPCanvas extends JPanel implements MouseListener, MouseMotionListener {

	public BRPCanvas(BRPEditor pixelEditor) {
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// TODO actual drawing

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		int x = arg0.getX();
		int y = arg0.getY();

		if(x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT) {

			// TODO mouse clicked
		}

		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1)
			leftButtonPressed = false;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if(leftButtonPressed) {
			int x = arg0.getX();
			int y = arg0.getY();
			if(x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT) {

				// TODO dragging
			}

			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(leftButtonPressed) {
			int x = arg0.getX();
			int y = arg0.getY();
			if(x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT) {

				// TODO simply moved
			}

			repaint();
		}
	}

	private boolean leftButtonPressed;

	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;

	private static final long serialVersionUID = 1L;
}
