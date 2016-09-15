package hu.csega.skeleton.contour.second;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class ContourSecondCanvas extends JPanel {

	public ContourSecondCanvas() {
	}

	@Override
	public void paint(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		int widthPerTwo = this.getWidth() / 2;
		int heightPerTwo = this.getHeight() / 2;
		g.translate(widthPerTwo, heightPerTwo);

		for(int x = -widthPerTwo; x < widthPerTwo; x++) {
			for(int y = -heightPerTwo; y < heightPerTwo; y++) {

				double acc = 0;

				for(int i = 0; i < points.length; i++) {
					double dist = distance(x, y, points[i].x, points[i].y);
					double diff = (dist - weights[i]);
					acc += diff;
				}

				int b = 0;
				int r = 0;
				if(acc > 255) {
					r = 255;
				} else if (acc > 0) {
					r = (int) acc;
				} else if (acc >= -255) {
					b = (int) Math.abs(acc);
				} else {
					b = 255;
				}

				Color color = new Color(r, 0, b);
				g.setColor(color);
				g.fillOval(x, y, 1, 1);
			}
		}

		g.setColor(Color.white);
		for(int i = 0; i < points.length; i++) {
			g.drawLine(points[i].x - 10, points[i].y, points[i].x + 10, points[i].y);
			g.drawLine(points[i].x, points[i].y - 10, points[i].x, points[i].y + 10);
		}

		g.translate(-widthPerTwo, -heightPerTwo);

	}

	private static double distance(double x1, double y1, double x2, double y2) {
		double xd = x2 - x1;
		double yd = y2 - y1;
		return Math.sqrt(xd * xd + yd * yd);
	}

	private static final Point[] points = new Point[] {
			new Point(-100, -100),
			new Point(100, -100),
			new Point(0, 0)
	};

	private static final double[] weights = new double[] {
			100,
			100,
			100
	};

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

}
