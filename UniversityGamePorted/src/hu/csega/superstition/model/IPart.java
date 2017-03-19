package hu.csega.superstition.model;

import org.joml.Matrix3f;
import org.joml.Vector3f;

public interface IPart {


	Vector3f centerPoint(int scene);

	void scale(Matrix3f matrix, int scene);

	void move(Vector3f direction, int scene);

	void move(Vector3f direction);
	void moveTexture(Vector2 direction);
	boolean hasPart(IPart part);
	Vector3f centerPoint();
	void scale(Matrix matrix);

}
