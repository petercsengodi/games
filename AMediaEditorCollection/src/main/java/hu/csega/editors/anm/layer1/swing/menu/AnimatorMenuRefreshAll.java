package hu.csega.editors.anm.layer1.swing.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hu.csega.editors.anm.components.ComponentRefreshViews;
import hu.csega.games.units.UnitStore;

public class AnimatorMenuRefreshAll implements ActionListener {

	private ComponentRefreshViews refreshViews;

	@Override
	public void actionPerformed(ActionEvent e) {
		if(refreshViews == null) {
			refreshViews = UnitStore.instance(ComponentRefreshViews.class);
			if(refreshViews == null) {
				return;
			}
		}

		refreshViews.refreshAll();
	}

}
