package hu.csega.superstition.game.object;

public interface IGameObject : IRenderObject, IPeriod
{
	/// <summary>
	/// Reads serializable data.
	/// </summary>
	/// <returns>Data object</returns>
	GameObjectData getData();

	/// <summary>
	/// Build object.
	/// </summary>
	/// <param name="engine">Game engine.</param>
	void Build(Engine engine);

	/// <summary>
	/// Pre-Render functionalities. F.e.: Light activation.
	/// </summary>
	void PreRender();

	/// <summary>
	/// Post-Render functionalities. F.e.: Light deactivation.
	/// </summary>
	void PostRender();
}