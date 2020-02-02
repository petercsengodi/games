package hu.csega.editors.anm.model;

public class AnimationVector {

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

}
