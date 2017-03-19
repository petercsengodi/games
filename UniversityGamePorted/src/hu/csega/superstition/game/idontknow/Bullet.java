package hu.csega.superstition.game.idontknow;

public class Bullet extends DynamicObject implements IGameElement {

	protected Model model;
	protected float radius;
	protected MeshElement bullet;
	protected static float speed = 0.1f, limit = (float)(Math.PI * 2.0);
	protected static Vector3 vup = new Vector3(0f, 1f, 0f);

	public Bullet(Vector3 position, Vector3 velocity, Model model) {
		super(position, position, position);
		this.radius = 0f;
		this.model = model;
		this.position = position;
		this.velocity = velocity;
	}


	public class BulletData extends GameObjectData
	{
		public Vector3 position;
		public Vector3 velocity;

		public BulletData()
		{
			description = "Bullet Data";
		}

		public Object create()
		{
			return new Bullet(this);
		}
	}

	public Bullet(BulletData data) {
		super(new Vector3(0f, 0f, 0f),
				new Vector3(0f, 0f, 0f), new Vector3(0f, 0f, 0f));
		this.position = data.position;
		this.velocity = velocity;
		this.radius = 0f;
	}

	public void SetModel(Model model)
	{
		this.model = model;
	}

	@Override
	public void Period()
	{
		float deltat = 0.04f;
		velocity.Y -= /*10f*/5f * deltat / 2f;
		base.Period();
	} // End of function Period

	@Override
	public GameObjectData getData()
	{
		BulletData data = new BulletData();
		data.position = position;
		data.velocity = velocity;
		return data;
	}

	public void Build(Engine engine)
	{
		bullet = (MeshElement)engine.GetMeshElement("bullet.x",
				EngineMeshFlags.Colored | EngineMeshFlags.NoShadow,
				Color.Yellow);
		radius = bullet.Radius();
		corner1 = new Vector3(-radius, -radius, -radius);
		corner2 = new Vector3(radius, radius, radius);
	}

	@Override
	public void Render()
	{
		bullet.Render(position, velocity);
	} // End of function Render

	public void Squash(StaticVectorLibrary.Direction dir, Vector3 box1, Vector3 box2, Vector3 sqpoint)
	{
		model.GEToRemove.Add(this);
	}

	public void PlayerEffect(Object player)
	{
	}

}