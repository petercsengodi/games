package hu.csega.games.libary.legacy.modeldata;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("T3DCreator.CTriangle")
public class CTriangle {

	public List<CEdge> edges = new ArrayList<>();
	public List<CTriangle> neighbours = new ArrayList<>();
	public List<CVertex> vertices = new ArrayList<>();
	public int count;

	public CTriangle() {
	}

	public CTriangle(CVertex v1, CVertex v2, CVertex v3) {
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
	}

	@XmlField("edges")
	public List<CEdge> getEdges() {
		return edges;
	}

	@XmlField("edges")
	public void setEdges(List<CEdge> edges) {
		this.edges = edges;
	}

	@XmlField("neighbours")
	public List<CTriangle> getNeighbours() {
		return neighbours;
	}

	@XmlField("neighbours")
	public void setNeighbours(List<CTriangle> neighbours) {
		this.neighbours = neighbours;
	}

	@XmlField("vertices")
	public List<CVertex> getVertices() {
		return vertices;
	}

	@XmlField("vertices")
	public void setVertices(List<CVertex> vertices) {
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

} // End of class CTriangle