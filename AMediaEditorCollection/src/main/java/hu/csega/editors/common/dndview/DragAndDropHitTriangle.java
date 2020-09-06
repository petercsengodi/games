package hu.csega.editors.common.dndview;

import java.awt.Color;
import java.awt.Graphics2D;

public class DragAndDropHitTriangle implements DragAndDropHitShape {

	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private double x3;
	private double y3;

	public DragAndDropHitTriangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
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

	public double getX3() {
		return x3;
	}

	public void setX3(double x3) {
		this.x3 = x3;
	}

	public double getY3() {
		return y3;
	}

	public void setY3(double y3) {
		this.y3 = y3;
	}

	@Override
	public void renderHitShape(DragAndDropRenderContext context, Color color) {
		Graphics2D graphics = context.getGraphics();
		graphics.setColor(color);
		graphics.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
		graphics.drawLine((int)x2, (int)y2, (int)x3, (int)y3);
		graphics.drawLine((int)x3, (int)y3, (int)x1, (int)y1);
	}

	@Override
	public boolean pointIsInsideOfHitShape(double x, double y) {
		// Algorithm copied from StackOverflow:

		double d1 = sign(x, y, x1, y1, x2, y2);
		double d2 = sign(x, y, x2, y2, x3, y3);
		double d3 = sign(x, y, x3, y3, x1, y1);

		boolean hasNegative = (d1 < 0) || (d2 < 0) || (d3 < 0);
		boolean hasPositive = (d1 > 0) || (d2 > 0) || (d3 > 0);

		return !(hasNegative && hasPositive);
	}

	@Override
	public boolean hitShapeIsInsideOfBox(double x1, double y1, double x2, double y2) {
		return DragAndDropHitBox.pointIsInsideOfBox(this.x1, this.y1, x1, y1, x2, y2) &&
				DragAndDropHitBox.pointIsInsideOfBox(this.x2, this.y2, x1, y1, x2, y2) &&
				DragAndDropHitBox.pointIsInsideOfBox(this.x3, this.y3, x1, y1, x2, y2);
	}

	private static double sign(double x1, double y1, double x2, double y2, double x3, double y3) {
		// Algorithm copied from StackOverflow:
		return (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3);
	}
}
