package hu.csega.klongun.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import hu.csega.klongun.KlonGun;
import hu.csega.klongun.screen.TPal;
import hu.csega.klongun.screen.TVscr;

public class KlonGunCanvas extends JPanel implements MouseListener, MouseMotionListener {

	public static final Dimension PREFERRED_SIZE = new Dimension(640, 400);

	public KlonGunCanvas(KlonGun klonGun) {
		this.klonGun = klonGun;
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
		TPal palette = klonGun.palette;
		TVscr scr = klonGun.gun.vscr;

		for(int x = 0; x < TVscr.WIDTH; x++) {
			for(int y = 0; y < TVscr.HEIGHT; y++) {

				int pixel = scr.get(x, y);
				int cr = palette.get(pixel, 0);
				int cg = palette.get(pixel, 1);
				int cb = palette.get(pixel, 2);
				int col = (cr << 16) | (cg << 8) | cb;

				for(int xx = 0; xx < 2; xx++) {
					for(int yy = 0; yy < 2; y++) {

						buffer.setRGB(x + xx, y + yy, col);

					}
				}

			}
		}

		g.drawImage(buffer, 0, 0, null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseAt.x = e.getX();
		mouseAt.y = e.getY();

		if(mouseRightPressed) {
			translate.x += mouseRightAt.x - mouseAt.x;
			translate.y += mouseRightAt.y - mouseAt.y;

			mouseRightAt.x = mouseAt.x;
			mouseRightAt.y = mouseAt.y;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseAt.x = e.getX();
		mouseAt.y = e.getY();

		if(mouseRightPressed) {
			translate.x += mouseRightAt.x - mouseAt.x;
			translate.y += mouseRightAt.y - mouseAt.y;

			mouseRightAt.x = mouseAt.x;
			mouseRightAt.y = mouseAt.y;
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

	private KlonGun klonGun;

	private BufferedImage buffer = new BufferedImage(PREFERRED_SIZE.width, PREFERRED_SIZE.height, BufferedImage.TYPE_INT_RGB);
	private Point mouseAt = new Point(0, 0);
	private boolean mouseLeftPressed = false;
	private boolean mouseRightPressed = false;
	private Point mouseLeftAt = new Point(0, 0);
	private Point mouseRightAt = new Point(0, 0);
	private Point translate = new Point(0, 0);

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

}
