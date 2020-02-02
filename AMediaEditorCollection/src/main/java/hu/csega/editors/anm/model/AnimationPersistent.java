package hu.csega.editors.anm.model;

public class AnimationPersistent {

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

}
