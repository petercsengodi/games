package hu.csega.editors.anm.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class AnimatorScenesPanel extends JPanel {

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private static final long serialVersionUID = 1L;
}
