package hu.csega.editors.common.dndview;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class DragAndDropHitLine implements DragAndDropHitShape {

	private double x1;
	private double y1;
	private double x2;
	private double y2;

	private double r;
	private double r2;

	private double dx;
	private double dy;

	public DragAndDropHitLine(double x1, double y1, double x2, double y2, double r) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;

		this.r = r;
		this.r2 = r * r;

		this.dx = x2 - x1;
		this.dy = y2 - y1;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
		this.dx = x2 - x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
		this.dy = y2 - y1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
		this.dx = x2 - x1;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
		this.dy = y2 - y1;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
		this.r2 = r*r;
	}

	@Override
	public void renderHitShape(DragAndDropRenderContext context, Color color) {
		Graphics2D graphics = context.getGraphics();
		graphics.setColor(color);
		Stroke old = graphics.getStroke();

		graphics.setStroke(new BasicStroke((float)(r*2.0), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		graphics.drawLine((int)x1, (int)y1, (int)x2, (int)y2);

		if(old != null) {
			graphics.setStroke(old);
		}
	}

	@Override
	public boolean pointIsInsideOfHitShape(double x, double y) {
		// If (x, y, r) is a circle with a radius of r, then the line should intersect this circle.
		// The intersection coordinates (X?, Y?) should be between the starting and ending points of the line.

		// Linear equation:
		// double dx = x2 - x1;
		// double dy = y2 - y1;
		// X? == x1 + t * dx
		// Y? == y1 + t * dy
		// Should be: 0 <= t <= 1

		// Circle equation:
		// (X? - x)^2 + (Y? - y)^2 == r^2

		// Quadratic equation equation:
		// (x1 + t*dx - x)^2 + (y1 + t*dy - y)^2 == r^2

		double hx = x1 - x;
		double hy = y1 - y;
		// (hx + t*dx)^2 + (hy + t*dy)^2 == r^2
		// hx^2 + 2*hx*t*dx + dx^2*t^2 + hy^2 + 2*hy*t*dy + dy^2*t^2 - r^2 == 0
		// dx^2*t^2 + dy^2*t^2 + 2*hx*t*dx + 2*hy*t*dy + hx^2 + hy^2 - r^2 == 0
		// (dx^2 + dy^2)*t^2 + 2*(hx*dx + hy*dy)*t + (hx^2 + hy^2 - r^2) == 0

		// Solving the quadratic equation:
		double a = (dx*dx + dy*dy);
		double b = 2*(hx*dx + hy*dy);
		double c = (hx*hx + hy*hy - r2);

		// Solution: t1,2 = (-b +- sqrt(b^2 - 4*a*c)) / (2*a)
		double D = b*b - 4*a*c;

		if(D < 0.0) {
			// There is no solution for this equation at all!
			return false;
		}

		double t1 = (-b + Math.sqrt(D)) / (2 * a);
		if(0.0 <= t1 && t1 <= 1.0) {
			// We found a valid solution!
			return true;
		}

		double t2 = (-b - Math.sqrt(D)) / (2 * a);
		if(0.0 <= t2 && t2 <= 1.0) {
			// We found a valid solution!
			return true;
		}

		// The equation could be solved, but no result was inside the starting and ending points of the line.
		return false;
	}

	@Override
	public boolean hitShapeIsInsideOfBox(double x1, double y1, double x2, double y2) {
		return DragAndDropHitBox.pointIsInsideOfBox(this.x1, this.y1, x1, y1, x2, y2) &&
				DragAndDropHitBox.pointIsInsideOfBox(this.x2, this.y2, x1, y1, x2, y2);
	}

}
