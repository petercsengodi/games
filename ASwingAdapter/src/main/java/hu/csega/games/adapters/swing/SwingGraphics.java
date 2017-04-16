package hu.csega.games.adapters.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import hu.csega.games.engine.impl.GameColor;
import hu.csega.games.engine.impl.GameGraphics;
import hu.csega.games.engine.impl.GameHitBox;
import hu.csega.games.engine.impl.GameHitCircle;
import hu.csega.games.engine.impl.GameHitShape;
import hu.csega.games.engine.impl.GamePoint;
import hu.csega.games.engine.impl.GameSprite;

public class SwingGraphics implements GameGraphics {

	public SwingGraphics(Graphics2D g2d) {
		this.g = g2d;
	}

	@Override
	public void rotate(double angle) {
		g.rotate(angle);
	}

	@Override
	public void translate(double tx, double ty) {
		g.translate(tx, ty);
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
			g.setColor(toSwingColor(color));

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

	@Override
	public void drawSprite(BufferedImage image, double x, double y) {
		g.drawImage(image, (int)x, (int)y, null);
	}

	@Override
	public void drawHitShape(GameHitShape hitShape, double x, double y, GameColor color) {
		g.setColor(toSwingColor(color));

		// TODO: no instanceof !!!

		if(hitShape instanceof GameHitBox) {
			GameHitBox box = (GameHitBox) hitShape;
			g.drawRect((int)(x + box.minX), (int)(y + box.minY), (int)(box.maxX - box.minX), (int)(box.maxY - box.minY));
		}

		if(hitShape instanceof GameHitCircle) {
			GameHitCircle circle = (GameHitCircle) hitShape;
			g.drawOval((int)(x + circle.x - circle.r), (int)(y + circle.y - circle.r), (int)(2 * circle.r), (int)(2 * circle.r));
		}
	}

	private Color toSwingColor(GameColor color) {
		return new Color(color.r, color.g, color.b, color.a);
	}

	private int[] xPoints = new int[3];
	private int[] yPoints = new int[3];

	private Graphics2D g;

}
