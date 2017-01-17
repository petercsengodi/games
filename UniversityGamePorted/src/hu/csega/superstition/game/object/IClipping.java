package hu.csega.superstition.game.object;

public interface IClipping
{
	/// <summary>
	/// Clips an other object.
	/// </summary>
	/// <param name="clipable">Clipable object.</param>
	void Clip(Clipable clipable);
}
