package hu.csega.games.vbg.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import hu.csega.games.vbg.VirtualBoredGames;
import hu.csega.games.vbg.swing.VBGAbstractGame;
import hu.csega.games.vbg.util.GeometricUtil;

public class VBGSelector implements VBGAbstractGame {

	private Rectangle testRectangle = new Rectangle(10, 10, 100, 170);
	private int selectedIndex = -1;

	@Override
	public void paint(BufferedImage buffer) {
		Graphics2D g = (Graphics2D) buffer.getGraphics();

		Color c = (selectedIndex == 0 ? Color.white : Color.darkGray);

		g.setColor(c);
		g.fillRect(testRectangle.x, testRectangle.y, testRectangle.width, testRectangle.height - 20);
		g.setColor(Color.black);
		g.fillRect(testRectangle.x + 5, testRectangle.y + 5, testRectangle.width - 10, testRectangle.height - 30);
		g.setColor(c);
		g.setFont(new Font("Arial", 0, 80));
		g.drawString("T", testRectangle.x + 25, testRectangle.y + testRectangle.height - 65);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("Teszt", testRectangle.x, testRectangle.y + testRectangle.height - 5);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int ex = e.getX();
		int ey = e.getY();

		if(GeometricUtil.inRect(ex, ey, testRectangle))
			VirtualBoredGames.getCanvas().setGame(VBGAllGames.TEST_GAME);
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		int lastSelectedIndex = selectedIndex;
		if(GeometricUtil.inRect(e.getX(), e.getY(), testRectangle))
			selectedIndex = 0;
		else
			selectedIndex = -1;

		if(lastSelectedIndex != selectedIndex) {
			VirtualBoredGames.getCanvas().repaint();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		int lastSelectedIndex = selectedIndex;
		selectedIndex = -1;

		if(lastSelectedIndex != selectedIndex) {
			VirtualBoredGames.getCanvas().repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int lastSelectedIndex = selectedIndex;
		if(GeometricUtil.inRect(e.getX(), e.getY(), testRectangle))
			selectedIndex = 0;
		else
			selectedIndex = -1;

		if(lastSelectedIndex != selectedIndex) {
			VirtualBoredGames.getCanvas().repaint();
		}
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
