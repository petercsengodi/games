package hu.csega.superstition.unported.game.elements;

import org.joml.Vector3f;

import hu.csega.superstition.game.Model;
import hu.csega.superstition.game.object.Clipable;

public class Weapon extends Clipable implements IGameElement {

	protected WeaponStatus status;
	protected WeaponType type;
	protected float Angle;
	protected float anim; // from 0f to 1f
	protected Model model;
	protected Engine engine;

	// Clipper properties
	protected float radius;

	protected MeshElement weapon;
	protected static float speed = 0.1f, limit = (float)(Math.PI * 2.0);
	protected static Vector3f vup = new Vector3f(0f, 1f, 0f);

	public WeaponStatus Status
	{
		get{ return status; }
		set{ status = value; }
	}

	public Weapon(Vector3f position, WeaponType type) {
		super(position, position, position);
		this.status = WeaponStatus.Ground;
		this.type = type;
		this.Angle = 0f;
		this.anim = 1f;
		this.radius = 0f;
	}


	public class WeaponData extends GameObjectData
	{
		public Vector3f position;
		public WeaponStatus status;
		public WeaponType type;
		public float Angle;
		public float anim;

		public WeaponData()
		{
			description = "Weapon Data";
		}

		public Object create()
		{
			return new Weapon(this);
		}
	}

	public Weapon(WeaponData data) {
		super(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, 0f));
		this.position = data.position;
		this.status = data.status;
		this.type = data.type;
		this.Angle = data.Angle;
		this.anim = data.anim;
		this.radius = 0f;
	}


	@Override
	public void Period()
	{
		switch(status)
		{
		case WeaponStatus.Ground:
			Angle += 0.005f;
			if(Angle > limit) Angle -= limit;
			break;

		default:
			if(anim < 1f)
			{
				if(anim > -1f) anim -= speed;
				else anim = 1f;
			}
			break;
		}
	} // End of function Period

	@Override
	public GameObjectData getData()
	{
		WeaponData data = new WeaponData();
		data.position = position;
		data.status = status;
		data.type = type;
		data.Angle = Angle;
		data.anim = anim;
		return data;
	}

	public void Build(Engine engine)
	{
		this.engine = engine;
		switch(type)
		{
		case WeaponType.Gun:
			weapon = (MeshElement)engine.GetMeshElement(
					"gun.x", EngineMeshFlags.Colored, Color.White);
			break;

		case WeaponType.Torch:
			weapon = (MeshElement)engine.GetMeshElement(
					"torch.x", EngineMeshFlags.Colored, Color.White);
			break;

		default:
			weapon = (MeshElement)engine.GetMeshElement(
					"sword.x", EngineMeshFlags.Colored, Color.White);
			break;
		}

		radius = weapon.Radius();
		corner1 = new Vector3f(-radius, -radius, -radius);
		corner2 = new Vector3f(radius, radius, radius);
	}

	@Override
	public void PreRender()
	{
		//		if(status != WeaponStatus.Grabbed)
		//		{
		//			weapon.Shadow = true;
		//		}
		//		else weapon.Shadow = false;
	}

	@Override
	public void PostRender()
	{
	}

	public void Render(Vector3f position, Vector3f orientation)
	{
		if(status == WeaponStatus.Grabbed)
		{
			Vector3f[] res = GetGrabVectors();
			Vector3f po = Vector3.Lerp(
					res[1], res[0], Math.Abs(anim));
			Vector3f or = Vector3.Lerp(
					res[3], res[2], Math.Abs(anim));
			Vector3f vp = Vector3.Lerp(
					res[5], res[4], Math.Abs(anim));
			Matrix w1 = Matrix.LookAtLH(position,
					orientation + position, vup);
			w1.Invert();
			Matrix w2 = Matrix.LookAtLH(po, or + po, vp);
			w2.Invert();
			weapon.Render(Matrix.Multiply(w2, w1));
		}
	}

	@Override
	public void Render()
	{
		if(status != WeaponStatus.Grabbed)
		{
			weapon.Render(position, 0f, Angle, 0f);
		}
	} // End of function Render


	public void squash(StaticVectorLibrary.Direction dir, Vector3f box1, Vector3f box2, Vector3f sqpoint)
	{

		if((dir == StaticVectorLibrary.Left) || (dir == StaticVectorLibrary.Right))
		{
			diff.X = 2 * (sqpoint.X - position.X) - diff.X;
			velocity.X = -0.4f * velocity.X;
			if(Math.Abs(velocity.X) < 0.01f) velocity.X = 0.00f;
		}

		if((dir == StaticVectorLibrary.Top) || (dir == StaticVectorLibrary.Bottom))
		{
			diff.Y = 2 * (sqpoint.Y - position.Y) - diff.Y;
			velocity.Y = -0.6f * velocity.Y;
			velocity.X = 0.6f * velocity.X;
			velocity.Z = 0.6f * velocity.Z;
			if(Math.Abs(velocity.Y) < 0.01f) velocity.Y = 0.00f;
		}

		if((dir == StaticVectorLibrary.Front) || (dir == StaticVectorLibrary.Back))
		{
			diff.Z = 2 * (sqpoint.Z - position.Z) - diff.Z;
			velocity.Z = -0.4f * velocity.Z;
			if(Math.Abs(velocity.Z) < 0.01f) velocity.Z = 0.00f;
		}

	}

	public void playerEffect(Object player)
	{
		PlayerObject p = player as PlayerObject;
		if(status == WeaponStatus.Ground)
		{
			status = WeaponStatus.Grabbed;
			p.Model.GEToRemove.Add(this);
			p._AddWeapon(this);

		}
	}

	protected Vector3f[] GetGrabVectors()
	{
		Vector3f[] ret = new Vector3f[6];

		switch(type)
		{
		case WeaponType.Gun:
			ret[0] = new Vector3f(0.15f, -0.15f, 0.2f);
			ret[1] = new Vector3f(0.15f, -0.15f, 0.1f);
			ret[2] = new Vector3f(0f, 0f, -1f);
			ret[3] = new Vector3f(0f, 0f, -1f);
			ret[4] = new Vector3f(0f, 1f, 0f);
			ret[5] = new Vector3f(0f, 1f, 0f);
			break;

		case WeaponType.Torch:
			ret[0] = new Vector3f(0.15f, -0.15f, 0.2f);
			ret[1] = new Vector3f(0.15f, -0.15f, 0.1f);
			ret[2] = new Vector3f(0f, 0f, -1f);
			ret[3] = new Vector3f(0f, 0f, -1f);
			ret[4] = new Vector3f(0f, 1f, 0f);
			ret[5] = new Vector3f(0f, 1f, 0f);
			break;

		default:
			ret[0] = new Vector3f(0.2f, -0.5f, 0f);
			ret[1] = new Vector3f(0.2f, -0.25f, 0.5f);
			ret[2] = new Vector3f(0f, 1.5f, 1f);
			ret[3] = new Vector3f(-2f, 0f, 2f);
			ret[4] = new Vector3f(0f, 1f, 0f);
			ret[5] = new Vector3f(1f, 1f, 0f);
			break;
		}


		return ret;
	}

	public void Shot(Vector3f position, Vector3f direction)
	{
		if((status == WeaponStatus.Grabbed) && (anim == 1f))
		{
			anim -= speed;

			switch(type)
			{
			case WeaponType.Gun:
				Bullet bullet = new Bullet(
						position + direction,
						Vector3.Normalize(direction) * 15f,
						model);
				bullet.Build(engine);
				model.GameElements.Add(bullet);
				break;

			default:

				break;
			}
		}
	}

	@Override
	public void setModel(Model model)
	{
		this.model = model;
	}

} // End of class Weapon
