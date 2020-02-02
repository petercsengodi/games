package hu.csega.editors.anm.model;

import java.util.Map;

public class AnimationScene {

	private Map<Integer, AnimationScenePart> sceneParts;

	public Map<Integer, AnimationScenePart> getSceneParts() {
		return sceneParts;
	}

	public void setSceneParts(Map<Integer, AnimationScenePart> sceneParts) {
		this.sceneParts = sceneParts;
	}

}
