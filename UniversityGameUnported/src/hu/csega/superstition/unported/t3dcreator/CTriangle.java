package hu.csega.superstition.unported.t3dcreator;

import hu.csega.superstition.gamelib.legacy.modeldata.CEdge;
import hu.csega.superstition.gamelib.legacy.modeldata.CVertex;

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


	@Override
	public String toString() {
		return "Triangle"+ " " + count;
	}

} // End of class CTriangle