package hu.csega.editors.anm.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import hu.csega.editors.anm.model.parts.AnimatorPart;

public class AnimatorModel implements ListModel<AnimatorPart> {

	public boolean gridEnabled = true;
	public double gridSize = 30.0;

	private List<AnimatorPart> parts = new ArrayList<>();

	public AnimatorModel() {
		for(int i = 0; i < 100; i++) {
			AnimatorPart part = new AnimatorPart();
			part.setId("id-" + i);
			part.setDisplayName("Part " + i);
			parts.add(part);
		}
	}

	public void finalizeMove() {
	}

	@Override
	public int getSize() {
		return parts.size();
	}

	@Override
	public AnimatorPart getElementAt(int index) {
		return parts.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
	}

}
