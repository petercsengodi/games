package hu.csega.superstition.gamelib.legacy.modeldata;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public interface IModelPart {

	void move(Vector3f direction);

	void moveTexture(Vector2f direction);

	boolean hasPart(IModelPart part);

	Vector3f centerPoint();

	void scale(Matrix3f matrix);
}