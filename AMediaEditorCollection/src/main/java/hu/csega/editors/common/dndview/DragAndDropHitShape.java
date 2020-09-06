package hu.csega.editors.common.dndview;

import java.awt.Color;

public interface DragAndDropHitShape {

	boolean pointIsInsideOfHitShape(double x, double y);

	boolean hitShapeIsInsideOfBox(double x1, double y1, double x2, double y2);

	void renderHitShape(DragAndDropRenderContext context, Color color);

}
