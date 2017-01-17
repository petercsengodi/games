package hu.csega.superstition.t3dcreator;

public class CTriangle : IPart
{
	public CEdge[] edges;
	public CTriangle[] neighbours;

	static int _count = 0;
	int count;

	public CTriangle()
	{
		neighbours = new CTriangle[3];
		edges = new CEdge[3];

		count = _count++;
	}

	public CTriangle(CVertex a, CVertex b, CVertex c)
	{
		neighbours = new CTriangle[3];
		edges = new CEdge[3];
		edges[0] = new CEdge(a, b);
		edges[1] = new CEdge(b, c);
		edges[2] = new CEdge(c, a);

		count = _count++;
	}

	public CTriangle(object a, object b, object c)
	{
		neighbours = new CTriangle[3];
		edges = new CEdge[3];
		edges[0] = new CEdge((CVertex)a, (CVertex)b);
		edges[1] = new CEdge((CVertex)b, (CVertex)c);
		edges[2] = new CEdge((CVertex)c, (CVertex)a);

		count = _count++;

	}

	public void SwitchNeighbours(CTriangle from, CTriangle to)
	{
		for(int i = 0; i < 3; i++)
		{
			if(neighbours[i].Equals(from))
				neighbours[i] = to;
		}
	}


	public override string ToString()
	{
		return "Triangle"+ " " + count;
	}

	#region IPart Members

	public void move(Vector3 direction)
	{
		foreach(CEdge e in edges)
		{
			e.from.move(direction);
		}
	}

	public void moveTexture(Vector2 direction)
	{
		foreach(CEdge e in edges)
		{
			e.from.moveTexture(direction);
		}
	}

	public bool hasPart(IPart part)
	{
		if(part.Equals(this)) return true;
		bool ret = false;
		foreach(CEdge e in edges)
		{
			ret |= part.Equals(e);
			ret |= part.Equals(e.from);
		}
		return ret;
	}

	public Vector3 centerPoint()
	{
		Vector3 ret = new Vector3(0f, 0f, 0f);
		ret += edges[0].from.position;
		ret += edges[1].from.position;
		ret += edges[2].from.position;
		ret = ret * (1f/3f);
		return ret;
	}

	public void scale(Matrix matrix)
	{
		foreach(CEdge edge in edges)
		{
			edge.from.scale(matrix);
		}
	}

	#endregion

} // End of class CTriangle