package hu.csega.editors.anm.layer4.data.animation;

import java.io.Serializable;

public class AnimationVector implements Serializable {

	/** Vector, float, 4x1. */
	private float[] v;

	public AnimationVector() {
		this.v = new float[4];
		this.v[3] = 1f;
	}

	public float[] getV() {
		return v;
	}

	public void setV(float[] v) {
		this.v = v;
	}

	private static final long serialVersionUID = 1L;

}
