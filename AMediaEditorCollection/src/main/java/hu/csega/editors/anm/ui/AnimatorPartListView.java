package hu.csega.editors.anm.ui;

import java.util.List;

import hu.csega.editors.anm.components.ComponentPartListView;
import hu.csega.games.units.UnitStore;

public class AnimatorPartListView implements ComponentPartListView {

	private AnimatorUIComponents ui;

	@Override
	public Void provide() {
		return null;
	}

	@Override
	public void accept(List<AnimatorPartListItem> items) {
		if(ui == null) {
			ui = UnitStore.instance(AnimatorUIComponents.class);
			if(ui == null) {
				return;
			}
		}

		if(ui.partListModel != null) {
			ui.partListModel.setItems(items);
			if(ui.partList != null) {
				ui.partList.updateUI();
			}
		}

	}

}
