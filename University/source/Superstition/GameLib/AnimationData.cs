using System;
using System.Collections;

using Microsoft.DirectX;

namespace GameLib
{
	public class CModelData
	{
		public ArrayList parts;
		public int max_scenes;
		public ArrayList named_connections;
		public Vector3[] bounding_box1, bounding_box2;
		public Vector3[] bounding_sphere_center;
		public float[] bounding_sphere_radius;
	}

	public class CPartData
	{
		public CConnection[] connections;
		public Vector3[] center_point;
		public string mesh_file;
		public Matrix[] model_transform;
		public Vector3[] bounding_box1, bounding_box2;
		public Vector3[] bounding_sphere_center;
		public float[] bounding_sphere_radius;
	}

	public class CConnection
	{
		public CConnection(){}

		public CConnection(float x, float y, float z,
			int object_index, int connection_index)
		{
			this.point = new Vector3(x, y, z);
			this.object_index = object_index;
			this.connection_index = connection_index;
		}

		public Vector3 point;
		public int object_index;
		public int connection_index;
		public string name;

		public override string ToString()
		{
			string sx = point.X.ToString();
			string sy = point.Y.ToString();
			string sz = point.Z.ToString();

			int px = sx.IndexOf(',');
			int py = sy.IndexOf(',');
			int pz = sz.IndexOf(',');

			string ssx, ssy, ssz;
			if(px >= 0) ssx = sx.Substring(0, Math.Min(px + 4, sx.Length)); 
			else ssx = sx; 
			if(py >= 0) ssy = sy.Substring(0, Math.Min(py + 4, sy.Length)); 
			else ssy = sy; 
			if(pz >= 0) ssz = sz.Substring(0, Math.Min(pz + 4, sz.Length)); 
			else ssz = sz; 

			string var_string = "";
			if((name != null) && (name.Length > 0))
			{
				var_string = name + " ";
			}

			return var_string +
				"x:" + ssx + " y:" + ssy + " z:" + ssz + 
				" obj=" + object_index + 
				" idx=" + connection_index;
		}

	}

	public class CNamedConnection
	{
		public string name;
		public int object_index;
		public int connection_index;
		public Vector3 point;

		public CNamedConnection()
		{
			this.name = null;
			this.object_index = -1;
			this.connection_index = -1;
			this.point = new Vector3(0f, 0f, 0f);
		}

		public CNamedConnection(string name, Vector3 point,
			int object_index, int connection_index)
		{
			this.name = name;
			this.point = point;
			this.object_index = object_index;
			this.connection_index = connection_index;
		}
	}
}
