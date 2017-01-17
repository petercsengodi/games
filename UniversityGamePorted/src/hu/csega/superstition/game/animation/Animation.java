package hu.csega.superstition.game.animation;

public class Animation
{
	private int max_scenes;
	private AnimPart[] parts;
	private Engine engine;
	private CNamedConnection[] named_connections;

	public Vector3[] bounding_box1, bounding_box2;
	public Vector3[] bounding_sphere_center;
	public float[] bounding_sphere_radius;

	public int MaxScenes
	{
		get { return max_scenes; }
	}

	public Animation(Engine engine)
	{
		this.engine = engine;
	}

	public Vector3 GetBoundingBox1(int scene)
	{
		return bounding_box1[scene];
	}

	public Vector3 GetBoundingBox2(int scene)
	{
		return bounding_box2[scene];
	}

	public Vector3 GetBoundingSphereCenter(int scene)
	{
		return bounding_sphere_center[scene];
	}

	public float GetBoundingSphereRadius(int scene)
	{
		return bounding_sphere_radius[scene];
	}

	public void Load(string file_name)
	{
		CModelData data = (CModelData)XmlHandler.Load(file_name);
		this.max_scenes = data.max_scenes;
		this.parts = new AnimPart[data.parts.Count];

		for(int i = 0; i < this.parts.Length; i++)
		{
			CPartData d = (CPartData)data.parts[i];
			this.parts[i].element = (MeshElement)
				Library.Meshes().getMesh(d.mesh_file);
			this.parts[i].worlds = d.model_transform;
			this.parts[i].center = d.center_point;

			this.parts[i].bounding_box1 = d.bounding_box1;
			this.parts[i].bounding_box2 = d.bounding_box2;
			this.parts[i].bounding_sphere_center = d.bounding_sphere_center;
			this.parts[i].bounding_sphere_radius = d.bounding_sphere_radius;
		}

		if(data.named_connections == null)
		{
			this.named_connections = new CNamedConnection[0];
		}
		else
		{
			this.named_connections = new CNamedConnection[data.named_connections.Count];
			for(int i = 0; i < this.named_connections.Length; i++)
			{
				this.named_connections[i] = data.named_connections[i] as CNamedConnection;
			}
		}

		this.bounding_box1 = data.bounding_box1;
		this.bounding_box2 = data.bounding_box2;
		this.bounding_sphere_center = data.bounding_sphere_center;
		this.bounding_sphere_radius = data.bounding_sphere_radius;

	} // End of function Load

	public void Render(Vector3 position, Vector3 direction, Vector3 up, int scene)
	{
		Matrix m = Matrix.LookAtLH(position,
			position + direction, up);
		m.Invert();
		Draw(m, scene);
	}

	public void Draw(Matrix common_world, int scene)
	{
		if(scene < 0 || scene >= max_scenes) return;
		Matrix actual;
		Matrix old = engine.Device.Transform.World;
		foreach(AnimPart part in parts)
		{
			actual = part.worlds[scene] *
				Matrix.Translation(part.center[scene]) *
				common_world;
			part.element.Render(actual);
		}

		engine.Device.Transform.World = old;
	}

	public bool Shot(Matrix common_world, int scene,
		Vector3 start_point, Vector3 end_point, bool infinity)
	{
		Matrix world;
		Vector3 start, end;
		bool ret = false;
		for(int i = 0; i < parts.Length; i++)
		{
			world = parts[i].worlds[scene] *
				Matrix.Translation(parts[i].center[scene]) *
				common_world;
			world.Invert();
			start = Vector3.TransformCoordinate(
				start_point, world);
			end = Vector3.TransformCoordinate(
				end_point, world);
			ret |= parts[i].element.Shot(start, end, infinity);
		}
		return ret;
	}

	public NamedConnectionResult GetNamedConnection(
		string name, Matrix common_world, int scene)
	{
		NamedConnectionResult ret = null;
		if((name == null) || (name.Length == 0)) return ret;

		foreach(CNamedConnection nc in this.named_connections)
		{
			if(nc.name.Equals(name))
			{
				ret = new NamedConnectionResult();
				AnimPart part = parts[nc.object_index];
				Matrix actual = part.worlds[scene] *
					Matrix.Translation(part.center[scene]) *
					common_world;
				ret.position = Vector3.TransformCoordinate(
					nc.point, actual);
				return ret;
			}
		}

		return ret;
	}

	public static Matrix LookAt(Vector3 position, Vector3 direction, Vector3 up)
	{
		Matrix ret = Matrix.LookAtLH(position, direction + position, up);
		ret.Invert();
		return ret;
	}

} // End of class Animation