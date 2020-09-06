package hu.csega.editors.common.dndview;

import java.awt.Color;

class DADVTestLine extends DragAndDropModelObject {

	private DragAndDropHitLine line = new DragAndDropHitLine(200, 200, 400, 300, 10);

	public DADVTestLine() {
		super(DragAndDropZoomFlag.RELATIVE, DragAndDropZoomFlag.RELATIVE);
	}

	@Override
	public boolean pointIsInsideOfHitShape(double x, double y) {
		return line.pointIsInsideOfHitShape(x, y);
	}

	@Override
	public boolean hitShapeIsInsideOfBox(double x1, double y1, double x2, double y2) {
		return line.hitShapeIsInsideOfBox(x1, y1, x2, y2);
	}

	@Override
	protected void render(DragAndDropRenderContext context) {
		line.renderHitShape(context, context.isSelected() ? Color.RED : Color.BLACK);
	}

	@Override
	protected boolean moved(DragAndDropSelectionContext context, double dx, double dy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void renderHitShape(DragAndDropRenderContext context, Color color) {
		line.renderHitShape(context, color);
	}

}
