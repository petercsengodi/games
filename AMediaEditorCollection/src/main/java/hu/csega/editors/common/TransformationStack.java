package hu.csega.editors.common;

import java.util.List;

import java.util.ArrayList;

import org.joml.Matrix4d;
import org.joml.Vector4d;

import hu.csega.editors.anm.model.AnimatorTransformation;

// Not thread-safe.
public class TransformationStack {

	private Matrix4d transformation = new Matrix4d(
			1.0, 0.0, 0.0, 0.0,
			0.0, 1.0, 0.0, 0.0,
			0.0, 0.0, 1.0, 0.0,
			0.0, 0.0, 0.0, 1.0);

	private Vector4d v = new Vector4d();

	private List<Matrix4d> stack = new ArrayList<>();

	public void push(AnimatorTransformation transformation) {
		Matrix4d matrix = new Matrix4d();
		double[][] m = transformation.matrix;

		matrix.m00(m[0][0]);
		matrix.m01(m[0][1]);
		matrix.m02(m[0][2]);
		matrix.m03(m[0][3]);

		matrix.m10(m[1][0]);
		matrix.m11(m[1][1]);
		matrix.m12(m[1][2]);
		matrix.m13(m[1][3]);

		matrix.m20(m[2][0]);
		matrix.m21(m[2][1]);
		matrix.m22(m[2][2]);
		matrix.m23(m[2][3]);

		matrix.m30(m[3][0]);
		matrix.m31(m[3][1]);
		matrix.m32(m[3][2]);
		matrix.m33(m[3][3]);
	}

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
