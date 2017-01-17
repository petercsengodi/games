package hu.csega.superstition.game.object;

abstract class DynamicObject : Clipable, IGameObject
{
	protected bool alive;
	public bool Alive { get { return alive; } }

	// Others
	public Room CurrentRoom = null;
	protected Engine engine; // !! Only for On-Line building

	public DynamicObject(Vector3 _position, Vector3 _corner1, Vector3 _corner2) : base(_position, _corner1, _corner2)
	{
		alive = true;
	}

	public DynamicObject()
		: base(new Vector3(0f, 0f, 0f),
		new Vector3(0f, 0f, 0f), new Vector3(0f, 0f, 0f))
	{
		alive = true;
	}

	#region IRenderObject Members

	public abstract void Render();

	#endregion

	/// <summary>
	/// Preparation of rendeing. For example: light activation.
	/// </summary>
	public virtual void PreRender(){}

	/// <summary>
	/// Post functions of rendeing. For example: light deactivation.
	/// </summary>
	public virtual void PostRender(){}

	public abstract GameObjectData getData();
	public abstract void Build(Engine engine);

	public virtual void Period()
	{
		float deltat = 0.04f;
		position = Vector3.Add(position, diff);
		diff = Vector3.Multiply(velocity, deltat);
	}
}