package hu.csega.superstition.gamelib.animationdata;

import java.util.ArrayList;

import org.joml.Vector3f;

public class CModelData
{
	public ArrayList<CPartData> parts;
	public int max_scenes;
	public ArrayList<CNamedConnection> named_connections;
	public Vector3f[] bounding_box1, bounding_box2;
	public Vector3f[] bounding_sphere_center;
	public float[] bounding_sphere_radius;
}