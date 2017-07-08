package hu.csega.superstition.unported.game.animation;

import org.joml.Matrix3f;
import org.joml.Vector3f;

public class AnimPart {
	public MeshElement element;
	public Vector3f[] center;
	public Matrix3f[] worlds;
	public Vector3f[] bounding_box1, bounding_box2;
	public Vector3f[] bounding_sphere_center;
	public float[] bounding_sphere_radius;
}