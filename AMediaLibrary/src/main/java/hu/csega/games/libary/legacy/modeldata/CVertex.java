package hu.csega.games.libary.legacy.modeldata;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("T3DCreator.CVertex")
public class CVertex {

	public Vector3f position;
	public Vector2f texture_coordinates;

	public List<CVertex> neighbours = new ArrayList<>();
	public List<CTriangle> triangles = new ArrayList<>();
	public List<CEdge> edges = new ArrayList<>();

	public CVertex() {
		this.texture_coordinates = new Vector2f();
		this.position = new Vector3f();
	}

	public CVertex(Vector3f v) {
		this.texture_coordinates = new Vector2f();
		this.position = new Vector3f().set(v);
	}

	@XmlField("position")
	public Vector3f getPosition() {
		return position;
	}

	@XmlField("position")
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	@XmlField("texture_coordinates")
	public Vector2f getTexture_coordinates() {
		return texture_coordinates;
	}

	@XmlField("texture_coordinates")
	public void setTexture_coordinates(Vector2f texture_coordinates) {
		this.texture_coordinates = texture_coordinates;
	}

	@XmlField("neighbours")
	public List<CVertex> getNeighbours() {
		return neighbours;
	}

	@XmlField("neighbours")
	public void setNeighbours(List<CVertex> neighbours) {
		this.neighbours = neighbours;
	}

	@XmlField("triangles")
	public List<CTriangle> getTriangles() {
		return triangles;
	}

	@XmlField("triangles")
	public void setTriangles(List<CTriangle> triangles) {
		this.triangles = triangles;
	}

	@XmlField("edges")
	public List<CEdge> getEdges() {
		return edges;
	}

	@XmlField("edges")
	public void setEdges(List<CEdge> edges) {
		this.edges = edges;
	}

	@Override
	public String toString() {
		if(position == null || texture_coordinates == null)
			return super.toString();

		return "Vertex (" + position.x + ";" + position.y + ";" + position.z + ") [" +
			texture_coordinates.x + ";" + texture_coordinates.y + "]";
	}

}