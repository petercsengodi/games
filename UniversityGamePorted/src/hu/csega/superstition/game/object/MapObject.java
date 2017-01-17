package hu.csega.superstition.game.object;

public abstract class MapObject : Clipper, IGameObject
{
	public MapObject(Vector3 _corner1, Vector3 _corner2) : base(_corner1, _corner2)
	{
	}

	/// <summary>
	/// Render this object.
	/// </summary>
	public abstract void Render();
	public abstract GameObjectData getData();
	public abstract void Build(Engine engine);

	#region IGameObject Members

	public virtual void PreRender(){}
	public virtual void PostRender(){}

	#endregion

	#region IPeriod Members

	public void Period()
	{
	}

	#endregion
}
