package hu.csega.superstition.unported.t3dcreator.operations;

import org.joml.Vector3f;

import hu.csega.superstition.gamelib.legacy.modeldata.CEdge;
import hu.csega.superstition.gamelib.legacy.modeldata.CTriangle;
import hu.csega.superstition.unported.t3dcreator.CFigure;
import hu.csega.superstition.unported.t3dcreator.CVertex;

public class EdgeSplit extends Operation
{
	private CFigure figure;
	private CTriangle[] old_triangles;
	private CTriangle[] new_triangles;
	private CTriangle[] neighbours;
	private CVertex[] old_vertices;
	private CVertex new_vertex;

	public EdgeSplit(CFigure figure, CEdge edge)
	{
		this.figure = figure;
		this.new_triangles = new CTriangle[4];
		this.old_triangles = new CTriangle[2];
		this.old_vertices = new CVertex[2];
		this.old_vertices[0] = edge.from;
		this.old_vertices[1] = edge.to;

		// looking up triangles for vertices
		int count = 0;
		for(CTriangle t : figure.triangles)
		{
			if(t.hasPart(edge.from) && t.hasPart(edge.to))
			{
				old_triangles[count++] = t;
				if(count == 2) break;
			}
		}

		ArrayList n = new ArrayList();
		for(CTriangle t : old_triangles[0].neighbours)
		{
			if(!(t.Equals(old_triangles[0]) || t.Equals(old_triangles[1])))
			{
				if(n.IndexOf(t) == -1) n.Add(t);;
			}
		}
		for(CTriangle t : old_triangles[1].neighbours)
		{
			if(!(t.Equals(old_triangles[0]) || t.Equals(old_triangles[1])))
			{
				if(n.IndexOf(t) == -1) n.Add(t);;
			}
		}
		neighbours = (CTriangle[])n.ToArray(typeof(CTriangle));
	}

	@Override
	public void OnTransform()
	{
		// Removing old triangels from model
		figure.triangles.remove(old_triangles[0]);
		figure.triangles.remove(old_triangles[1]);

		// Creating new vertex
		Vector3f position = (old_vertices[0].position + old_vertices[1].position) * 0.5f;
		Vector2f texture = (old_vertices[0].texture_coordinates + old_vertices[1].texture_coordinates) * 0.5f;
		new_vertex = new CVertex(position, texture);

		// Creating new triangles
		CVertex[] quadrat = new CVertex[4];
		int count = 0; boolean missed = true; int idx = -1;
		for(CEdge edge : old_triangles[0].edges) {
			quadrat[count++] = edge.from;

			if(missed) {
				if(edge.from.equals(old_vertices[0]) || edge.from.equals(old_vertices[1])) {
					idx = count;
					quadrat[count++] = new_vertex;
					missed = false;
				}
			}
		}

		new_triangles[0] = new CTriangle(quadrat[(idx + 2) % 4], quadrat[(idx + 3) % 4], quadrat[(idx + 4) % 4]);
		new_triangles[1] = new CTriangle(quadrat[(idx + 2) % 4], quadrat[(idx + 4) % 4], quadrat[(idx + 5) % 4]);

		count = 0; missed = true; idx = -1;
		for(CEdge edge : old_triangles[1].edges) {
			quadrat[count++] = edge.from;

			if(missed) {
				if(edge.from.equals(old_vertices[0]) || edge.from.equals(old_vertices[1])) {
					idx = count;
					quadrat[count++] = new_vertex;
					missed = false;
				}
			}
		}

		new_triangles[2] = new CTriangle(quadrat[(idx + 2) % 4], quadrat[(idx + 3) % 4], quadrat[(idx + 4) % 4]);
		new_triangles[3] = new CTriangle(quadrat[(idx + 2) % 4], quadrat[(idx + 4) % 4], quadrat[(idx + 5) % 4]);

		// Addig vertex and triangels to model
		figure.vertices.add(new_vertex);
		figure.triangles.add(new_triangles[0]);
		figure.triangles.add(new_triangles[1]);
		figure.triangles.add(new_triangles[2]);
		figure.triangles.add(new_triangles[3]);

		// Creating neighbour connection
		figure.CalculateNeighbours(new_triangles, neighbours);
	}


	@Override
	public void OnInvert() {

		// Remove new trianlges and vertices
		figure.triangles.remove(new_triangles[0]);
		figure.triangles.remove(new_triangles[1]);
		figure.triangles.remove(new_triangles[2]);
		figure.triangles.remove(new_triangles[3]);
		figure.vertices.remove(new_vertex);

		// Add old triangles
		figure.triangles.add(old_triangles[0]);
		figure.triangles.add(old_triangles[1]);

		// Fix neighbour connections
		figure.CalculateNeighbours(old_triangles, neighbours);
	}

}