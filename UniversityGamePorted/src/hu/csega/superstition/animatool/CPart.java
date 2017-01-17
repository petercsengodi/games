package hu.csega.superstition.animatool;

import org.joml.Matrix3f;
import org.joml.Vector3f;

import hu.csega.superstition.gamelib.animationdata.CConnection;
import hu.csega.superstition.gamelib.animationdata.CPartData;
import hu.csega.superstition.stubs.CModel;
import hu.csega.superstition.stubs.CustomVertex;
import hu.csega.superstition.stubs.LockFlags;
import hu.csega.superstition.stubs.Mesh;
import hu.csega.superstition.stubs.MeshID;
import hu.csega.superstition.stubs.MeshLibrary;

public class CPart implements IPart {

	public CConnection[] connections;
	public Vector3f[] center_point;

	public String mesh_file;

	public MeshID mesh;
	public Matrix3f[] model_transform;

	public MeshID getMesh()
	{
		return mesh;
	}

	public void SetMesh(MeshID mesh)
	{
		this.mesh = mesh;
	}

	public CPart(CModel model)
	{
		mesh = null;
		model_transform = new Matrix3f[model.max_scenes];
		center_point = new Vector3f[model.max_scenes];

		for(int i = 0; i < model.max_scenes; i++)
		{
			model_transform[i] = new Matrix3f(); // Identity matrix
			center_point[i] = new Vector3f(0f, 0f, 0f);
		}

		connections = new CConnection[0];
	}

	@SuppressWarnings("unused")
	private CPart()
	{
		mesh = null;
		model_transform = new Matrix3f[0];
		center_point = new Vector3f[0];
		connections = new CConnection[0];
	}

	@Override
	public Vector3f centerPoint(int scene)
	{
		return center_point[scene];
	}

	@Override
	public void scale(Matrix3f matrix, int scene)
	{
		model_transform[scene] =
				model_transform[scene].mul(matrix);
	}

	@Override
	public void move(Vector3f direction, int scene)
	{
		center_point[scene] = center_point[scene].add(direction);
	}

	@Override
	public String toString()
	{
		if(mesh_file == null) return "<empty>";
		else return mesh_file;
	}

	public CPart(CPartData data)
	{
		this.mesh_file = data.mesh_file;
		this.model_transform = data.model_transform;
		this.center_point = data.center_point;
		this.connections = data.connections;
		if(this.mesh_file != null)
			this.mesh = MeshLibrary.instance().loadMesh(this.mesh_file);
		else this.mesh = null;
	}

	public CPartData GetPartData()
	{
		CPartData ret = new CPartData();
		ret.mesh_file = this.mesh_file;
		ret.model_transform = this.model_transform;
		ret.center_point = this.center_point;
		ret.connections = this.connections;

		if(mesh == null)
		{
			return ret;
		}

		// counting bounding boxes and spheres
		Mesh m = mesh.simpleMesh;

		if(m.numberVertices == 0)
		{
			return ret;
		}

		ret.bounding_box1 = new Vector3f[model_transform.length];
		ret.bounding_box2 = new Vector3f[model_transform.length];
		ret.bounding_sphere_center = new Vector3f[model_transform.length];
		ret.bounding_sphere_radius = new float[model_transform.length];

		Vector3f temp = new Vector3f(0f, 0f, 0f),
				average = new Vector3f(0f, 0f, 0f);
		CustomVertex.PositionOnly[] vertices =
				m.lockVertexBuffer(CustomVertex.PositionOnly.class,
						LockFlags.ReadOnly, m.numberVertices);
		float f = 1f / m.numberVertices;

		for(int i = 0; i < model_transform.length; i++)
		{
			vertices[0].Position.mul(model_transform[i], temp);
			ret.bounding_box1[i] = temp;
			ret.bounding_box2[i] = temp;
			ret.bounding_sphere_center[i] = temp;
			ret.bounding_sphere_radius[i] = 0f;

			for(int v = 1; v < m.numberVertices; v++)
			{
				vertices[v].Position.mul(model_transform[i], temp);
				minimize(ret.bounding_box1[i], temp, ret.bounding_box1[i]);
				maximize(ret.bounding_box2[i], temp, ret.bounding_box2[i]);
				average = average.add(temp.mul(f));
			}

			ret.bounding_sphere_radius[i] = 0f;
			for(int v = 0; v < m.numberVertices; v++)
			{
				temp.mul(model_transform[i], temp);

				vertices[v].Position.mul(model_transform[i], temp);
				ret.bounding_sphere_radius[i] = Math.max(
						ret.bounding_sphere_radius[i],
						(temp.sub(average)).length());
			}

			ret.bounding_sphere_center[i] = average;
		}

		m.unlockVertexBuffer();

		return ret;
	}

	private static void minimize(Vector3f a, Vector3f b, Vector3f to) {
		to.x = Math.min(a.x, b.x);
		to.y = Math.min(a.y, b.y);
		to.z = Math.min(a.z, b.z);
	}

	private static void maximize(Vector3f a, Vector3f b, Vector3f to) {
		to.x = Math.max(a.x, b.x);
		to.y = Math.max(a.y, b.y);
		to.z = Math.max(a.z, b.z);
	}
}
