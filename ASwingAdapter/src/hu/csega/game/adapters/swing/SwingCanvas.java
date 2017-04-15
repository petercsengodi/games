package hu.csega.game.adapters.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import hu.csega.game.engine.GameEngine;
import hu.csega.game.engine.GameRendering;

public class SwingCanvas extends JPanel implements MouseListener, MouseMotionListener {

	public static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

	public SwingCanvas(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		this.rendering = gameEngine.getRendering();
		setPreferredSize(PREFERRED_SIZE);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)buffer.getGraphics();
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, PREFERRED_SIZE.width, PREFERRED_SIZE.height);
		g2d.setColor(Color.black);

		paint2d(g2d);

		g.drawImage(buffer, 0, 0, null);
	}

	private void paint2d(Graphics2D g) {
		g.translate(PREFERRED_SIZE.width / 2, PREFERRED_SIZE.height / 2);

		SwingGraphics graphics = new SwingGraphics(g);
		rendering.render(graphics);

		g.translate(-PREFERRED_SIZE.width / 2, -PREFERRED_SIZE.height / 2);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());

		if(mouseRightPressed) {
			translate.x += mouseRightAt.x - p.x;
			translate.y += mouseRightAt.y - p.y;

			mouseRightAt.x = p.x;
			mouseRightAt.y = p.y;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());

		if(mouseRightPressed) {
			translate.x += mouseRightAt.x - p.x;
			translate.y += mouseRightAt.y - p.y;

			mouseRightAt.x = p.x;
			mouseRightAt.y = p.y;
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
		}
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

	private GameEngine gameEngine;
	private GameRendering rendering;

	private BufferedImage buffer = new BufferedImage(PREFERRED_SIZE.width, PREFERRED_SIZE.height, BufferedImage.TYPE_INT_RGB);
	private boolean mouseLeftPressed = false;
	private boolean mouseRightPressed = false;
	private Point mouseLeftAt = new Point(0, 0);
	private Point mouseRightAt = new Point(0, 0);
	private Point translate = new Point(0, 0);

	private static final long serialVersionUID = 1L;
}
