package hu.csega.editors.anm.layer4.data.model;

import java.io.Serializable;

import hu.csega.editors.anm.layer4.data.animation.Animation;
import hu.csega.editors.anm.layer4.data.animation.AnimationMisc;

public class AnimationPersistent implements Serializable {

	private String name;
	private Animation animation;
	private AnimationMisc misc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public AnimationMisc getMisc() {
		return misc;
	}

	public void setMisc(AnimationMisc misc) {
		this.misc = misc;
	}

	private static final long serialVersionUID = 1L;

}
