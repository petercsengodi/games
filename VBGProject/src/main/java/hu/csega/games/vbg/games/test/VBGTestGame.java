package hu.csega.games.vbg.games.test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import hu.csega.games.vbg.swing.VBGAbstractGame;

public class VBGTestGame implements VBGAbstractGame {

	@Override
	public void paint(BufferedImage buffer) {
		Graphics2D g = (Graphics2D) buffer.getGraphics();
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 0, 40));
		g.drawString("Szia Anya!", 20, 50);
	}

	@Override
	public void mouseClicked(MouseEvent e) {


	}

	@Override
	public void mousePressed(MouseEvent e) {


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

	@Override
	public void mouseDragged(MouseEvent e) {


	}

	@Override
	public void mouseMoved(MouseEvent e) {


	}

	@Override
	public void keyTyped(KeyEvent e) {


	}

	@Override
	public void keyPressed(KeyEvent e) {


	}

	@Override
	public void keyReleased(KeyEvent e) {


	}

	@Override
	public String compactIntoState() {
		return "";
	}

	@Override
	public void loadState(String stateRepresentation) {


	}

}
