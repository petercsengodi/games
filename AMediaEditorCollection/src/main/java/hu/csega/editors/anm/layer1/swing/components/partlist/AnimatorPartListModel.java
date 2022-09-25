package hu.csega.editors.anm.layer1.swing.components.partlist;

import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class AnimatorPartListModel implements ListModel<AnimatorPartListItem> {

	private List<AnimatorPartListItem> items = null;

	public void setItems(List<AnimatorPartListItem> items) {
		this.items = items;
	}

	@Override
	public int getSize() {
		if(items == null)
			return 0;
		return items.size();
	}

	@Override
	public AnimatorPartListItem getElementAt(int index) {
		if(items == null)
			return null;
		return items.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
	}
}
