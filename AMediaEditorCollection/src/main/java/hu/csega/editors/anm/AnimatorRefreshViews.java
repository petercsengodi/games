package hu.csega.editors.anm;

import hu.csega.editors.anm.components.ComponentExtractPartList;
import hu.csega.editors.anm.components.ComponentOpenGLExtractor;
import hu.csega.editors.anm.components.ComponentRefreshViews;
import hu.csega.editors.anm.layer4.data.animation.Animation;
import hu.csega.editors.anm.layer4.data.model.AnimationPersistent;
import hu.csega.editors.anm.layer4.data.model.AnimatorModel;
import hu.csega.games.units.UnitStore;

public class AnimatorRefreshViews implements ComponentRefreshViews {

	private AnimatorModel model;
	private ComponentExtractPartList partListExtractor;
	private ComponentOpenGLExtractor openGLExtractor;

	@Override
	public void refreshAll() {
		if(model == null) {
			model = UnitStore.instance(AnimatorModel.class);
			if(model == null) {
				return;
			}
		}

		if(partListExtractor == null) {
			partListExtractor = UnitStore.instance(ComponentExtractPartList.class);
		}

		if(openGLExtractor == null) {
			openGLExtractor = UnitStore.instance(ComponentOpenGLExtractor.class);
		}

		AnimationPersistent persistent = model.getPersistent();
		Animation animation = null;

		if(persistent != null) {
			animation = persistent.getAnimation();
		}

		if(partListExtractor != null) {
			synchronized (model) {
				partListExtractor.accept(animation);
			}
		}

		if(openGLExtractor != null) {
			openGLExtractor.accept(model);
		}

	} // end of refreshAll

}
