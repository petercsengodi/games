package hu.csega.games.vbg.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import hu.csega.games.vbg.main.VBGAllGames;

public class VBGCanvas extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

	private VBGAbstractGame game;

	private BufferedImage buffer = null;
	private Dimension lastSize = new Dimension();

	public VBGCanvas(VBGFrame pixelEditor) {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void setGame(VBGAllGames gameContainer) {
		// TODO load game state
		VBGAbstractGame previous;
		synchronized (this) {
			previous = this.game;
			this.game = gameContainer.getGame();
		}

		// TODO save previous' state
		if(previous != null)
			previous.compactIntoState();

		repaint();
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

		if(game != null) {
			synchronized (this) {
				game.paint(buffer);
			}
		}

		g.drawImage(buffer, 0, 0, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(game != null)
			game.mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(game != null)
			game.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(game != null)
			game.mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(game != null)
			game.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(game != null)
			game.mouseReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(game != null)
			game.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(game != null)
			game.mouseMoved(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(game != null)
			game.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(game != null)
			game.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(game != null)
			game.keyReleased(e);
	}

	private static final long serialVersionUID = 1L;
}
