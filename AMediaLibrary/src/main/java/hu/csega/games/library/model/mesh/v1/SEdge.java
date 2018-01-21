package hu.csega.games.library.model.mesh.v1;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import hu.csega.games.library.xml.XmlClass;
import hu.csega.games.library.xml.XmlField;

@XmlClass("Superstition.Edge")
public class SEdge implements SPhysicalObject {

	SVertex from;
	SVertex to;
	STriangle triangle;

	@XmlField("from")
	public SVertex getFrom() {
		return from;
	}

	@XmlField("from")
	public void setFrom(SVertex from) {
		this.from = from;
	}

	@XmlField("to")
	public SVertex getTo() {
		return to;
	}

	@XmlField("to")
	public void setTo(SVertex to) {
		this.to = to;
	}

	@XmlField("triangle")
	public STriangle getTriangle() {
		return triangle;
	}

	@XmlField("triangle")
	public void setTriangle(STriangle triangle) {
		this.triangle = triangle;
	}

	@Override
	public String toString() {
		if(from == null || to == null)
			return super.toString();

		return "Edge (" +
		from.position.x + ";" + from.position.y + ";" + from.position.z + ") -> (" +
		to.position.x + ";" + to.position.y + ";" + to.position.z + ")";
	}

	public float length() {
		double xl = from.position.x - to.position.x;
		double yl = from.position.y - to.position.y;
		return (float)Math.sqrt(xl * xl + yl * yl);
	}

	@Override
	public void move(Vector4f direction) {
		this.to.move(direction);
		this.from.move(direction);
	}

	@Override
	public void moveTexture(Vector2f direction) {
		this.to.moveTexture(direction);
		this.from.moveTexture(direction);
	}

	@Override
	public boolean hasPart(SPhysicalObject part) {
		return part.equals(this) || part.equals(from) || part.equals(to);
	}

	@Override
	public Vector4f centerPoint(Vector4f ret) {
		ret.set(from.position);
		ret.add(to.position, ret);
		ret.mul(0.5f, ret);
		return ret;
	}

	@Override
	public void scale(Matrix4f matrix) {
		from.scale(matrix);
		to.scale(matrix);
	}
}