package hu.csega.editors.anm.swing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class AnimatorColorPanel extends JPanel {

	private Color color;

	public AnimatorColorPanel(Color color) {
		this.color = color;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, getWidth(), getHeight());
	}


}
