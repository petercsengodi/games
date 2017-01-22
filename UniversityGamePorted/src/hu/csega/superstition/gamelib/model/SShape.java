package hu.csega.superstition.gamelib.model;

import java.util.ArrayList;
import java.util.List;

import hu.csega.superstition.xml.XmlClass;
import hu.csega.superstition.xml.XmlField;

@XmlClass("Superstition.Shape")
public class SShape implements SObject {

	@XmlField("vertices")
	public List<SVertex> getVertices() {
		return vertices;
	}

	@XmlField("vertices")
	public void setVertices(List<SVertex> vertices) {
		this.vertices = vertices;
	}

	@XmlField("triangles")
	public List<STriangle> getTriangles() {
		return triangles;
	}

	@XmlField("triangles")
	public void setTriangles(List<STriangle> triangles) {
		this.triangles = triangles;
	}

	@XmlField("texture")
	public STextureRef getTexture() {
		return texture;
	}

	@XmlField("texture")
	public void setTexture(STextureRef texture) {
		this.texture = texture;
	}

	@XmlField("ambientColor")
	public int getAmbientColor() {
		return ambientColor;
	}

	@XmlField("ambientColor")
	public void setAmbientColor(int ambientColor) {
		this.ambientColor = ambientColor;
	}

	@XmlField("diffuseColor")
	public int getDiffuseColor() {
		return diffuseColor;
	}

	@XmlField("diffuseColor")
	public void setDiffuseColor(int diffuseColor) {
		this.diffuseColor = diffuseColor;
	}

	@XmlField("emissiveColor")
	public int getEmissiveColor() {
		return emissiveColor;
	}

	@XmlField("emissiveColor")
	public void setEmissiveColor(int emissiveColor) {
		this.emissiveColor = emissiveColor;
	}

	private int ambientColor;
	private int diffuseColor;
	private int emissiveColor;

	private List<SVertex> vertices = new ArrayList<>();
	private List<STriangle> triangles = new ArrayList<>();
	private STextureRef texture;
}
