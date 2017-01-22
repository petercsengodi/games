package hu.csega.superstition.gamelib.model;

import java.util.ArrayList;
import java.util.List;

import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("Superstition.Triangle")
public class STriangle implements SObject {

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

	private List<SEdge> edges = new ArrayList<>();
	private List<STriangle> neighbours = new ArrayList<>();
	private List<SVertex> vertices = new ArrayList<>();
	private int count;
}
