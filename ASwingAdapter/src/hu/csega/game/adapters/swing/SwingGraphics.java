package hu.csega.game.adapters.swing;

import java.awt.Color;
import java.awt.Graphics2D;

import hu.csega.game.engine.GameGraphics;

public class SwingGraphics implements GameGraphics {

	public SwingGraphics(Graphics2D g2d) {
		this.g = g2d;
	}

	@Override
	public void crossHair(double x, double y) {
		g.setColor(Color.black);
		g.drawLine((int)(x - 20), (int)(y), (int)(x + 20), (int)(y));
		g.drawLine((int)(x), (int)(y - 20), (int)(x), (int)(y + 20));
	}

	private Graphics2D g;

}
