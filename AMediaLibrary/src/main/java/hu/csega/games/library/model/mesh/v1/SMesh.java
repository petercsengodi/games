package hu.csega.games.library.model.mesh.v1;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import hu.csega.games.library.collection.VectorStack;
import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Superstition.Mesh")
public class SMesh implements SPhysicalObject {

	String name;
	List<SShape> figures = new ArrayList<>();

	@XmlField("name")
	public String getName() {
		return name;
	}

	@XmlField("name")
	public void setName(String name) {
		this.name = name;
	}

	@XmlField("figures")
	public List<SShape> getFigures() {
		return figures;
	}

	@XmlField("figures")
	public void setFigures(List<SShape> figures) {
		this.figures = figures;
	}

	@Override
	public void move(Vector4f direction) {
		for(SShape figure : figures)
			figure.move(direction);
	}

	@Override
	public void moveTexture(Vector2f direction) {
		for(SShape figure : figures)
			figure.moveTexture(direction);
	}

	@Override
	public boolean hasPart(SPhysicalObject part) {
		if(part.equals(this))
			return true;

		boolean ret = false;
		for(SShape figure : figures)
			ret |= figure.hasPart(part);

		return ret;
	}

	@Override
	public Vector4f centerPoint(Vector4f ret) {
		ret.set(0f, 0f, 0f, 0f);
		Vector4f tmp = VectorStack.newVector4f();

		float n = 1f / figures.size();
		for(SShape figure : figures) {
			figure.centerPoint(tmp).mul(n, tmp);
			ret.add(tmp, ret);
		}

		VectorStack.release(tmp);
		return ret;
	}

	@Override
	public void scale(Matrix4f matrix) {
		for(SShape figure : figures) {
			figure.scale(matrix);
		}
	}
}
