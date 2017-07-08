package hu.csega.superstition.unported.animatool;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import hu.csega.superstition.gamelib.animationdata.CConnection;
import hu.csega.superstition.gamelib.animationdata.CPartData;
import hu.csega.superstition.gamelib.model.SMeshRef;
import hu.csega.superstition.gamelib.model.mesh.SMesh;
import hu.csega.superstition.util.MeshLibrary;

public class CPart implements IPart {

	public CConnection[] connections;
	public Vector3f[] center_point;

	public SMeshRef meshRef;
	public SMesh mesh;
	public Matrix4f[] model_transform;

	public SMesh getMesh()
	{
		return mesh;
	}

	public void setMesh(SMesh mesh)
	{
		this.mesh = mesh;
	}

	public CPart(CModel model)
	{
		mesh = null;
		model_transform = new Matrix4f[model.max_scenes];
		center_point = new Vector3f[model.max_scenes];

		for(int i = 0; i < model.max_scenes; i++)
		{
			model_transform[i] = new Matrix4f(); // Identity matrix
			center_point[i] = new Vector3f(0f, 0f, 0f);
		}

		connections = new CConnection[0];
	}

	@SuppressWarnings("unused")
	private CPart()
	{
		mesh = null;
		meshRef = null;
		model_transform = new Matrix4f[0];
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
		// TODO csega uncomment, fix
		//		model_transform[scene] =
		//				model_transform[scene].mul(matrix);
	}

	@Override
	public void move(Vector3f direction, int scene)
	{
		center_point[scene] = center_point[scene].add(direction);
	}

	@Override
	public String toString()
	{
		if(mesh == null) return "<empty>";
		else return mesh.getName();
	}

	public CPart(CPartData data)
	{
		// TODO csega uncomment, fix
		this.meshRef = new SMeshRef(data.getMesh_file());
		this.model_transform = data.getModel_transform();
		this.center_point = data.center_point;
		this.connections = data.connections;
		if(this.mesh != null)
			this.mesh = MeshLibrary.instance().resolve(this.meshRef);
		else this.mesh = null;
	}

	public CPartData GetPartData()
	{
		CPartData ret = new CPartData();
		ret.mesh_file = this.meshRef.getName();
		ret.model_transform = this.model_transform;
		ret.center_point = this.center_point;
		ret.connections = this.connections;

		if(mesh == null)
		{
			return ret;
		}

		// counting bounding boxes and spheres
		// TODO csega uncomment, fix
		///		SMesh m = mesh.simpleMesh;
		//
		//		if(m.numberVertices == 0)
		//		{
		//			return ret;
		//		}

		ret.bounding_box1 = new Vector3f[model_transform.length];
		ret.bounding_box2 = new Vector3f[model_transform.length];
		ret.bounding_sphere_center = new Vector3f[model_transform.length];
		ret.bounding_sphere_radius = new float[model_transform.length];

		Vector3f temp = new Vector3f(0f, 0f, 0f),
				average = new Vector3f(0f, 0f, 0f);
		//		CustomVertex.PositionOnly[] vertices =
		//				m.lockVertexBuffer(CustomVertex.PositionOnly.class,
		//						LockFlags.ReadOnly, m.numberVertices);
		//		float f = 1f / m.numberVertices;
		//
		//		for(int i = 0; i < model_transform.length; i++)
		//		{
		//			vertices[0].Position.mul(model_transform[i], temp);
		//			ret.bounding_box1[i] = temp;
		//			ret.bounding_box2[i] = temp;
		//			ret.bounding_sphere_center[i] = temp;
		//			ret.bounding_sphere_radius[i] = 0f;
		//
		//			for(int v = 1; v < m.numberVertices; v++)
		//			{
		//				vertices[v].Position.mul(model_transform[i], temp);
		//				Vectors.minimize(ret.bounding_box1[i], temp, ret.bounding_box1[i]);
		//				Vectors.maximize(ret.bounding_box2[i], temp, ret.bounding_box2[i]);
		//				average = average.add(temp.mul(f));
		//			}
		//
		//			ret.bounding_sphere_radius[i] = 0f;
		//			for(int v = 0; v < m.numberVertices; v++)
		//			{
		//				temp.mul(model_transform[i], temp);
		//
		//				vertices[v].Position.mul(model_transform[i], temp);
		//				ret.bounding_sphere_radius[i] = Math.max(
		//						ret.bounding_sphere_radius[i],
		//						(temp.sub(average)).length());
		//			}
		//
		//			ret.bounding_sphere_center[i] = average;
		//		}
		//
		//		m.unlockVertexBuffer();

		return ret;
	}
}
