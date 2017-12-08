package hu.csega.games.libary.legacy.modeldata;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.libary.legacy.modeldata.CTriangle;
import hu.csega.games.libary.legacy.modeldata.CVertex;
import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("T3DCreator.CFigure")
public class CFigure {

	@XmlField("vertices")
	public List<CVertex> getVertices() {
		return vertices;
	}

	@XmlField("vertices")
	public void setVertices(List<CVertex> vertices) {
		this.vertices = vertices;
	}

	@XmlField("triangles")
	public List<CTriangle> getTriangles() {
		return triangles;
	}

	@XmlField("triangles")
	public void setTriangles(List<CTriangle> triangles) {
		this.triangles = triangles;
	}

	@XmlField("texID")
	public CTexID getTexID() {
		return texID;
	}

	@XmlField("texID")
	public void setTexID(CTexID texID) {
		this.texID = texID;
	}

	@XmlField("ambient_color")
	public int getAmbient_color() {
		return ambient_color;
	}

	@XmlField("ambient_color")
	public void setAmbient_color(int ambient_color) {
		this.ambient_color = ambient_color;
	}

	@XmlField("diffuse_color")
	public int getDiffuse_color() {
		return diffuse_color;
	}

	@XmlField("diffuse_color")
	public void setDiffuse_color(int diffuse_color) {
		this.diffuse_color = diffuse_color;
	}

	@XmlField("emissive_color")
	public int getEmissive_color() {
		return emissive_color;
	}

	@XmlField("emissive_color")
	public void setEmissive_color(int emissive_color) {
		this.emissive_color = emissive_color;
	}

	public List<CVertex> vertices = new ArrayList<>();
	public List<CTriangle> triangles = new ArrayList<>();
	public CTexID texID;

	public int ambient_color;
	public int diffuse_color;
	public int emissive_color;


} // End of class Figure