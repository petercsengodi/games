package hu.csega.editors.anm.model;

public class AnimationPartJoint {

	private int partIndex;
	private AnimationVector relativePosition;
	private AnimationTransformation relativeTransformation;

	public int getPartIndex() {
		return partIndex;
	}

	public void setPartIndex(int partIndex) {
		this.partIndex = partIndex;
	}

	public AnimationVector getRelativePosition() {
		return relativePosition;
	}

	public void setRelativePosition(AnimationVector relativePosition) {
		this.relativePosition = relativePosition;
	}

	public AnimationTransformation getRelativeTransformation() {
		return relativeTransformation;
	}

	public void setRelativeTransformation(AnimationTransformation relativeTransformation) {
		this.relativeTransformation = relativeTransformation;
	}

}
