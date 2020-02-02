package hu.csega.editors.anm.ui;

import java.awt.Component;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import hu.csega.editors.anm.components.ComponentExtractPartList;
import hu.csega.games.common.CommonUIComponent;
import hu.csega.games.units.UnitStore;

public class AnimatorPartList implements ListModel<AnimatorPartListItem>, CommonUIComponent<List<AnimatorPartListItem>, Void> {

	private ComponentExtractPartList extractor;

	private JList<AnimatorPartListItem> list;
	private List<AnimatorPartListItem> items;
	private JScrollPane scrollableList;

	public AnimatorPartList() {
		this.list = new JList<>(this);
		this.scrollableList = new JScrollPane(list);
	}

	public Component getAWTComponent() {
		return scrollableList;
	}

	@Override
	public Void provide() {
		return null;
	}

	@Override
	public void accept(List<AnimatorPartListItem> items) {
		this.items = items;
		list.repaint();
	}

	@Override
	public int getSize() {
		reloadIfNeeded();
		if(items == null)
			return 0;
		return items.size();
	}

	@Override
	public AnimatorPartListItem getElementAt(int index) {
		reloadIfNeeded();
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

	private void reloadIfNeeded() {
		if(extractor == null) {
			extractor = UnitStore.instance(ComponentExtractPartList.class);
		}

		if(items == null) {
			items = extractor.provide();
		}
	}
}
