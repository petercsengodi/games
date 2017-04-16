package hu.csega.superstition.gamelib.legacy.modeldata;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class CEdge implements IModelPart {

	public CVertex from, to;

	public CEdge() {
		this.from = null;
		this.to = null;
	}

	public CEdge(CVertex from, CVertex to) {
		this.from = from;
		this.to = to;
	}

	public String toString()
	{
		if(from == null)
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

	public void move(Vector3f direction) {
		this.to.move(direction);
		this.from.move(direction);
	}

	public void moveTexture(Vector2f direction) {
		this.to.moveTexture(direction);
		this.from.moveTexture(direction);
	}

	public boolean hasPart(IModelPart part) {
		return part.equals(this) || part.equals(from) || part.equals(to);
	}

	public Vector3f centerPoint() {
		// TODO not allocationless
		Vector3f ret = from.position.add(to.position);
		ret.mul(0.5f, ret);
		return ret;
	}

	public void scale(Matrix3f matrix) {
		from.scale(matrix);
		to.scale(matrix);
	}

}