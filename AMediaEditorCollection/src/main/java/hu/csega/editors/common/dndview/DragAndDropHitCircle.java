package hu.csega.editors.common.dndview;

import java.awt.Color;
import java.awt.Graphics2D;

public class DragAndDropHitCircle implements DragAndDropHitShape {

	private double x0;
	private double y0;
	private double r;
	private double r2;

	public DragAndDropHitCircle(double x0, double y0, double r) {
		this.x0 = x0;
		this.y0 = y0;
		this.r = r;
		this.r2 = r * r;
	}

	public double getX0() {
		return x0;
	}

	public void setX0(double x0) {
		this.x0 = x0;
	}

	public double getY0() {
		return y0;
	}

	public void setY0(double y0) {
		this.y0 = y0;
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
		graphics.drawOval((int)(x0 - r), (int)(y0 - r), (int)(r*2.0), (int)(r*2.0));
	}

	@Override
	public boolean pointIsInsideOfHitShape(double x, double y) {
		double dx = x - x0;
		double dy = y - y0;
		return dx*dx + dy*dy <= r2;
	}

	@Override
	public boolean hitShapeIsInsideOfBox(double x1, double y1, double x2, double y2) {
		return DragAndDropHitBox.pointIsInsideOfBox(x0-r, y0-r, x1, y1, x2, y2) &&
				DragAndDropHitBox.pointIsInsideOfBox(x0+r, y0+r, x1, y1, x2, y2);
	}

}
