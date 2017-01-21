package hu.csega.superstition.t3dcreator;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import hu.csega.superstition.t3dcreator.texture.TexID;
import hu.csega.superstition.util.StaticRandomLibrary;
import hu.csega.superstition.util.Vectors;

public class CFigure implements IModelPart {

	public List<CVertex> vertices;
	public List<CTriangle> triangles;
	public TexID texID;

	public Color ambient_color;
	public Color diffuse_color;
	public Color emissive_color;

	public CFigure()
	{
		vertices = new ArrayList<>();
		triangles = new ArrayList<>();
		texID = null;

		ambient_color = Color.white;
		diffuse_color = Color.white;
		emissive_color = Color.black;
	}

	public CFigure(InitialFigure figure) {
		vertices = new ArrayList<>();
		triangles = new ArrayList<>();
		texID = null;

		ambient_color = Color.white;
		diffuse_color = Color.white;
		emissive_color = Color.black;

		switch(figure)
		{
			case Cube:
				AddNewCube();
				break;

			default:
				AddNewTetra();
				break;
		}
	}

	public void AddNewTetra()
	{
		vertices.add(new CVertex(new Vector3f(-1f, 0f, 0f)));
		vertices.add(new CVertex(new Vector3f(1f, 0f, 0f)));
		vertices.add(new CVertex(new Vector3f(0f, 0f, 1f)));
		vertices.add(new CVertex(new Vector3f(0f, 1f, 0f)));

		CTriangle t1 = new CTriangle(
			vertices.get(0), vertices.get(1), vertices.get(2));
		CTriangle t2 = new CTriangle(
			vertices.get(1), vertices.get(0), vertices.get(3));
		CTriangle t3 = new CTriangle(
			vertices.get(2), vertices.get(1), vertices.get(3));
		CTriangle t4 = new CTriangle(
			vertices.get(0), vertices.get(2), vertices.get(3));

		t1.neighbours[0] = t2; t1.neighbours[1] = t3; t1.neighbours[2] = t4;
		t2.neighbours[0] = t1; t2.neighbours[1] = t4; t2.neighbours[2] = t3;
		t3.neighbours[0] = t1; t3.neighbours[1] = t2; t3.neighbours[2] = t4;
		t4.neighbours[0] = t1; t4.neighbours[1] = t3; t4.neighbours[2] = t2;

		triangles.add(t1);
		triangles.add(t2);
		triangles.add(t3);
		triangles.add(t4);

		vertices.get(0).texture_coordinates = new Vector2f(0f, 0f);
		vertices.get(1).texture_coordinates = new Vector2f(0f, 1f);
		vertices.get(2).texture_coordinates = new Vector2f(1f, 0f);
		vertices.get(3).texture_coordinates = new Vector2f(1f, 1f);
	}

	public void AddNewCube()
	{
		vertices.add(new CVertex(new Vector3f(0f, 0f, 0f)));
		vertices.add(new CVertex(new Vector3f(1f, 0f, 0f)));
		vertices.add(new CVertex(new Vector3f(0f, 1f, 0f)));
		vertices.add(new CVertex(new Vector3f(1f, 1f, 0f)));
		vertices.add(new CVertex(new Vector3f(0f, 0f, 1f)));
		vertices.add(new CVertex(new Vector3f(1f, 0f, 1f)));
		vertices.add(new CVertex(new Vector3f(0f, 1f, 1f)));
		vertices.add(new CVertex(new Vector3f(1f, 1f, 1f)));

		CTriangle t1 = new CTriangle(
			vertices.get(2), vertices.get(1), vertices.get(0));
		CTriangle t2 = new CTriangle(
			vertices.get(2), vertices.get(3), vertices.get(1));

		CTriangle t3 = new CTriangle(
			vertices.get(4), vertices.get(5), vertices.get(6));
		CTriangle t4 = new CTriangle(
			vertices.get(7), vertices.get(6), vertices.get(5));

		CTriangle t5 = new CTriangle(
			vertices.get(1), vertices.get(3), vertices.get(5));
		CTriangle t6 = new CTriangle(
			vertices.get(3), vertices.get(7), vertices.get(5));

		CTriangle t7 = new CTriangle(
			vertices.get(2), vertices.get(0), vertices.get(6));
		CTriangle t8 = new CTriangle(
			vertices.get(0), vertices.get(4), vertices.get(6));

		CTriangle t9 = new CTriangle(
			vertices.get(4), vertices.get(0), vertices.get(1));
		CTriangle t10 = new CTriangle(
			vertices.get(4), vertices.get(1), vertices.get(5));

		CTriangle t11 = new CTriangle(
			vertices.get(2), vertices.get(6), vertices.get(3));
		CTriangle t12 = new CTriangle(
			vertices.get(6), vertices.get(7), vertices.get(3));

		triangles.add(t1);
		triangles.add(t2);
		triangles.add(t3);
		triangles.add(t4);
		triangles.add(t5);
		triangles.add(t6);
		triangles.add(t7);
		triangles.add(t8);
		triangles.add(t9);
		triangles.add(t10);
		triangles.add(t11);
		triangles.add(t12);

		CalculateNeighbours();

		vertices.get(0).texture_coordinates = new Vector2f(0.333f, 0.666f);
		vertices.get(1).texture_coordinates = new Vector2f(0.666f, 0.666f);
		vertices.get(2).texture_coordinates = new Vector2f(0.333f, 0.333f);
		vertices.get(3).texture_coordinates = new Vector2f(0.666f, 0.333f);
		vertices.get(4).texture_coordinates = new Vector2f(0f, 1f);
		vertices.get(5).texture_coordinates = new Vector2f(1f, 1f);
		vertices.get(6).texture_coordinates = new Vector2f(0f, 0f);
		vertices.get(7).texture_coordinates = new Vector2f(1f, 0f);
	}

	public void CalculateNeighbours()
	{
		// for test // TODO: delete
		for(CTriangle t : triangles) {
			t.neighbours = new CTriangle[3];
		}

		CalculateNeighbours(triangles);
	}

	public void CalculateNeighbours(CTriangle[] old_triangles, CTriangle[] new_triangles) {

		List<CTriangle> list = new ArrayList<>(old_triangles.length + new_triangles.length);

		for(CTriangle t : old_triangles)
			list.add(t);

		for(CTriangle t : new_triangles)
			list.add(t);

		CalculateNeighbours(list);
//		list.Clear();
	}

	protected void CalculateNeighbours(List<CTriangle> list)
	{
		for(CTriangle t1 : list)
			for(CTriangle t2 : list)
			{
				if(t1.equals(t2))
					continue;

				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 3; j++)
					{
						if(t1.edges[i].from.equals(t2.edges[j].to) &&
							t1.edges[i].to.equals(t2.edges[j].from))
						{
							t1.neighbours[i] = t2;
							t2.neighbours[j] = t1;
						}
					}
			}
	}

	public boolean verify() {

		boolean ret = true;

		for(CTriangle t : triangles)
		{
			ret &= (t.neighbours[0] != null);
			ret &= (t.neighbours[1] != null);
			ret &= (t.neighbours[2] != null);
		}

		return ret;
	}

	public void AddNewSphere() {
		// TODO: Sphere
	}

	public String toString() {
		return "Figure";
	}

	public void move(Vector3f direction) {
		for(CVertex v : this.vertices) {
			v.move(direction);
		}
	}

	public void moveTexture(Vector2f direction) {
		for(CVertex v : this.vertices) {
			v.moveTexture(direction);
		}
	}

	public boolean hasPart(IModelPart part)
	{
		if(part.equals(this))
			return true;

		boolean ret = false;
		for(CTriangle t : triangles)
		{
			ret |= t.hasPart(part);
		}
		return ret;
	}

	public Vector3f centerPoint()
	{
		// TODO allocationless
		Vector3f ret = new Vector3f(0f, 0f, 0f);
		Vector3f tmp = new Vector3f(0f, 0f, 0f);
		float n = 1f / (float)vertices.size();
		for(CVertex vertex : vertices) {
			vertex.position.mul(n, tmp);
			ret.add(tmp, ret);
		}
		return ret;
	}

	public void scale(Matrix3f matrix)
	{
		for(CVertex vertex : vertices) {
			vertex.scale(matrix);
		}
	}

	public void zoom(float factor)
	{
		// TODO allocationless
		Vector3f average = new Vector3f(0f, 0f, 0f);
		Vector3f tmp = new Vector3f(0f, 0f, 0f);

		float num = 1f / vertices.size();
		for(CVertex v : vertices)
		{
			v.position.mul(num, tmp);
			average.add(tmp, average);
		}

		for(CVertex v : vertices)
		{
			v.position.sub(average, tmp);
			tmp.mul(factor, tmp);
			tmp.add(average, v.position);
		}
	}

	public void sphereTexture()
	{
		Vector3f min = new Vector3f();
		Vector3f max = new Vector3f();
		Vector3f average = new Vector3f();
		float radius = 0f;

		min.set(vertices.get(0).position);
		max.set(vertices.get(0).position);

		for(int i = 1; i < vertices.size(); i++)
		{
			CVertex v = vertices.get(i);
			Vectors.minimize(min, v.position, min);
			Vectors.maximize(max, v.position, max);
		}

		min.add(max, average);
		average.mul(0.5f, average);

		radius = Math.max(average.x - min.x, Math.max(
			average.y - min.y, average.z - min.z));

		Vector3f tmp = new Vector3f();

		for(CVertex v : vertices) {
			v.position.sub(average, tmp);
			tmp.normalize(tmp);
			double ty = Math.acos(tmp.y) / Math.PI;
			tmp.y = 0f;
			tmp.normalize(tmp);
			double tx;

			if(tmp.z >= 0f) {
				tx = Math.acos(tmp.x) / Math.PI;
			} else {
				tx = 1.0 - Math.acos(tmp.x) / Math.PI;
			}

            v.texture_coordinates = new Vector2f((float)tx, (float)ty);
		}
	}

	public void randomTexture()
	{
		for(CVertex v : vertices)
		{
			v.texture_coordinates.x = StaticRandomLibrary.floatValue();
			v.texture_coordinates.y = StaticRandomLibrary.floatValue();
		}
	}

} // End of class Figure