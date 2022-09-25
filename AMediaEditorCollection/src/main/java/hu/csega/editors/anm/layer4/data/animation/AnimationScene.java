package hu.csega.editors.anm.layer4.data.animation;

import java.io.Serializable;
import java.util.Map;

public class AnimationScene implements Serializable {

	private Map<Integer, AnimationScenePart> sceneParts;

	public Map<Integer, AnimationScenePart> getSceneParts() {
		return sceneParts;
	}

	public void setSceneParts(Map<Integer, AnimationScenePart> sceneParts) {
		this.sceneParts = sceneParts;
	}

	private static final long serialVersionUID = 1L;

}
