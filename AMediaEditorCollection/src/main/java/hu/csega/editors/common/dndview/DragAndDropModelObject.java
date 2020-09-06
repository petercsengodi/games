package hu.csega.editors.common.dndview;

public abstract class DragAndDropModelObject implements DragAndDropHitShape {

	private final String id;
	private final DragAndDropZoomFlag positionFlag;
	private final DragAndDropZoomFlag sizeFlag;

	public DragAndDropModelObject(DragAndDropZoomFlag positionFlag, DragAndDropZoomFlag sizeFlag) {
		this.id = DragAndDropView.generateNewID();
		this.positionFlag = positionFlag;
		this.sizeFlag = sizeFlag;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DragAndDropModelObject other = (DragAndDropModelObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	protected abstract void render(DragAndDropRenderContext context);

	/**
	 * @return True, if there was change in the model.
	 */
	protected abstract boolean moved(DragAndDropSelectionContext context, double dx, double dy);

}
