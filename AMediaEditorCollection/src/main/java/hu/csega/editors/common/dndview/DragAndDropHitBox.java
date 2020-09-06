package hu.csega.editors.common.dndview;

import java.awt.Color;
import java.awt.Graphics2D;

public class DragAndDropHitBox implements DragAndDropHitShape {

	private double x1;
	private double y1;
	private double x2;
	private double y2;

	public DragAndDropHitBox(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

	@Override
	public void renderHitShape(DragAndDropRenderContext context, Color color) {
		Graphics2D graphics = context.getGraphics();
		graphics.setColor(color);
		graphics.drawRect((int)x1, (int)y1, (int)(x2 - x1), (int)(y2 - y1));
	}

	@Override
	public boolean pointIsInsideOfHitShape(double x, double y) {
		return pointIsInsideOfBox(x, y, x1, y1, x2, y2);
	}

	@Override
	public boolean hitShapeIsInsideOfBox(double x1, double y1, double x2, double y2) {
		return pointIsInsideOfBox(this.x1, this.y1, x1, y1, x2, y2) &&
				pointIsInsideOfBox(this.x2, this.y2, x1, y1, x2, y2);
	}

	public static boolean pointIsInsideOfBox(double px, double py, double x1, double y1, double x2, double y2) {
		if(px < x1 || px > x2)
			return false;

		if(py < y1 || py > y2)
			return false;

		return true;
	}
}
