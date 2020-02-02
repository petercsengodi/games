package hu.csega.editors.anm.model;

public class AnimationScenePart {

	private int partIndex;
	private AnimationTransformation modelTransformation;
	private AnimationTransformation partTransformation;
	private boolean visible;

	public int getPartIndex() {
		return partIndex;
	}

	public void setPartIndex(int partIndex) {
		this.partIndex = partIndex;
	}

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

}
