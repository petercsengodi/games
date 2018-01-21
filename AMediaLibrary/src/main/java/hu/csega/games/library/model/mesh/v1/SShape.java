package hu.csega.games.library.model.mesh.v1;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import hu.csega.games.library.collection.VectorStack;
import hu.csega.games.library.model.STextureRef;
import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Superstition.Shape")
public class SShape implements SPhysicalObject {

	int ambientColor;
	int diffuseColor;
	int emissiveColor;

	List<SVertex> vertices = new ArrayList<>();
	List<STriangle> triangles = new ArrayList<>();
	STextureRef texture;

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

	@Override
	public void move(Vector4f direction) {
		for(SVertex v : this.vertices) {
			v.move(direction);
		}
	}

	@Override
	public void moveTexture(Vector2f direction) {
		for(SVertex v : this.vertices) {
			v.moveTexture(direction);
		}
	}

	@Override
	public boolean hasPart(SPhysicalObject part)
	{
		if(part.equals(this))
			return true;

		boolean ret = false;
		for(STriangle t : triangles) {
			ret |= t.hasPart(part);
		}
		return ret;
	}

	@Override
	public Vector4f centerPoint(Vector4f ret) {
		ret.set(0f, 0f, 0f, 0f);
		Vector4f tmp = VectorStack.newVector4f();

		float n = 1f / vertices.size();
		for(SVertex vertex : vertices) {
			vertex.position.mul(n, tmp);
			ret.add(tmp, ret);
		}

		VectorStack.release(tmp);
		return ret;
	}

	@Override
	public void scale(Matrix4f matrix) {
		for(SVertex vertex : vertices) {
			vertex.scale(matrix);
		}
	}
}
