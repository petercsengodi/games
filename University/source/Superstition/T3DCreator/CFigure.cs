using System;
using System.Drawing;
using System.Collections;

using Microsoft.DirectX;

namespace T3DCreator
{
	/// <summary>
	/// Summary description for CFigure.
	/// </summary>
	public class CFigure : IPart
	{
		public ArrayList vertices;
		public ArrayList triangles;
		public TexID texID;
		public int ambient_color;
		public int diffuse_color;
		public int emissive_color;

		public CFigure()
		{
			vertices = new ArrayList();
			triangles = new ArrayList();
			texID = null;
			
			ambient_color = Color.White.ToArgb();
			diffuse_color = Color.White.ToArgb();
			emissive_color = Color.Black.ToArgb();
		}

		public CFigure(InitialFigure figure)
		{
			vertices = new ArrayList();
			triangles = new ArrayList();
			texID = null;

			ambient_color = Color.White.ToArgb();
			diffuse_color = Color.White.ToArgb();
			emissive_color = Color.Black.ToArgb();

			switch(figure)
			{
				case InitialFigure.Cube:
					AddNewCube();
					break;

				default:
					AddNewTetra();
					break;
			}
		}

		public void AddNewTetra()
		{
			vertices.Add(new CVertex(new Vector3(-1f, 0f, 0f)));
			vertices.Add(new CVertex(new Vector3(1f, 0f, 0f)));
			vertices.Add(new CVertex(new Vector3(0f, 0f, 1f)));
			vertices.Add(new CVertex(new Vector3(0f, 1f, 0f)));

			CTriangle t1 = new CTriangle(
				vertices[0], vertices[1], vertices[2]);
			CTriangle t2 = new CTriangle(
				vertices[1], vertices[0], vertices[3]);
			CTriangle t3 = new CTriangle(
				vertices[2], vertices[1], vertices[3]);
			CTriangle t4 = new CTriangle(
				vertices[0], vertices[2], vertices[3]);

			t1.neighbours[0] = t2; t1.neighbours[1] = t3; t1.neighbours[2] = t4;
			t2.neighbours[0] = t1; t2.neighbours[1] = t4; t2.neighbours[2] = t3;
			t3.neighbours[0] = t1; t3.neighbours[1] = t2; t3.neighbours[2] = t4;
			t4.neighbours[0] = t1; t4.neighbours[1] = t3; t4.neighbours[2] = t2;

			triangles.Add(t1);
			triangles.Add(t2);
			triangles.Add(t3);
			triangles.Add(t4);

			(vertices[0] as CVertex).texture_coordinates = new Vector2(0f, 0f);
			(vertices[1] as CVertex).texture_coordinates = new Vector2(0f, 1f);
			(vertices[2] as CVertex).texture_coordinates = new Vector2(1f, 0f);
			(vertices[3] as CVertex).texture_coordinates = new Vector2(1f, 1f);
		}

		public void AddNewCube()
		{
			vertices.Add(new CVertex(new Vector3(0f, 0f, 0f)));
			vertices.Add(new CVertex(new Vector3(1f, 0f, 0f)));
			vertices.Add(new CVertex(new Vector3(0f, 1f, 0f)));
			vertices.Add(new CVertex(new Vector3(1f, 1f, 0f)));
			vertices.Add(new CVertex(new Vector3(0f, 0f, 1f)));
			vertices.Add(new CVertex(new Vector3(1f, 0f, 1f)));
			vertices.Add(new CVertex(new Vector3(0f, 1f, 1f)));
			vertices.Add(new CVertex(new Vector3(1f, 1f, 1f)));

			CTriangle t1 = new CTriangle(
				vertices[2], vertices[1], vertices[0]);
			CTriangle t2 = new CTriangle(
				vertices[2], vertices[3], vertices[1]);

			CTriangle t3 = new CTriangle(
				vertices[4], vertices[5], vertices[6]);
			CTriangle t4 = new CTriangle(
				vertices[7], vertices[6], vertices[5]);

			CTriangle t5 = new CTriangle(
				vertices[1], vertices[3], vertices[5]);
			CTriangle t6 = new CTriangle(
				vertices[3], vertices[7], vertices[5]);

			CTriangle t7 = new CTriangle(
				vertices[2], vertices[0], vertices[6]);
			CTriangle t8 = new CTriangle(
				vertices[0], vertices[4], vertices[6]);

			CTriangle t9 = new CTriangle(
				vertices[4], vertices[0], vertices[1]);
			CTriangle t10 = new CTriangle(
				vertices[4], vertices[1], vertices[5]);

			CTriangle t11 = new CTriangle(
				vertices[2], vertices[6], vertices[3]);
			CTriangle t12 = new CTriangle(
				vertices[6], vertices[7], vertices[3]);

			triangles.Add(t1);
			triangles.Add(t2);
			triangles.Add(t3);
			triangles.Add(t4);
			triangles.Add(t5);
			triangles.Add(t6);
			triangles.Add(t7);
			triangles.Add(t8);
			triangles.Add(t9);
			triangles.Add(t10);
			triangles.Add(t11);
			triangles.Add(t12);

			CalculateNeighbours();

			(vertices[0] as CVertex).texture_coordinates = new Vector2(0.333f, 0.666f);
			(vertices[1] as CVertex).texture_coordinates = new Vector2(0.666f, 0.666f);
			(vertices[2] as CVertex).texture_coordinates = new Vector2(0.333f, 0.333f);
			(vertices[3] as CVertex).texture_coordinates = new Vector2(0.666f, 0.333f);
			(vertices[4] as CVertex).texture_coordinates = new Vector2(0f, 1f);
			(vertices[5] as CVertex).texture_coordinates = new Vector2(1f, 1f);
			(vertices[6] as CVertex).texture_coordinates = new Vector2(0f, 0f);
			(vertices[7] as CVertex).texture_coordinates = new Vector2(1f, 0f);
		}

		public void CalculateNeighbours()
		{
			// for test // TODO: delete
			foreach(CTriangle t in triangles)
			{
				t.neighbours = new CTriangle[3];
			}

			CalculateNeighbours(triangles);
		}

		public void CalculateNeighbours(CTriangle[] old_triangles,
			CTriangle[] new_triangles)
		{
			ArrayList list = new ArrayList(old_triangles.Length +
				new_triangles.Length);
			foreach(CTriangle t in old_triangles) list.Add(t);
			foreach(CTriangle t in new_triangles) list.Add(t);
			CalculateNeighbours(list);
//			list.Clear();
		}

		protected void CalculateNeighbours(ArrayList list)
		{
			foreach(CTriangle t1 in list)
				foreach(CTriangle t2 in list)
				{
					if(t1.Equals(t2)) continue;
					for(int i = 0; i < 3; i++)
						for(int j = 0; j < 3; j++)
						{
							if(t1.edges[i].from.Equals(t2.edges[j].to) &&
								t1.edges[i].to.Equals(t2.edges[j].from))
							{
								t1.neighbours[i] = t2;
								t2.neighbours[j] = t1;
							}
						}
				}
		}

		public bool Verify()
		{
			bool ret = true;

			foreach(CTriangle t in triangles)
			{
				ret &= (t.neighbours[0] != null);
				ret &= (t.neighbours[1] != null);
				ret &= (t.neighbours[2] != null);
			}

			return ret;
		}

		public void AddNewSphere()
		{
			// TODO: Sphere
		}

		public override string ToString()
		{
			return "Figure";
		}

		#region IPart Members

		public void move(Vector3 direction)
		{
			foreach(CVertex v in this.vertices)
			{
				v.move(direction);
			}
		}

		public void moveTexture(Vector2 direction)
		{
			foreach(CVertex v in this.vertices)
			{
				v.moveTexture(direction);
			}
		}

		public bool hasPart(IPart part)
		{
			if(part.Equals(this)) return true;
			bool ret = false;
			foreach(CTriangle t in triangles)
			{
				ret |= t.hasPart(part);
			}
			return ret;
		}

		public Vector3 centerPoint()
		{
			Vector3 ret = new Vector3(0f, 0f, 0f);
			float n = 1f / (float)vertices.Count;
			foreach(CVertex vertex in vertices)
			{
				ret += vertex.position * n;
			}
			return ret;
		}

		public void scale(Matrix matrix)
		{
			foreach(CVertex vertex in vertices)
			{
				vertex.scale(matrix);
			}
		}

		#endregion

		public void zoom(float factor)
		{
			Vector3 average = new Vector3(0f, 0f, 0f);
			float num = 1f / vertices.Count;
			foreach(CVertex v in vertices)
			{
				average += v.position * num;
			}

			foreach(CVertex v in vertices)
			{
				v.position = ((v.position - average) * factor)
					+ average;
			}
		}

		public void SphereTexture()
		{
			Vector3 min, max, average;
			float radius = 0f;

			min = (vertices[0] as CVertex).position;
			max = (vertices[0] as CVertex).position;

			for(int i = 1; i < vertices.Count; i++)
			{
				CVertex v = vertices[i] as CVertex;
				min = Vector3.Minimize(min, v.position);
				max = Vector3.Maximize(max, v.position);
			}

			average = (min + max) * 0.5f;
			radius = Math.Max(average.X - min.X, Math.Max(
				average.Y - min.Y, average.Z - min.Z));

			foreach(CVertex v in vertices)
			{
				Vector3 tmp = v.position - average;
				tmp.Normalize();
				double ty = Math.Acos(tmp.Y) / Math.PI;
				tmp.Y = 0f;
				tmp.Normalize();
				double tx;

				if(tmp.Z >= 0f)
				{
					tx = Math.Acos(tmp.X) / Math.PI;
				} 
				else 
				{
					tx = 1.0 - Math.Acos(tmp.X) / Math.PI;
				}

                v.texture_coordinates = new Vector2((float)tx, (float)ty);
			}
		}

		public void RandomTexture()
		{
			Random random = new Random();
			foreach(CVertex v in vertices)
			{
				v.texture_coordinates.X = (float)random.NextDouble();
				v.texture_coordinates.Y = (float)random.NextDouble();
			}
		}

	} // End of class Figure

	public enum InitialFigure
	{
		Tetra, Cube, 
		Sphere, Cylinder, Box,
		TextMesh, Torus, Teapot
	}
}
