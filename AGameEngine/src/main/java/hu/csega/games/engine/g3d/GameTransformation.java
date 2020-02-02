package hu.csega.games.engine.g3d;

import org.joml.Matrix4f;

public class GameTransformation {

	private float[] m;

	public GameTransformation() {
		this.m = new float[16];
		this.m[0] = 1f;
		this.m[5] = 1f;
		this.m[10] = 1f;
		this.m[15] = 1f;
	}

	public void importFrom(Matrix4f matrix) {
		matrix.get(this.m);
	}

	public void exportTo(Matrix4f matrix) {
		matrix.set(this.m);
	}

	public float[] getFloats() {
		return m;
	}

}
