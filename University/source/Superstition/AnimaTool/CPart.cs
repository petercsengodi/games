using System;

using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;

using GameLib;

namespace AnimaTool
{
	/// <summary>
	/// Summary description for CPart.
	/// </summary>
	public class CPart : IPart
	{
		public CConnection[] connections;
		public Vector3[] center_point;
		
		public string mesh_file;

		public MeshID mesh;
		public Matrix[] model_transform;

		public MeshID GetMesh()
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
			model_transform = new Matrix[model.max_scenes];
			center_point = new Vector3[model.max_scenes];

			for(int i = 0; i < model.max_scenes; i++)
			{
				model_transform[i] = Matrix.Identity;
				center_point[i] = new Vector3(0f, 0f, 0f);
			}

			connections = new CConnection[0];
		}

		private CPart()
		{
			mesh = null;
			model_transform = new Matrix[0];
			center_point = new Vector3[0];
			connections = new CConnection[0];
		}

		#region IPart Members

		public Vector3 centerPoint(int scene)
		{
			return center_point[scene];
		}

		public void scale(Matrix matrix, int scene)
		{
			model_transform[scene] = 
				model_transform[scene] * matrix;
		}

		public void move(Vector3 direction, int scene)
		{
			center_point[scene] += direction;
		}
		
		#endregion

		public override string ToString()
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
				this.mesh = MeshLibrary.Instance().LoadMesh(this.mesh_file);
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
			Mesh m = mesh.SimpleMesh;
			
			if(m.NumberVertices == 0)
			{
				return ret;
			}

			ret.bounding_box1 = new Vector3[model_transform.Length];
			ret.bounding_box2 = new Vector3[model_transform.Length];
			ret.bounding_sphere_center = new Vector3[model_transform.Length];
			ret.bounding_sphere_radius = new float[model_transform.Length];

			Vector3 temp = new Vector3(0f, 0f, 0f),
				average = new Vector3(0f, 0f, 0f);
			CustomVertex.PositionOnly[] vertices =
				(CustomVertex.PositionOnly[])
				m.LockVertexBuffer(typeof(CustomVertex.PositionOnly),
				LockFlags.ReadOnly, m.NumberVertices);
			float f = 1f / m.NumberVertices;
            
			for(int i = 0; i < model_transform.Length; i++)
			{
				temp = Vector3.TransformCoordinate(
					vertices[0].Position, model_transform[i]);
				ret.bounding_box1[i] = temp;
				ret.bounding_box2[i] = temp;
				ret.bounding_sphere_center[i] = temp;
				ret.bounding_sphere_radius[i] = 0f;

				for(int v = 1; v < m.NumberVertices; v++)
				{
					temp = Vector3.TransformCoordinate(
						vertices[v].Position, model_transform[i]);
					ret.bounding_box1[i] = Vector3.Minimize(
						ret.bounding_box1[i], temp);
					ret.bounding_box2[i] = Vector3.Maximize(
						ret.bounding_box2[i], temp);
					average += temp * f;
				}

				ret.bounding_sphere_radius[i] = 0f;
				for(int v = 0; v < m.NumberVertices; v++)
				{
					temp = Vector3.TransformCoordinate(
						vertices[v].Position, model_transform[i]);
					ret.bounding_sphere_radius[i] = Math.Max(
						ret.bounding_sphere_radius[i], 
						(temp - average).Length());
				}

				ret.bounding_sphere_center[i] = average;
			}
			
			m.UnlockVertexBuffer();

			return ret;
		}
	}

}
