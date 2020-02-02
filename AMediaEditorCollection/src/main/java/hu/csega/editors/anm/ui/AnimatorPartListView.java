package hu.csega.editors.anm.ui;

import java.util.List;

import hu.csega.editors.anm.components.ComponentPartListView;
import hu.csega.games.units.UnitStore;

public class AnimatorPartListView implements ComponentPartListView {

	private AnimatorUIComponents uiComponents;

	@Override
	public Void provide() {
		return null;
	}

	@Override
	public void accept(List<AnimatorPartListItem> items) {
		if(uiComponents == null) {
			uiComponents = UnitStore.instance(AnimatorUIComponents.class);
			if(uiComponents == null) {
				return;
			}
		}

		if(uiComponents.partListModel != null) {
			uiComponents.partListModel.setItems(items);
		}

		if(uiComponents.partList != null) {
			uiComponents.partList.repaint();
		}
	}

}
