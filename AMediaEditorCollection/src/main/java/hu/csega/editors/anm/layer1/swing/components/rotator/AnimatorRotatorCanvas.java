package hu.csega.editors.anm.layer1.swing.components.rotator;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class AnimatorRotatorCanvas extends JPanel {

	static final int CANVAS_MIN_SIZE = 80;

	@Override
	public void paint(Graphics g) {
		int width = this.getWidth();
		int height = this.getHeight();
		g.setColor(Color.RED);
		g.fillRect(0, 0, width, height);
	}

	private static final long serialVersionUID = 1L;

}
