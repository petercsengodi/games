package hu.csega.editors.anm.layer4.data.animation;

import java.io.Serializable;

public class AnimationPartJoint implements Serializable {

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

	private static final long serialVersionUID = 1L;

}
