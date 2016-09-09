package hu.csega.game.adapters.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import hu.csega.game.engine.GameColor;
import hu.csega.game.engine.GameGraphics;
import hu.csega.game.engine.GamePoint;
import hu.csega.game.engine.GameSprite;

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

	@Override
	public void drawTriangleStrip(GameColor color, GamePoint... gamePoints) {
		int len = gamePoints.length;
		g.setColor(new Color(color.r, color.g, color.b, color.a));

		int a = 0;
		while(a < len - 2) {
			for(int i = 0; i < 3; i++) {
				GamePoint gp = gamePoints[a+i];
				xPoints[i] = (int)gp.x;
				yPoints[i] = (int)gp.y;
			} // end for

			g.fillPolygon(xPoints, yPoints, 3);
			a++;
		} // end while

	}

	@Override
	public void drawTriangles(GameColor[] colors, GamePoint[] gamePoints) {
		int len = colors.length;
		if(len * 3 != gamePoints.length) {
			throw new IllegalArgumentException("Color and triangle count is not the same.");
		}

		for(int a = 0; a < len; a++) {
			GameColor color = colors[a];
			g.setColor(new Color(color.r, color.g, color.b, color.a));

			for(int i = 0; i < 3; i++) {
				GamePoint gp = gamePoints[a*3 + i];
				xPoints[i] = (int)gp.x;
				yPoints[i] = (int)gp.y;
			} // end for

			g.fillPolygon(xPoints, yPoints, 3);
		} // end while

	}

	@Override
	public void drawSprite(GameSprite sprite, double x, double y) {
		g.drawImage(sprite.getImage(), (int)x, (int)y, null);
	}

	private int[] xPoints = new int[3];
	private int[] yPoints = new int[3];

	private Graphics2D g;

}
