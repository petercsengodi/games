package hu.csega.games.vbg.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import hu.csega.games.vbg.VirtualBoredGames;
import hu.csega.games.vbg.swing.VBGAbstractGame;
import hu.csega.games.vbg.util.GeometricUtil;

public class VBGSelector implements VBGAbstractGame {

	private static final List<VBGSelectable> selectables = new ArrayList<>();

	private Rectangle baseRectangle = new Rectangle(10, 10, 100, 170);
	private int selectedIndex = -1;
	private int lastWidth = 100;
	private boolean needsRepaint = false;

	@Override
	public boolean needsRepaint() {
		return needsRepaint;
	}

	@Override
	public void paint(BufferedImage buffer) {
		Graphics2D g = (Graphics2D) buffer.getGraphics();
		lastWidth = buffer.getWidth();

		int row = 0;
		int col = 0;

		for(int count = 0; count < selectables.size(); count++) {
			VBGSelectable selectable = selectables.get(count);
			String iconCharacter = String.valueOf(selectable.iconCharacter);
			Color c = (selectedIndex == count ? Color.white : Color.darkGray);

			int left = baseRectangle.x + (baseRectangle.x + baseRectangle.width) * col;
			if(col > 0 && left + baseRectangle.width >= lastWidth) {
				left = baseRectangle.x;
				col = 0;
				row++;
			}

			int top = baseRectangle.y + (baseRectangle.y + baseRectangle.height) * row;
			col++;

			g.setColor(c);
			g.fillRect(left, top, baseRectangle.width, baseRectangle.height - 20);
			g.setColor(Color.black);
			g.fillRect(left + 5, top + 5, baseRectangle.width - 10, baseRectangle.height - 30);
			g.setColor(c);
			g.setFont(new Font("Arial", 0, 80));
			g.drawString(iconCharacter, left + 25, top + baseRectangle.height - 65);
			g.setFont(new Font("Arial", Font.BOLD, 14));
			g.drawString(selectable.title, left, top + baseRectangle.height - 5);
		}

		needsRepaint = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		selectedIndex = getSelectionIndex(e.getX(), e.getY());

		if(selectedIndex > -1) {
			VBGAllGames game = selectables.get(selectedIndex).game;
			VirtualBoredGames.getCanvas().setGame(game);
		}
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
		selectedIndex = getSelectionIndex(e.getX(), e.getY());

		if(lastSelectedIndex != selectedIndex) {
			needsRepaint = true;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		int lastSelectedIndex = selectedIndex;
		selectedIndex = -1;

		if(lastSelectedIndex != selectedIndex) {
			needsRepaint = true;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int lastSelectedIndex = selectedIndex;
		selectedIndex = getSelectionIndex(e.getX(), e.getY());

		if(lastSelectedIndex != selectedIndex) {
			needsRepaint = true;
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

	private int getSelectionIndex(int x, int y) {
		int row = 0;
		int col = 0;
		Rectangle test = new Rectangle();
		test.setBounds(baseRectangle);

		for(int count = 0; count < selectables.size(); count++) {
			test.x = baseRectangle.x + (baseRectangle.x + baseRectangle.width) * col;
			if(col > 0 && test.x + baseRectangle.width >= lastWidth) {
				test.x = baseRectangle.x;
				col = 0;
				row++;
			}

			test.y = baseRectangle.y + (baseRectangle.y + baseRectangle.height) * row;
			col++;

			if(GeometricUtil.inRect(x, y, test))
				return count;
		}

		return -1;
	}

	public static void fillMenu() {
		{
			VBGSelectable selectable = new VBGSelectable();
			selectable.title = "Üdvözlet";
			selectable.iconCharacter = 'Ü';
			selectable.game = VBGAllGames.GREETINGS;
			selectables.add(selectable);
		}
	}
}
