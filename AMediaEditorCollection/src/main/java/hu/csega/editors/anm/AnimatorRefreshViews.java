package hu.csega.editors.anm;

import hu.csega.editors.anm.components.ComponentExtractPartList;
import hu.csega.editors.anm.components.ComponentRefreshViews;
import hu.csega.editors.anm.model.Animation;
import hu.csega.editors.anm.model.AnimationPersistent;
import hu.csega.editors.anm.model.AnimatorModel;
import hu.csega.editors.anm.ui.AnimatorUIComponents;
import hu.csega.games.units.UnitStore;

public class AnimatorRefreshViews implements ComponentRefreshViews {

	private AnimatorModel model;
	private AnimatorUIComponents ui;
	private ComponentExtractPartList partListExtractor;

	@Override
	public void refreshAll() {
		if(model == null) {
			model = UnitStore.instance(AnimatorModel.class);
			if(model == null) {
				return;
			}
		}

		if(ui == null) {
			ui = UnitStore.instance(AnimatorUIComponents.class);
			if(ui == null) {
				return;
			}
		}


		if(partListExtractor == null) {
			partListExtractor = UnitStore.instance(ComponentExtractPartList.class);
		}

		AnimationPersistent persistent = model.getPersistent();
		Animation animation = null;

		if(persistent != null) {
			animation = persistent.getAnimation();
		}

		if(partListExtractor != null) {
			partListExtractor.accept(animation);
		}

	} // end of refreshAll

}
