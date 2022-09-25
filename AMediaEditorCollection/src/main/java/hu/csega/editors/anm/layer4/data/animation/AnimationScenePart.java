package hu.csega.editors.anm.layer4.data.animation;

import java.io.Serializable;

public class AnimationScenePart implements Serializable {

	private AnimationTransformation modelTransformation;
	private AnimationTransformation partTransformation;
	private boolean visible;

	public AnimationTransformation getModelTransformation() {
		return modelTransformation;
	}

	public void setModelTransformation(AnimationTransformation modelTransformation) {
		this.modelTransformation = modelTransformation;
	}

	public AnimationTransformation getPartTransformation() {
		return partTransformation;
	}

	public void setPartTransformation(AnimationTransformation partTransformation) {
		this.partTransformation = partTransformation;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	private static final long serialVersionUID = 1L;

}
