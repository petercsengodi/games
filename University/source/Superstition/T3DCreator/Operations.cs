using System;
using System.Collections;

using Microsoft.DirectX;

namespace T3DCreator
{
	/// <summary>
	/// Summary description for Operations.
	/// </summary>
	public abstract class Operation
	{
		private bool done = false;
		public bool Done
		{
			get{ return done; }
		}

		public Operation()
		{
		}

		public void Transform()
		{
			if(done) return;
			OnTransform();
			done = true;
		}
        
		public abstract void OnTransform();

		public void Invert()
		{
			if(!done) return;
			OnInvert();
			done = false;
		}

		public abstract void OnInvert();

	}

	/// <summary>
	/// Tears a triangle into three pieces.
	/// Removes a triangle.
	/// Adds a vertex and three triangles.
	/// </summary>
	public class TriangleSplit : Operation
	{
		private CFigure figure;
		private CTriangle triangle;
		private CVertex vertex;
		private CTriangle[] new_triangles;

		public TriangleSplit(CFigure figure, CTriangle triangle)
		{
			this.figure = figure;            
			this.triangle = triangle;
			this.vertex = null;
		}

		public override void OnTransform()
		{
			// Removes the triangle from figure
			figure.triangles.Remove(triangle);

			// Creates new vertex
			Vector3 position = new Vector3(0f, 0f, 0f);
			Vector2 tex_pos = new Vector2(0f, 0f);
			foreach(CEdge e in triangle.edges)
			{
				position += e.from.position;
				tex_pos += e.from.texture_coordinates;
			}
			position = position * (1/3f);
			tex_pos = tex_pos * (1/3f);
			vertex = new CVertex(position, tex_pos);

			// Creates new triangles
			new_triangles = new CTriangle[3];
			new_triangles[0] = new CTriangle(triangle.edges[0].from,
				triangle.edges[1].from, vertex);
			new_triangles[1] = new CTriangle(triangle.edges[1].from,
				triangle.edges[2].from, vertex);
			new_triangles[2] = new CTriangle(triangle.edges[2].from,
				triangle.edges[0].from, vertex);

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
			figure.vertices.Add(vertex);
			figure.triangles.Add(new_triangles[0]);
			figure.triangles.Add(new_triangles[1]);
			figure.triangles.Add(new_triangles[2]);
		}

		public override void OnInvert()
		{
			// Remove vertex and triangles
			figure.vertices.Remove(vertex);
			figure.triangles.Remove(new_triangles[0]);
			figure.triangles.Remove(new_triangles[1]);
			figure.triangles.Remove(new_triangles[2]);

			// fixing neighbour connections
			triangle.neighbours[0].SwitchNeighbours(new_triangles[0], triangle);
			triangle.neighbours[1].SwitchNeighbours(new_triangles[1], triangle);
			triangle.neighbours[2].SwitchNeighbours(new_triangles[2], triangle);

			// Add old triangle
			figure.triangles.Add(triangle);
		}

	}

	
	/// <summary>
	/// Adds a figure.
	/// </summary>
	class AddFigure : Operation
	{
		private CModel model;
		private InitialFigure initial;
		private CFigure figure;

		public AddFigure(CModel model, InitialFigure initial)
		{
			this.model = model;
			this.initial = initial;
			this.figure = null;
		}

		public override void OnTransform()
		{
			figure = new CFigure(initial);
			model.figures.Add(figure);
			model.Selected = figure;
		}

		public override void OnInvert()
		{
			model.figures.Remove(figure);
			model.Selected = null;
		}

	}


	/// <summary>
	/// Changes the coordinates of the part
	/// </summary>
	public class MoveSelected : Operation
	{
		private Vector3 translation;
		private IPart part;

		public MoveSelected(IPart part, Matrix matrix)
		{
			this.part = part;
			this.translation = Vector3.TransformCoordinate(
				new Vector3(0f, 0f, 0f), matrix);
		}

		public override void OnTransform()
		{
			part.move(translation);
		}

		public override void OnInvert()
		{
			part.move(-translation);
		}

	}

	/// <summary>
	/// Changes the coordinates of the part
	/// </summary>
	public class ScaleSelected : Operation
	{
		private Matrix matrix;
		private Matrix inverse;
		private IPart part;

		public ScaleSelected(IPart part, Matrix matrix)
		{
			this.part = part;
			this.matrix = matrix;
			this.inverse = matrix;
			this.inverse.Invert();
		}

		public override void OnTransform()
		{
			part.scale(matrix);
		}

		public override void OnInvert()
		{
			part.scale(inverse);
		}

	}

	/// <summary>
	/// Changes the coordinates of the part
	/// </summary>
	public class MoveTexture : Operation
	{
		private Vector2 translation;
		private IPart part;

		public MoveTexture(IPart part, Vector2 translation)
		{
			this.translation = translation;
			this.part = part;
		}

		public override void OnTransform()
		{
			part.moveTexture(translation);
		}

		public override void OnInvert()
		{
			part.moveTexture(-translation);
		}

	}

	/// <summary>
	/// Splits an edge.
	/// Removes two triangles.
	/// Adds a vertex and four triangles.
	/// </summary>
	public class EdgeSplit : Operation
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
			foreach(CTriangle t in figure.triangles)
			{
				if(t.hasPart(edge.from) && t.hasPart(edge.to))
				{
					old_triangles[count++] = t;
					if(count == 2) break;
				}
			}

			ArrayList n = new ArrayList();
			foreach(CTriangle t in old_triangles[0].neighbours)
			{
				if(!(t.Equals(old_triangles[0]) || t.Equals(old_triangles[1])))
				{
					 if(n.IndexOf(t) == -1) n.Add(t);;
				}
			}
			foreach(CTriangle t in old_triangles[1].neighbours)
			{
				if(!(t.Equals(old_triangles[0]) || t.Equals(old_triangles[1])))
				{
					if(n.IndexOf(t) == -1) n.Add(t);;
				}
			}
			neighbours = (CTriangle[])n.ToArray(typeof(CTriangle));
		}

		public override void OnTransform()
		{
			// Removing old triangels from model
			figure.triangles.Remove(old_triangles[0]);
			figure.triangles.Remove(old_triangles[1]);

			// Creating new vertex
			Vector3 position = (old_vertices[0].position + 
				old_vertices[1].position) * 0.5f;
			Vector2 texture = (old_vertices[0].texture_coordinates + 
				old_vertices[1].texture_coordinates) * 0.5f;
			new_vertex = new CVertex(position, texture);

			// Creating new triangles
			CVertex[] quadrat = new CVertex[4];
			int count = 0; bool missed = true; int idx = -1;
			foreach(CEdge edge in old_triangles[0].edges)
			{
				quadrat[count++] = edge.from;
				if(missed)
				{
					if(edge.from.Equals(old_vertices[0]) ||
						edge.from.Equals(old_vertices[1]))
					{
						idx = count;
						quadrat[count++] = new_vertex;
						missed = false;
					}
				}
			}
			new_triangles[0] = new CTriangle(quadrat[(idx + 2) % 4],
				quadrat[(idx + 3) % 4], quadrat[(idx + 4) % 4]);
			new_triangles[1] = new CTriangle(quadrat[(idx + 2) % 4],
				quadrat[(idx + 4) % 4], quadrat[(idx + 5) % 4]);

			count = 0; missed = true; idx = -1;
			foreach(CEdge edge in old_triangles[1].edges)
			{
				quadrat[count++] = edge.from;
				if(missed)
				{
					if(edge.from.Equals(old_vertices[0]) ||
						edge.from.Equals(old_vertices[1]))
					{
						idx = count;
						quadrat[count++] = new_vertex;
						missed = false;
					}
				}
			}
			new_triangles[2] = new CTriangle(quadrat[(idx + 2) % 4],
				quadrat[(idx + 3) % 4], quadrat[(idx + 4) % 4]);
			new_triangles[3] = new CTriangle(quadrat[(idx + 2) % 4],
				quadrat[(idx + 4) % 4], quadrat[(idx + 5) % 4]);

			// Addig vertex and triangels to model
			figure.vertices.Add(new_vertex);
			figure.triangles.Add(new_triangles[0]);
			figure.triangles.Add(new_triangles[1]);
			figure.triangles.Add(new_triangles[2]);
			figure.triangles.Add(new_triangles[3]);

			// Creating neighbour connection
			figure.CalculateNeighbours(new_triangles, neighbours);
		}


		public override void OnInvert()
		{
			// Remove new trianlges and vertices
			figure.triangles.Remove(new_triangles[0]);
			figure.triangles.Remove(new_triangles[1]);
			figure.triangles.Remove(new_triangles[2]);
			figure.triangles.Remove(new_triangles[3]);
			figure.vertices.Remove(new_vertex);

			// Add old triangles
			figure.triangles.Add(old_triangles[0]);
			figure.triangles.Add(old_triangles[1]);

			// Fix neighbour connections
			figure.CalculateNeighbours(old_triangles, neighbours);
		}

	}


	/// <summary>
	/// Deletes a figure from model.
	/// </summary>
	public class DeleteFigure : Operation
	{
		private CModel model;
		private CFigure figure;

		public DeleteFigure(CModel model, CFigure figure)
		{
			this.model = model;
			this.figure = figure;
		}

		public override void OnTransform()
		{
			model.figures.Remove(figure);
		}

		public override void OnInvert()
		{
			model.figures.Add(figure);
		}


	}

	/// <summary>
	/// Adds mesh figures
	/// </summary>
	public class AddMeshOperation : Operation
	{
		private CModel model;
		private CFigure[] figures;

		public AddMeshOperation(CModel model, CFigure[] figures)
		{
			this.model = model;
			this.figures = figures;
		}

		public AddMeshOperation(CModel model, CFigure figure)
		{
			this.model = model;
			this.figures = new CFigure[]{figure};
		}

		public override void OnTransform()
		{
			foreach(CFigure figure in figures)
			{
				model.figures.Add(figure);
			}
		}

		public override void OnInvert()
		{
			foreach(CFigure figure in figures)
			{
				model.figures.Remove(figure);
			}
		}

	}

	/// <summary>
	/// Changes Material colors
	/// </summary>
	public class ChangeMaterial : Operation
	{
		private int ambient, diffuse, emissive;
		private int old_ambient, old_diffuse, old_emissive;
		private CFigure figure;

		public ChangeMaterial(CFigure figure, 
			int ambient, int diffuse, int emissive)
		{
			this.ambient = ambient;
			this.diffuse = diffuse;
			this.emissive = emissive;

			this.figure = figure;

			this.old_ambient = figure.ambient_color;
			this.old_diffuse = figure.diffuse_color;
			this.old_emissive = figure.emissive_color;
		}

		public override void OnTransform()
		{
			figure.ambient_color = ambient;
			figure.diffuse_color = diffuse;
			figure.emissive_color = emissive;
		}

		public override void OnInvert()
		{
			figure.ambient_color = old_ambient;
			figure.diffuse_color = old_diffuse;
			figure.emissive_color = old_emissive;
		}

	}
}

