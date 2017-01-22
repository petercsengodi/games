package hu.csega.superstition.gamelib.model.mesh;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import hu.csega.superstition.gamelib.model.SObject;
import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("Superstition.Vertex")
public class SVertex implements SObject {

	public SVertex() {
		this.textureCoordinates = new Vector2f();
		this.position = new Vector3f();
	}

	@XmlField("position")
	public Vector3f getPosition() {
		return position;
	}

	@XmlField("position")
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	@XmlField("textureCoordinates")
	public Vector2f getTextureCoordinates() {
		return textureCoordinates;
	}

	@XmlField("textureCoordinates")
	public void setTextureCoordinates(Vector2f textureCoordinates) {
		this.textureCoordinates = textureCoordinates;
	}

	@XmlField("neighbours")
	public List<SVertex> getNeighbours() {
		return neighbours;
	}

	@XmlField("neighbours")
	public void setNeighbours(List<SVertex> neighbours) {
		this.neighbours = neighbours;
	}

	@XmlField("triangles")
	public List<STriangle> getTriangles() {
		return triangles;
	}

	@XmlField("triangles")
	public void setTriangles(List<STriangle> triangles) {
		this.triangles = triangles;
	}

	@XmlField("edges")
	public List<SEdge> getEdges() {
		return edges;
	}

	@XmlField("edges")
	public void setEdges(List<SEdge> edges) {
		this.edges = edges;
	}

	@Override
	public String toString() {
		if(position == null || textureCoordinates == null)
			return super.toString();

		return "Vertex (" + position.x + ";" + position.y + ";" + position.z + ") [" +
			textureCoordinates.x + ";" + textureCoordinates.y + "]";
	}

	public Vector3f position;
	public Vector2f textureCoordinates;

	public List<SVertex> neighbours = new ArrayList<>();
	public List<STriangle> triangles = new ArrayList<>();
	public List<SEdge> edges = new ArrayList<>();

}
