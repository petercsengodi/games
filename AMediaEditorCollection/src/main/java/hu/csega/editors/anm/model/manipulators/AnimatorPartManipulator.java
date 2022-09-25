package hu.csega.editors.anm.model.manipulators;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import hu.csega.editors.anm.components.ComponentPartManipulator;
import hu.csega.editors.anm.components.ComponentRefreshViews;
import hu.csega.editors.anm.layer4.data.animation.Animation;
import hu.csega.editors.anm.layer4.data.animation.AnimationPart;
import hu.csega.editors.anm.layer4.data.animation.AnimationPartJoint;
import hu.csega.editors.anm.layer4.data.animation.AnimationTransformation;
import hu.csega.editors.anm.layer4.data.model.AnimationPersistent;
import hu.csega.editors.anm.layer4.data.model.AnimatorModel;
import hu.csega.games.common.CommonManipulable;
import hu.csega.games.units.UnitStore;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class AnimatorPartManipulator implements ComponentPartManipulator {

	private AnimatorModel cachedModel;
	private ComponentRefreshViews refreshViews;

	@Override
	public void addObjectToManipulate(CommonManipulable arg0) {
	}

	@Override
	public void accept(AnimatorModel arg0) {
	}

	@Override
	public void addNewPart(String filename) {
		AnimatorModel model = getModel();

		synchronized (model) {
			AnimationPersistent persistent = model.getPersistent();
			if(persistent == null) {
				persistent = new AnimationPersistent();
				model.setPersistent(persistent);
			}

			Animation animation = persistent.getAnimation();
			if(animation == null) {
				animation = new Animation();
				persistent.setAnimation(animation);
			}

			int lastIndex = animation.getMaxPartIndex();
			int newIndex = lastIndex + 1;
			animation.setMaxPartIndex(newIndex);

			Map<Integer, AnimationPart> parts = animation.getParts();
			if(parts == null) {
				parts = new TreeMap<>();
				animation.setParts(parts);
			}

			AnimationPart part = new AnimationPart();
			part.setMesh(filename);
			part.setBasicTransformation(new AnimationTransformation());
			part.setJoints(new ArrayList<AnimationPartJoint>());

			parts.put(newIndex, part);
			animation.cleanUpScenes();
		}

		refreshUI();
	}

	private AnimatorModel getModel() {
		if(cachedModel == null) {
			cachedModel = UnitStore.instance(AnimatorModel.class);
		}

		return cachedModel;
	}

	private void refreshUI() {
		if(refreshViews == null) {
			refreshViews = UnitStore.instance(ComponentRefreshViews.class);
			if(refreshViews == null) {
				logger.error("Missing component: " + ComponentRefreshViews.class.getSimpleName());
				return;
			}
		}

		refreshViews.refreshAll();
	}

	private static final Logger logger = LoggerFactory.createLogger(AnimatorPartManipulator.class);
}
