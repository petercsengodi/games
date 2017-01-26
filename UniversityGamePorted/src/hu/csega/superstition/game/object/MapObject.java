package hu.csega.superstition.game.object;

public abstract class MapObject implements Clipper, IGameObject
{
	public MapObject(Vector3 _corner1, Vector3 _corner2) {
		super(_corner1, _corner2);
	}

	/// <summary>
	/// Render this object.
	/// </summary>
	@Override
	public abstract void Render();
	@Override
	public abstract GameObjectData getData();
	public abstract void Build(Engine engine);


	public void PreRender(){}
	public void PostRender(){}


	@Override
	public void Period()
	{
	}

}
