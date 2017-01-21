package hu.csega.superstition.game.animation;

import org.joml.Matrix3f;
import org.joml.Vector3f;

import hu.csega.superstition.game.Engine;
import hu.csega.superstition.game.Library;
import hu.csega.superstition.game.elements.MeshElement;
import hu.csega.superstition.gamelib.animationdata.CModelData;
import hu.csega.superstition.gamelib.animationdata.CNamedConnection;
import hu.csega.superstition.gamelib.animationdata.CPartData;

public class Animation {

	private int max_scenes;
	private AnimPart[] parts;
	private Engine engine;
	private CNamedConnection[] named_connections;

	public Vector3f[] bounding_box1, bounding_box2;
	public Vector3f[] bounding_sphere_center;
	public float[] bounding_sphere_radius;

	public int getMaxScenes() {
		return max_scenes;
	}

	public Animation(Engine engine)
	{
		this.engine = engine;
	}

	public Vector3f getBoundingBox1(int scene)
	{
		return bounding_box1[scene];
	}

	public Vector3f getBoundingBox2(int scene)
	{
		return bounding_box2[scene];
	}

	public Vector3f getBoundingSphereCenter(int scene)
	{
		return bounding_sphere_center[scene];
	}

	public float getBoundingSphereRadius(int scene)
	{
		return bounding_sphere_radius[scene];
	}

	public void Load(String file_name)
	{
		CModelData data = (CModelData)XmlRootHandler.Load(file_name);
		this.max_scenes = data.max_scenes;
		this.parts = new AnimPart[data.parts.size()];

		for(int i = 0; i < this.parts.length; i++)
		{
			CPartData d = data.parts.get(i);
			AnimPart thisPart = this.parts[i];
			thisPart.element = (MeshElement)Library.Meshes().getMesh(d.mesh_file);
			thisPart.worlds = d.model_transform;
			thisPart.center = d.center_point;

			thisPart.bounding_box1 = d.bounding_box1;
			thisPart.bounding_box2 = d.bounding_box2;
			thisPart.bounding_sphere_center = d.bounding_sphere_center;
			thisPart.bounding_sphere_radius = d.bounding_sphere_radius;
		}

		if(data.named_connections == null)
		{
			this.named_connections = new CNamedConnection[0];
		}
		else
		{
			this.named_connections = new CNamedConnection[data.named_connections.size()];
			for(int i = 0; i < this.named_connections.length; i++)
			{
				this.named_connections[i] = (CNamedConnection)data.named_connections[i];
			}
		}

		this.bounding_box1 = data.bounding_box1;
		this.bounding_box2 = data.bounding_box2;
		this.bounding_sphere_center = data.bounding_sphere_center;
		this.bounding_sphere_radius = data.bounding_sphere_radius;

	} // End of function Load

	public void Render(Vector3f position, Vector3f direction, Vector3f up, int scene)
	{
		Matrix3f m = Matrix.LookAtLH(position,
				position + direction, up);
		m.Invert();
		Draw(m, scene);
	}

	public void Draw(Matrix3f common_world, int scene)
	{
		if(scene < 0 || scene >= max_scenes) return;
		Matrix3f actual;
		Matrix3f old = engine.Device.Transform.World;
		for(AnimPart part : parts)
		{
			actual = part.worlds[scene] *
					Matrix.Translation(part.center[scene]) *
					common_world;
			part.element.Render(actual);
		}

		engine.Device.Transform.World = old;
	}

	public boolean shot(Matrix3f common_world, int scene, Vector3f start_point, Vector3f end_point, boolean infinity)
	{
		Matrix3f world;
		Vector3f start, end;
		boolean ret = false;
		for(int i = 0; i < parts.length; i++)
		{
			world = parts[i].worlds[scene] *
					Matrix3f.Translation(parts[i].center[scene]) *
					common_world;
			world.Invert();
			start = Vector3f.TransformCoordinate(
					start_point, world);
			end = Vector3f.TransformCoordinate(
					end_point, world);
			ret |= parts[i].element.Shot(start, end, infinity);
		}
		return ret;
	}

	public NamedConnectionResult getNamedConnection(String name, Matrix3f common_world, int scene)
	{
		NamedConnectionResult ret = null;
		if((name == null) || (name.length() == 0)) return ret;

		for(CNamedConnection nc : this.named_connections)
		{
			if(nc.name.equals(name))
			{
				ret = new NamedConnectionResult();
				AnimPart part = parts[nc.object_index];
				Matrix3f actual = part.worlds[scene] *
						Matrix3f.Translation(part.center[scene]) *
						common_world;
				ret.position = Vector3f.TransformCoordinate(
						nc.point, actual);
				return ret;
			}
		}

		return ret;
	}

	public static Matrix3f LookAt(Vector3f position, Vector3f direction, Vector3f up)
	{
		Matrix3f ret = Matrix.LookAtLH(position, direction + position, up);
		ret.Invert();
		return ret;
	}

} // End of class Animation