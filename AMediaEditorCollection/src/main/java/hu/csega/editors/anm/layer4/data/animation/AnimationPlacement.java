package hu.csega.editors.anm.layer4.data.animation;

import java.io.Serializable;

public class AnimationPlacement implements Serializable {

	private AnimationVector position;
	private AnimationVector target;
	private AnimationVector up;

	public AnimationVector getPosition() {
		return position;
	}

	public void setPosition(AnimationVector position) {
		this.position = position;
	}

	public AnimationVector getTarget() {
		return target;
	}

	public void setTarget(AnimationVector target) {
		this.target = target;
	}

	public AnimationVector getUp() {
		return up;
	}

	public void setUp(AnimationVector up) {
		this.up = up;
	}

	private static final long serialVersionUID = 1L;

}
