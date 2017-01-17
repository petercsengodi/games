using System;
using System.Collections;

using Microsoft.DirectX;

using GameLib;
using AnimaToolWindows;

namespace AnimaTool
{
	/// <summary>
	/// Summary description for CModel.
	/// </summary>
	public class CModel
	{
		protected ArrayList views;
		protected object selected;
		protected bool snap_to_grid;

		public ArrayList parts;
		public int scene;
		public int max_scenes;

		public double grid_from = -1, grid_to = 1, 
			grid_step = 0.1, grid_error = 0.001;

		public bool SnapToGrid
		{
			get{ return snap_to_grid; }
		}

		public void SetSnapToGrid(bool snap_to_grid)
		{
			this.snap_to_grid = snap_to_grid;
		}

		public object Selected 
		{
			get { return selected; }
			set	{ 
				selected = value;
				UpdateViews(Updates.Selection);
			}
		}

		public void ClearSelection()
		{
			selected = "";
		}

		public CModel()
		{
			views = new ArrayList();
			selected = null;
			snap_to_grid = true;
			parts = new ArrayList();
			scene = 0;
			max_scenes = 1;
		}

		public void RegisterView(CView view)
		{
			views.Add(view);
			view.SetData(this);
			view.Invalidate();
		}

		public void RemoveView(CView view)
		{
			views.Remove(view);
		}

		public void UpdateViews()
		{
			UpdateViews(Updates.Full);
		}

		public void UpdateViews(Updates update)
		{
			foreach(CView view in views)
			{
				view.UpdateView(update);
			}
		}

		public CModelData GetModelData()
		{
			CModelData ret = new CModelData();
			ret.max_scenes = this.max_scenes;
			ret.parts = new ArrayList();
			ret.named_connections = new ArrayList();

			CPart part;
			CConnection con;

			for(int object_index = 0; object_index < parts.Count; object_index++)
			{
				part = parts[object_index] as CPart;
				ret.parts.Add(part.GetPartData());

				for(int connection_index = 0; connection_index < part.connections.Length; connection_index++)
				{
					con = part.connections[connection_index];
					if((con.name != null) && (con.name.Length > 0))
					{
						ret.named_connections.Add(
							new CNamedConnection(con.name,
							con.point,
							object_index,
							connection_index));
					}
				}
			}

			int start = 0;
			while(((ret.parts[start] as CPartData).bounding_box1 == null)
				|| (start >= max_scenes)) start++;
			if(start >= max_scenes) return ret;

			ret.bounding_box1 = new Vector3[max_scenes];
			ret.bounding_box2 = new Vector3[max_scenes];
			ret.bounding_sphere_center = new Vector3[max_scenes];
			ret.bounding_sphere_radius = new float[max_scenes];

			for(int i = 0; i < max_scenes; i++)
			{
				CPartData pdata = ret.parts[start] as CPartData;
				ret.bounding_box1[i] = pdata.bounding_box1[i];
				ret.bounding_box2[i] = pdata.bounding_box2[i];
				ret.bounding_sphere_center[i] = pdata.bounding_sphere_center[i];
				ret.bounding_sphere_radius[i] = pdata.bounding_sphere_radius[i];

				foreach(CPartData data in ret.parts)
				{
					if(data == pdata) continue;
					if(data.bounding_box1 == null) continue;

					ret.bounding_box1[i] = Vector3.Minimize(
						ret.bounding_box1[i], data.bounding_box1[i]);
					ret.bounding_box2[i] = Vector3.Maximize(
						ret.bounding_box2[i], data.bounding_box2[i]);

					Vector3 o1 = ret.bounding_sphere_center[i];
					float r1 = ret.bounding_sphere_radius[i];
					Vector3 o2 = data.bounding_sphere_center[i];
					float r2 = data.bounding_sphere_radius[i];
					Vector3 dir = o2-o1;
					dir.Normalize();
					Vector3 t1 = o1 - dir * r1;
					Vector3 t2 = o2 + dir * r2;
					ret.bounding_sphere_center[i] = (t1 + t2) * 0.5f;
					ret.bounding_sphere_radius[i] = (t2 - t1).Length() * 0.5f;
				}
			}

			return ret;
		}

		public void SetModelData(CModelData data)
		{
			this.max_scenes = data.max_scenes;
			this.parts = new ArrayList();
			foreach(CPartData d in data.parts)
			{
				this.parts.Add(new CPart(d));
			}
			this.scene = 0;
		}

		public void DeleteConnectedPart(int idx)
		{
			for(int i = 0; i < parts.Count; i++)
			{
				if(i == idx) continue;
				CPart part = parts[i] as CPart;
				foreach(CConnection con in part.connections)
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
			for(int i = 0; i < parts.Count; i++)
			{
				if(i == obj) continue;
				CPart part = parts[i] as CPart;
				foreach(CConnection con in part.connections)
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
			bool loop = true;
			while(loop)
			{
				loop = false;
				for(int i = 0; i < root.connections.Length; i++)
				{
					idx = root.connections[i].object_index;
					if(idx >= 0)
					{
						root = parts[idx] as CPart;
						loop = true;
						break;
					}
				}
			}

			// Calling Recursive algorithm from root
			RecursivePreserve(root);
		}

		/// <summary>
		/// Preserves connections with searching all roots.
		/// </summary>
		public void PreserveConnections()
		{
			foreach(CPart root in parts)
			{
				int c = 0;
				foreach(CConnection con in root.connections)
				{
					if(con.object_index >= 0) c++;
				}

				if(c == 0) RecursivePreserve(root);
			}
		}

		private void RecursivePreserve(CPart root)
		{
			CPart p, tmp;
			Vector3 con1, con2, diff;

			// Searching for children
			for(int j = 0; j < parts.Count; j++)
			{
				p = parts[j] as CPart;
				if(p == root) continue;

				foreach(CConnection con in p.connections)
				{
					if(con.object_index < 0) continue;
					tmp = parts[con.object_index] as CPart;
					if(tmp == root)
					{
						/* Child found: p   Connection: con */ 

						// Calculate root's connection position
						con1 = root.connections[con.connection_index].point;
						con1 = Vector3.TransformCoordinate(con1,
							root.model_transform[scene] *
							Matrix.Translation(root.center_point[scene]));

						// Calculate child's connection position
						con2 = con.point;
						con2 = Vector3.TransformCoordinate(con2,
							p.model_transform[scene] *
							Matrix.Translation(p.center_point[scene]));

						// Calculating difference, moving
						diff = con1 - con2;
						p.move(diff, scene);

						// Calling Recursive algorythm
						RecursivePreserve(p);
					}
				}
			}

		}

	} // End of class CModel

	public enum Updates
	{
		Full = 0,
		Move,
		Selection
	}
}
