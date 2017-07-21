package hu.csega.superstition.unported.animatool;

import java.util.ArrayList;

public class CModel {

	public CModelData GetModelData()
	{
		CModelData ret = new CModelData();
		ret.max_scenes = this.max_scenes;
		ret.parts = new ArrayList<>();
		ret.named_connections = new ArrayList<>();

		CPart part;
		CConnection con;

		for(int object_index = 0; object_index < parts.size(); object_index++)
		{
			part = parts.get(object_index);
			ret.parts.add(part.GetPartData());

			for(int connection_index = 0; connection_index < part.connections.length; connection_index++)
			{
				con = part.connections[connection_index];
				if((con.name != null) && (con.name.length() > 0))
				{
					ret.named_connections.add(
							new CNamedConnection(con.name,
									con.point,
									object_index,
									connection_index));
				}
			}
		}

		int start = 0;
		// TODO csega: uncomment, fix
		//		while((ret.get(start).bounding_box1 == null)
		//				|| (start >= max_scenes)) start++;
		if(start >= max_scenes) return ret;

		ret.bounding_box1 = new Vector3f[max_scenes];
		ret.bounding_box2 = new Vector3f[max_scenes];
		ret.bounding_sphere_center = new Vector3f[max_scenes];
		ret.bounding_sphere_radius = new float[max_scenes];

		for(int i = 0; i < max_scenes; i++)
		{
			CPartData pdata = ret.parts.get(start);
			ret.bounding_box1[i] = pdata.bounding_box1[i];
			ret.bounding_box2[i] = pdata.bounding_box2[i];
			ret.bounding_sphere_center[i] = pdata.bounding_sphere_center[i];
			ret.bounding_sphere_radius[i] = pdata.bounding_sphere_radius[i];

			for(CPartData data : ret.parts)
			{
				if(data == pdata) continue;
				if(data.bounding_box1 == null) continue;

				// TODO csega: uncomment, fix
				//				CPart.minimize(ret.bounding_box1[i], data.bounding_box1[i], ret.bounding_box1[i]);
				//				CPart.maximize(ret.bounding_box2[i], data.bounding_box2[i], ret.bounding_box2[i]);

				Vector3f o1 = ret.bounding_sphere_center[i];
				float r1 = ret.bounding_sphere_radius[i];
				Vector3f o2 = data.bounding_sphere_center[i];
				float r2 = data.bounding_sphere_radius[i];
				Vector3f dir = o2.sub(o1);
				dir.normalize();
				Vector3f t1 = o1.sub(dir.mul(r1));
				Vector3f t2 = o2.add(dir.mul(r2));
				ret.bounding_sphere_center[i] = (t1.add(t2)).mul(0.5f);
				ret.bounding_sphere_radius[i] = (t2.sub(t1)).length() * 0.5f;
			}
		}

		return ret;
	}

	public void setModelData(CModelData data)
	{
		this.max_scenes = data.max_scenes;
		this.parts = new ArrayList<>();
		for(CPartData d : data.parts)
		{
			this.parts.add(new CPart(d));
		}
		this.scene = 0;
	}

	public void DeleteConnectedPart(int idx)
	{
		for(int i = 0; i < parts.size(); i++)
		{
			if(i == idx) continue;
			CPart part = parts.get(i);
			for(CConnection con : part.connections)
			{
				if(con.object_index == idx)
				{
					con.object_index = -1;
					con.connection_index = -1;
				}
				else if(con.object_index > idx)
				{
					con.object_index--;
				}
			}
		}
	}

	public void DeleteConnection(int obj, int idx)
	{
		for(int i = 0; i < parts.size(); i++)
		{
			if(i == obj) continue;
			CPart part = parts.get(i);
			for(CConnection con : part.connections)
			{
				if(con.object_index != obj) continue;
				if(con.connection_index == idx)
				{
					con.object_index = -1;
					con.connection_index = -1;
				}
				else if(con.connection_index > idx)
				{
					con.connection_index--;
				}
			}
		}
	}

	/// <summary>
	/// Preserves connection of a group identified by given part.
	/// </summary>
	/// <param name="moved"></param>
	public void PreserveConnections(CPart moved)
	{
		if(moved == null) return;

		// Searching for Root: root == not connected
		//    to anything
		CPart root = moved;
		int idx;
		boolean loop = true;
		while(loop)
		{
			loop = false;
			for(int i = 0; i < root.connections.length; i++)
			{
				idx = root.connections[i].object_index;
				if(idx >= 0)
				{
					root = parts.get(idx);
					loop = true;
					break;
				}
			}
		}

		// Calling Recursive algorithm from root
		RecursivePreserve(root);
	}

	public void PreserveConnections()
	{
		for(CPart root : parts) {
			int c = 0;
			for(CConnection con : root.connections)
			{
				if(con.object_index >= 0) c++;
			}

			if(c == 0) RecursivePreserve(root);
		}
	}

	private void RecursivePreserve(CPart root)
	{
		CPart p, tmp;
		Vector3f con1, con2, diff;

		// Searching for children
		for(int j = 0; j < parts.size(); j++)
		{
			p = parts.get(j);
			if(p == root) continue;

			for(CConnection con : p.connections)
			{
				if(con.object_index < 0) continue;
				tmp = parts.get(con.object_index);
				if(tmp == root)
				{
					/* Child found: p   Connection: con */

					// Calculate root's connection position
					// TODO csega: uncomment, fix
					//					con1 = root.connections[con.connection_index].point;
					//					con1 = Vector3.TransformCoordinate(con1,
					//							root.model_transform[scene] *
					//							Matrix4f..Translation(root.center_point[scene]));
					//
					//					// Calculate child's connection position
					//					con2 = con.point;
					//					con2 = Vector3.TransformCoordinate(con2,
					//							p.model_transform[scene] *
					//							Matrix.Translation(p.center_point[scene]));
					//
					//					// Calculating difference, moving
					//					diff = con1.sub(con2);
					//					p.move(diff, scene);
					//
					//					// Calling Recursive algorythm
					//					RecursivePreserve(p);
				}
			}
		}

	}

} // End of class CModel