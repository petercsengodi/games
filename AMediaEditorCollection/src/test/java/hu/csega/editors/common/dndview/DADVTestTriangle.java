package hu.csega.editors.common.dndview;

import java.awt.Color;

class DADVTestTriangle extends DragAndDropModelObject {

	private DragAndDropHitTriangle triangle = new DragAndDropHitTriangle(20, -20, -20, -20, 20, 20);

	public DADVTestTriangle() {
		super(DragAndDropZoomFlag.RELATIVE, DragAndDropZoomFlag.RELATIVE);
	}

	@Override
	public boolean pointIsInsideOfHitShape(double x, double y) {
		return triangle.pointIsInsideOfHitShape(x, y);
	}

	@Override
	public boolean hitShapeIsInsideOfBox(double x1, double y1, double x2, double y2) {
		return triangle.hitShapeIsInsideOfBox(x1, y1, x2, y2);
	}

	@Override
	protected void render(DragAndDropRenderContext context) {
		triangle.renderHitShape(context, context.isSelected() ? Color.RED : Color.BLACK);
	}

	@Override
	protected boolean moved(DragAndDropSelectionContext context, double dx, double dy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void renderHitShape(DragAndDropRenderContext context, Color color) {
		triangle.renderHitShape(context, color);
	}

}
