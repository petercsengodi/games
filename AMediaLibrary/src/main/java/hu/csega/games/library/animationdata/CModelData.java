package hu.csega.games.library.animationdata;

import java.util.List;

import org.joml.Vector3f;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Superstition.CModelData")
public class CModelData
{

	@XmlField("parts")
	public List<CPartData> getParts() {
		return parts;
	}

	@XmlField("parts")
	public void setParts(List<CPartData> parts) {
		this.parts = parts;
	}

	@XmlField("max_scenes")
	public int getMax_scenes() {
		return max_scenes;
	}

	@XmlField("max_scenes")
	public void setMax_scenes(int max_scenes) {
		this.max_scenes = max_scenes;
	}

	@XmlField("named_connections")
	public List<CNamedConnection> getNamed_connections() {
		return named_connections;
	}

	@XmlField("named_connections")
	public void setNamed_connections(List<CNamedConnection> named_connections) {
		this.named_connections = named_connections;
	}

	public List<CPartData> parts;
	public int max_scenes;

	public List<CNamedConnection> named_connections;
	public Vector3f[] bounding_box1, bounding_box2;
	public Vector3f[] bounding_sphere_center;
	public float[] bounding_sphere_radius;
}