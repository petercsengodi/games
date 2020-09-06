package hu.csega.editors.common.lens;

import java.util.List;

import java.util.ArrayList;

import org.joml.Matrix4d;
import org.joml.Vector4d;

// Not thread-safe.
public class TransformationStack {

	private Matrix4d transformation = new Matrix4d(
			1.0, 0.0, 0.0, 0.0,
			0.0, 1.0, 0.0, 0.0,
			0.0, 0.0, 1.0, 0.0,
			0.0, 0.0, 0.0, 1.0);

	private Vector4d v = new Vector4d();

	private List<Matrix4d> stack = new ArrayList<>();

	public void push(Matrix4d transformation) {
		Matrix4d current = new Matrix4d(this.transformation);
		stack.add(current);
		this.transformation = this.transformation.mul(transformation);
	}

	public Matrix4d pop() {
		if(!stack.isEmpty()) {
			Matrix4d value = stack.remove(stack.size() - 1);
			this.transformation.set(value);
			return value;
		} else {
			return null;
		}
	}

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

}
