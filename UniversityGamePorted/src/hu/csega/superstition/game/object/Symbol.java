package hu.csega.superstition.game.object;

class Symbol : IPeriod, IGameObject
{
	protected Vector3 position;
	protected Element element;
	protected float Angle;
	protected string mesh;
	protected Color sparkling;
	protected float range;
	protected Light light;

	protected static float speed = 0.01f, limit = (float)(Math.PI * 2.0);

	public Symbol(Vector3 position, string mesh, Color sparkling, float range)
	{
		this.position = position;
		this.mesh = mesh;
		this.sparkling = sparkling;
	}

	/// <summary>
	/// Serializable data class for symbol.
	/// </summary>
	[Serializable]
	protected class SymbolData : GameObjectData
	{
		public float angle;
		public string mesh;
		public Vector3 position;
		public Color sparkling;
		public float range;

		public SymbolData()
		{
			description = "Symbol";
		}

		public override object create()
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

	#region IPeriod Members

	public void Period()
	{
		Angle += 0.005f;
		if(Angle > limit) Angle -= limit;
	}

	#endregion

	#region IGameObject Members

	public virtual void Build(Engine engine)
	{
		element = Library.Meshes().getMesh(mesh, sparkling);
		light = engine.GetPointLight(range, sparkling, position);
	}

	#endregion

	#region IRenderObject Members

	public void Render()
	{
		element.Render(position, 0f, Angle, 0f);
	}

	#endregion

	#region IGameObject Members

	public void PreRender()
	{
		light.Activate();
	}

	public void PostRender()
	{
		light.DeActivate();
	}

	#endregion
}