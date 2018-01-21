package hu.csega.games.library.model.mesh.v1;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Superstition.Triangle")
public class STriangle implements SPhysicalObject {

	List<SEdge> edges = new ArrayList<>();
	List<STriangle> neighbours = new ArrayList<>();
	List<SVertex> vertices = new ArrayList<>();
	int count;

	@XmlField("edges")
	public List<SEdge> getEdges() {
		return edges;
	}

	@XmlField("edges")
	public void setEdges(List<SEdge> edges) {
		this.edges = edges;
	}

	@XmlField("neighbours")
	public List<STriangle> getNeighbours() {
		return neighbours;
	}

	@XmlField("neighbours")
	public void setNeighbours(List<STriangle> neighbours) {
		this.neighbours = neighbours;
	}

	@XmlField("vertices")
	public List<SVertex> getVertices() {
		return vertices;
	}

	@XmlField("vertices")
	public void setVertices(List<SVertex> vertices) {
		this.vertices = vertices;
	}

	@XmlField("count")
	public int getCount() {
		return count;
	}

	@XmlField("count")
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Triangle"+ " " + count;
	}

	@Override
	public void move(Vector4f direction) {
		for(SEdge e : edges) {
			e.from.move(direction);
		}
	}

	@Override
	public void moveTexture(Vector2f direction) {
		for(SEdge e : edges) {
			e.from.moveTexture(direction);
		}
	}

	@Override
	public boolean hasPart(SPhysicalObject part) {
		if(part.equals(this))
			return true;

		boolean ret = false;
		for(SEdge e : edges) {
			ret |= part.equals(e);
			ret |= part.equals(e.from);
		}

		return ret;
	}

	@Override
	public Vector4f centerPoint(Vector4f ret) {
		ret.set(0f, 0f, 0f, 0f);

		for(SEdge edge : edges) {
			ret.add(edge.from.position, ret);
		}

		ret.mul(1f/3f, ret);
		return ret;
	}

	@Override
	public void scale(Matrix4f matrix) {
		for(SEdge edge : edges) {
			edge.from.scale(matrix);
		}
	}
}
