package hu.csega.games.library.mwc.v1;

import java.util.List;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("mesh")
public class MWCSimpleMesh {

	private String name;

	private String texture;
	private List<MWCVertex> vertices;
	private List<Integer> indices;

	private List<MWCSphere> spheres;

	public MWCSimpleMesh() {
	}

	public MWCSimpleMesh(String name, String texture, List<MWCVertex> vertices, List<Integer> indices, List<MWCSphere> spheres) {
		this.name = name;
		this.texture = texture;
		this.vertices = vertices;
		this.indices = indices;
		this.spheres = spheres;
	}

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
	}

	@XmlField("texture")
	public String getTexture() {
		return texture;
	}

	@XmlField("texture")
	public void setTexture(String texture) {
		this.texture = texture;
	}

	@XmlField("vertices")
	public List<MWCVertex> getVertices() {
		return vertices;
	}

	@XmlField("vertices")
	public void setVertices(List<MWCVertex> vertices) {
		this.vertices = vertices;
	}

	@XmlField("indices")
	public List<Integer> getIndices() {
		return indices;
	}

	@XmlField("indices")
	public void setIndices(List<Integer> indices) {
		this.indices = indices;
	}

	@XmlField("spheres")
	public List<MWCSphere> getSpheres() {
		return spheres;
	}

	@XmlField("spheres")
	public void setSpheres(List<MWCSphere> spheres) {
		this.spheres = spheres;
	}

}
