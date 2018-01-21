package hu.csega.games.library.model.mesh.v1;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Superstition.Vertex")
public class SVertex implements SPhysicalObject {

	public Vector4f position;
	public Vector2f textureCoordinates;

	public List<SVertex> neighbours = new ArrayList<>();
	public List<STriangle> triangles = new ArrayList<>();
	public List<SEdge> edges = new ArrayList<>();

	public SVertex() {
		this.textureCoordinates = new Vector2f();
		this.position = new Vector4f(0f, 0f, 0f, 1f);
	}

	@XmlField("position")
	public Vector4f getPosition() {
		return position;
	}

	@XmlField("position")
	public void setPosition(Vector4f position) {
		this.position.set(position);
	}

	public void setPosition(Vector3f p) {
		this.position.set(p.x, p.y, p.z, 1f);
	}

	public void setPosition(float px, float py, float pz) {
		this.position.set(px, py, pz, 1f);
	}

	@XmlField("textureCoordinates")
	public Vector2f getTextureCoordinates() {
		return textureCoordinates;
	}

	@XmlField("textureCoordinates")
	public void setTextureCoordinates(Vector2f textureCoordinates) {
		this.textureCoordinates.set(textureCoordinates.x, textureCoordinates.y);
	}

	public void setTextureCoordinates(float tx, float ty) {
		this.textureCoordinates.set(tx, ty);
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

		return "Vertex (" + position.x + ";" + position.y +
				";" + position.z + ";" + position.w + ") [" +
				textureCoordinates.x + ";" + textureCoordinates.y + "]";
	}

	@Override
	public void move(Vector4f direction) {
		this.position.add(direction, position);
	}

	@Override
	public void moveTexture(Vector2f direction) {
		this.textureCoordinates.add(direction, this.textureCoordinates);

		if(textureCoordinates.x < 0f)
			textureCoordinates.x = 0f;
		if(textureCoordinates.x > 1f)
			textureCoordinates.x = 1f;
		if(textureCoordinates.y < 0f)
			textureCoordinates.y = 0f;
		if(textureCoordinates.y > 1f)
			textureCoordinates.y = 1f;
	}

	@Override
	public boolean hasPart(SPhysicalObject part) {
		return part.equals(this);
	}

	@Override
	public Vector4f centerPoint(Vector4f ret) {
		ret.set(this.position);
		return ret;
	}

	@Override
	public void scale(Matrix4f matrix) {
		this.position.mul(matrix, position);
	}

}
