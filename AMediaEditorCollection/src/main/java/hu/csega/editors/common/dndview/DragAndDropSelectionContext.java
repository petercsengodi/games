package hu.csega.editors.common.dndview;

import java.util.Iterator;
import java.util.Set;

public class DragAndDropSelectionContext {

	private Set<DragAndDropModelObject> selectedObjects;
	private boolean selected;

	protected DragAndDropSelectionContext(Set<DragAndDropModelObject> selectedObjects) {
		this.selectedObjects = selectedObjects;
	}

	protected void setSelectedObjects(Set<DragAndDropModelObject> selectedObjects) {
		this.selectedObjects = selectedObjects;
	}

	protected void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isSelected(DragAndDropModelObject object) {
		return selectedObjects.contains(object);
	}

	public Iterator<DragAndDropModelObject> iteratorOfSelectedObjects() {
		return new DragAndDropSelectedObjectsIterator(selectedObjects);
	}

}
