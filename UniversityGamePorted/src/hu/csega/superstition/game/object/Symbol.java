package hu.csega.superstition.game.object;

class Symbol implements IPeriod, IGameObject
{
	protected Vector3 position;
	protected Element element;
	protected float Angle;
	protected String mesh;
	protected Color sparkling;
	protected float range;
	protected Light light;

	protected static float speed = 0.01f, limit = (float)(Math.PI * 2.0);

	public Symbol(Vector3 position, String mesh, Color sparkling, float range)
	{
		this.position = position;
		this.mesh = mesh;
		this.sparkling = sparkling;
	}


	protected class SymbolData extends GameObjectData
	{
		public float angle;
		public String mesh;
		public Vector3 position;
		public Color sparkling;
		public float range;

		public SymbolData()
		{
			description = "Symbol";
		}

		public Object create()
		{
			return new Symbol(this);
		}

	}

	public Symbol(GameObjectData data)
	{
		SymbolData d = data as SymbolData;
		this.Angle = d.angle;
		this.mesh = d.mesh;
		this.position = d.position;
		this.sparkling = d.sparkling;
		this.range = d.range;
	}

	public virtual GameObjectData getData()
	{
		SymbolData ret = new SymbolData();
		ret.angle = this.Angle;
		ret.mesh = this.mesh;
		ret.position = this.position;
		ret.sparkling = this.sparkling;
		ret.range = this.range;
		return ret;
	}

	@Override
	public void Period()
	{
		Angle += 0.005f;
		if(Angle > limit) Angle -= limit;
	}

	public virtual void Build(Engine engine)
	{
		element = Library.Meshes().getMesh(mesh, sparkling);
		light = engine.GetPointLight(range, sparkling, position);
	}

	@Override
	public void Render()
	{
		element.Render(position, 0f, Angle, 0f);
	}

	public void PreRender()
	{
		light.Activate();
	}

	public void PostRender()
	{
		light.DeActivate();
	}

}