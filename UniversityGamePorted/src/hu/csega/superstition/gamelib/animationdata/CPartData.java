package hu.csega.superstition.gamelib.animationdata;

import org.joml.Matrix3f;
import org.joml.Vector3f;

public class CPartData {
	public CConnection[] connections;
	public Vector3f[] center_point;
	public String mesh_file;
	public Matrix3f[] model_transform;
	public Vector3f[] bounding_box1, bounding_box2;
	public Vector3f[] bounding_sphere_center;
	public float[] bounding_sphere_radius;
}