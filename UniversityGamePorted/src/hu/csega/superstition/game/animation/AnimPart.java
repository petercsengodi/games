package hu.csega.superstition.game.animation;

public class AnimPart
{
	public MeshElement element;
	public Vector3[] center;
	public Matrix[] worlds;
	public Vector3[] bounding_box1, bounding_box2;
	public Vector3[] bounding_sphere_center;
	public float[] bounding_sphere_radius;
}