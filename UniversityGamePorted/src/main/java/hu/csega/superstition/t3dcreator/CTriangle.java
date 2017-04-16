package hu.csega.superstition.gamelib.legacy.modeldata;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class CTriangle implements IModelPart {
	public CEdge[] edges;
	public CTriangle[] neighbours;

	static int _count = 0;
	int count;

	public CTriangle() {
		neighbours = new CTriangle[3];
		edges = new CEdge[3];

		count = _count++;
	}

	public CTriangle(CVertex a, CVertex b, CVertex c) {
		neighbours = new CTriangle[3];
		edges = new CEdge[3];
		edges[0] = new CEdge(a, b);
		edges[1] = new CEdge(b, c);
		edges[2] = new CEdge(c, a);

		count = _count++;
	}

	public CTriangle(Object a, Object b, Object c) {
		neighbours = new CTriangle[3];
		edges = new CEdge[3];
		edges[0] = new CEdge((CVertex)a, (CVertex)b);
		edges[1] = new CEdge((CVertex)b, (CVertex)c);
		edges[2] = new CEdge((CVertex)c, (CVertex)a);

		count = _count++;

	}

	public void SwitchNeighbours(CTriangle from, CTriangle to) {
		for(int i = 0; i < 3; i++)
		{
			if(neighbours[i].equals(from))
				neighbours[i] = to;
		}
	}


	public String toString() {
		return "Triangle"+ " " + count;
	}

	public void move(Vector3f direction) {
		for(CEdge e : edges) {
			e.from.move(direction);
		}
	}

	public void moveTexture(Vector2f direction)
	{
		for(CEdge e : edges) {
			e.from.moveTexture(direction);
		}
	}

	public boolean hasPart(IModelPart part)
	{
		if(part.equals(this))
			return true;

		boolean ret = false;
		for(CEdge e : edges) {
			ret |= part.equals(e);
			ret |= part.equals(e.from);
		}

		return ret;
	}

	public Vector3f centerPoint() {
		// TODO allocationless
		Vector3f ret = new Vector3f(0f, 0f, 0f);
		ret.add(edges[0].from.position, ret);
		ret.add(edges[1].from.position, ret);
		ret.add(edges[2].from.position, ret);
		ret.mul(1f/3f, ret);
		return ret;
	}

	public void scale(Matrix3f matrix) {
		for(CEdge edge : edges) {
			edge.from.scale(matrix);
		}
	}

} // End of class CTriangle