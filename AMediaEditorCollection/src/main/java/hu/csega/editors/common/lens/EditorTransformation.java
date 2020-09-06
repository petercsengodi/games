package hu.csega.editors.common.lens;

import org.joml.Matrix4d;
import org.joml.Vector4d;

// Not thread-safe.
public class EditorTransformation implements EditorLens {

	public Matrix4d transformation;
	public Matrix4d inverse;

	private Vector4d v = new Vector4d();

	public EditorTransformation() {
		this.transformation = new Matrix4d(
				1.0, 0.0, 0.0, 0.0,
				0.0, 1.0, 0.0, 0.0,
				0.0, 0.0, 1.0, 0.0,
				0.0, 0.0, 0.0, 1.0);

		this.inverse = new Matrix4d(this.transformation);
		this.inverse.invert();
	}

	public EditorTransformation(Matrix4d transformation) {
		this.transformation = new Matrix4d(transformation);
		this.inverse = new Matrix4d(this.transformation);
		this.inverse.invert();
	}

	@Override
	public void fromModelToScreen(EditorPoint original) {
		v.x = original.getX();
		v.y = original.getY();
		v.z = original.getZ();
		v.w = original.getW();

		v = transformation.transform(v);

		original.setX(v.x);
		original.setX(v.y);
		original.setX(v.z);
		original.setX(v.w);
	}

	@Override
	public void fromScreenToModel(EditorPoint original) {
		v.x = original.getX();
		v.y = original.getY();
		v.z = original.getZ();
		v.w = original.getW();

		v = inverse.transform(v);

		original.setX(v.x);
		original.setX(v.y);
		original.setX(v.z);
		original.setX(v.w);
	}

}
