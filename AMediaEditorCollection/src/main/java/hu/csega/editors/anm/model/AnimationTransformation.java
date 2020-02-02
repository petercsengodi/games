package hu.csega.editors.anm.model;

public class AnimationTransformation {

	/** Matrix, float, 4x4. */
	private float[] m;

	public AnimationTransformation() {
		this.m = new float[16];
		this.m[0] = 1f;
		this.m[5] = 1f;
		this.m[10] = 1f;
		this.m[15] = 1f;
	}

	public float[] getM() {
		return m;
	}

	public void setM(float[] m) {
		this.m = m;
	}

}
