package hu.csega.superstition.unported.animatool;

import org.joml.Matrix3f;
import org.joml.Vector3f;

public interface IPart {

	Vector3f centerPoint(int scene);

	void scale(Matrix3f matrix, int scene);

	void move(Vector3f direction, int scene);

}