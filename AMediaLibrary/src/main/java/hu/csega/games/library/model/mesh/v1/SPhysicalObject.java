package hu.csega.games.library.model.mesh.v1;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import hu.csega.games.library.model.SObject;

public interface SPhysicalObject extends SObject {

	Vector4f centerPoint(Vector4f ret);

	void move(Vector4f direction);

	void moveTexture(Vector2f direction);

	void scale(Matrix4f matrix);

	boolean hasPart(SPhysicalObject part);

}
