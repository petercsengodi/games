package hu.csega.games.library.legacy.animationdata;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Legacy.CPartData")
public class CPartData {

	@XmlField("connections")
	public CConnection[] getConnections() {
		return connections;
	}

	@XmlField("connections")
	public void setConnections(CConnection[] connections) {
		this.connections = connections;
	}

	@XmlField("center_point")
	public Vector3f[] getCenter_point() {
		return center_point;
	}

	@XmlField("center_point")
	public void setCenter_point(Vector3f[] center_point) {
		this.center_point = center_point;
	}

	@XmlField("mesh_file")
	public String getMesh_file() {
		return mesh_file;
	}

	@XmlField("mesh_file")
	public void setMesh_file(String mesh_file) {
		this.mesh_file = mesh_file;
	}

	@XmlField("model_transform")
	public Matrix4f[] getModel_transform() {
		return model_transform;
	}

	@XmlField("model_transform")
	public void setModel_transform(Matrix4f[] model_transform) {
		this.model_transform = model_transform;
	}

	public CConnection[] connections;
	public Vector3f[] center_point;
	public String mesh_file;
	public Matrix4f[] model_transform;

	public Vector3f[] bounding_box1, bounding_box2;
	public Vector3f[] bounding_sphere_center;
	public float[] bounding_sphere_radius;
}