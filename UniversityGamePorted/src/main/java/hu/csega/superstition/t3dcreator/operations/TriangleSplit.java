package hu.csega.superstition.t3dcreator.operations;

import org.joml.Vector2f;
import org.joml.Vector3f;

import hu.csega.superstition.gamelib.legacy.modeldata.CEdge;
import hu.csega.superstition.gamelib.legacy.modeldata.CTriangle;
import hu.csega.superstition.t3dcreator.CFigure;
import hu.csega.superstition.t3dcreator.CVertex;

public class TriangleSplit extends Operation {

	private CFigure figure;
	private CTriangle triangle;
	private CVertex vertex;
	private CTriangle[] new_triangles;

	public TriangleSplit(CFigure figure, CTriangle triangle) {
		this.figure = figure;
		this.triangle = triangle;
		this.vertex = null;
	}

	@Override
	public void OnTransform() {

		// Removes the triangle from figure
		figure.triangles.remove(triangle);

		// Creates new vertex
		Vector3f position = new Vector3f(0f, 0f, 0f);
		Vector2f tex_pos = new Vector2f(0f, 0f);

		for(CEdge e : triangle.edges) {
			position += e.from.position;
			tex_pos += e.from.texture_coordinates;
		}

		position = position * (1/3f);
		tex_pos = tex_pos * (1/3f);
		vertex = new CVertex(position, tex_pos);

		// Creates new triangles
		new_triangles = new CTriangle[3];
		new_triangles[0] = new CTriangle(triangle.edges[0].from, triangle.edges[1].from, vertex);
		new_triangles[1] = new CTriangle(triangle.edges[1].from, triangle.edges[2].from, vertex);
		new_triangles[2] = new CTriangle(triangle.edges[2].from, triangle.edges[0].from, vertex);

		// Creates neighbour connections
		new_triangles[0].neighbours[0] = triangle.neighbours[0];
		new_triangles[0].neighbours[1] = new_triangles[1];
		new_triangles[0].neighbours[2] = new_triangles[2];
		new_triangles[1].neighbours[0] = triangle.neighbours[1];
		new_triangles[1].neighbours[1] = new_triangles[2];
		new_triangles[1].neighbours[2] = new_triangles[0];
		new_triangles[2].neighbours[0] = triangle.neighbours[2];
		new_triangles[2].neighbours[1] = new_triangles[0];
		new_triangles[2].neighbours[2] = new_triangles[1];
		triangle.neighbours[0].SwitchNeighbours(triangle, new_triangles[0]);
		triangle.neighbours[1].SwitchNeighbours(triangle, new_triangles[1]);
		triangle.neighbours[2].SwitchNeighbours(triangle, new_triangles[2]);

		// Adds triangles and vertes
		figure.vertices.add(vertex);
		figure.triangles.add(new_triangles[0]);
		figure.triangles.add(new_triangles[1]);
		figure.triangles.add(new_triangles[2]);
	}

	@Override
	public void OnInvert() {

		// Remove vertex and triangles
		figure.vertices.remove(vertex);
		figure.triangles.remove(new_triangles[0]);
		figure.triangles.remove(new_triangles[1]);
		figure.triangles.remove(new_triangles[2]);

		// fixing neighbour connections
		triangle.neighbours[0].SwitchNeighbours(new_triangles[0], triangle);
		triangle.neighbours[1].SwitchNeighbours(new_triangles[1], triangle);
		triangle.neighbours[2].SwitchNeighbours(new_triangles[2], triangle);

		// Add old triangle
		figure.triangles.add(triangle);
	}

}