package hu.csega.superstition.game.object;

import org.joml.Vector3f;

import hu.csega.superstition.gamelib.network.GameObjectData;

class ThrowableTorch extends DynamicObject
{
	protected PointLight light;
	protected Element element;
	protected boolean stand;

	protected class TorchData extends GameObjectData {
		public Vector3f position, corner1, corner2, velocity, diff;
		public boolean stand, alive;

		public TorchData()
		{
			description = "Thorch";
		}

		public Object create()
		{
			return new ThrowableTorch(this);
		}
	}

	/// <summary>
	/// Torch stands or moving.
	/// </summary>
	public boolean Stand
	{
		get { return stand; }
		set { stand = value; }
	}

	public ThrowableTorch(Vector3f position, Vector3f speed) {
		super(position, new Vector3f(-0.125f, -0.125f, -0.125f), new Vector3f(0.125f, 0.125f, 0.125f));
		alive = true;
		this.velocity = speed;
	}

	public ThrowableTorch(GameObjectData data) {
		super(new Vector3f(), new Vector3f(), new Vector3f());
		TorchData d = data as TorchData;
		this.alive = d.alive;
		this.corner1 = d.corner1;
		this.corner2 = d.corner2;
		this.diff = d.diff;
		this.stand = d.stand;
		this.velocity = d.velocity;
		this.position = d.position;
	}

	/// <summary>
	/// Builds visuality.
	/// </summary>
	/// <param name="engine">Game Engine.</param>
	public void Build(Engine engine)
	{
		this.engine = engine;
		this.element = Library.Meshes().getMesh(@"..\meshes\torch.x", false, Color.White);
		light = engine.GetPointLight(100f /* 15f */, Color.FromArgb(255, 255, 0), position);
	}

	@Override
	public GameObjectData getData()
	{
		TorchData ret = new TorchData();
		ret.alive = this.alive;
		ret.corner1 = this.corner1;
		ret.corner2 = this.corner2;
		ret.diff = this.diff;
		ret.stand = this.stand;
		ret.velocity = this.velocity;
		ret.position = this.position;
		return ret;
	}

	public void PreRender()
	{
		light.Position = position;
		if(engine.IsLighted) light.Activate();
	}

	@Override
	public void Render()
	{
		element.Render(position);
	}

	public void PostRender()
	{
		if(engine.IsLighted) light.DeActivate();
	}

	public void Squash(StaticVectorLibrary.Direction dir, Vector3f box1, Vector3f box2, Vector3f sqpoint)
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

	@Override
	public void Period()
	{
		float deltat = 0.04f;
		if(!stand) velocity.Y -= 10f * deltat / 2f;
		super.Period();
	}
}