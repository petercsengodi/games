package hu.csega.editors.common.dndview;

import java.util.Iterator;
import java.util.Set;

class DragAndDropSelectedObjectsIterator implements Iterator<DragAndDropModelObject> {

	private Iterator<DragAndDropModelObject> it;

	protected DragAndDropSelectedObjectsIterator(Set<DragAndDropModelObject> objects) {
		this.it = objects.iterator();
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public DragAndDropModelObject next() {
		return it.next();
	}

}
