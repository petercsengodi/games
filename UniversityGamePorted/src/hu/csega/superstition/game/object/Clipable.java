package hu.csega.superstition.game.object;

public abstract class Clipable : Clipper
{
	public Vector3 diff, // Relative to position
		velocity;

	public Clipable(Vector3 _position, Vector3 _corner1, Vector3 _corner2)
		: base (_corner1, _corner2, _position)
	{
		velocity = diff = new Vector3(0f, 0f, 0f);
	}

	/// <summary>
	/// Behaviour when clipped.
	/// </summary>
	/// <param name="dir">Direction of hit.</param>
	/// <param name="box1">Box hit - lower coordinates.</param>
	/// <param name="box2">Box hit - upper coordinates.</param>
	/// <param name="sqpoint">Hit position.</param>
	public virtual void Squash(StaticVectorLibrary.Direction dir,
		Vector3 box1, Vector3 box2, Vector3 sqpoint)
	{
		#region Clipping behaviour - Stays at borders

		if(dir == StaticVectorLibrary.Left)
		{ diff.X = sqpoint.X - position.X; velocity.X = 0f; }

		else if(dir == StaticVectorLibrary.Right)
		{ diff.X = sqpoint.X - position.X; velocity.X = 0f; }

		else if(dir == StaticVectorLibrary.Top)
		{ diff.Y = sqpoint.Y - position.Y; velocity.Y = 0f; }

		else if(dir == StaticVectorLibrary.Bottom)
		{ diff.Y = sqpoint.Y - position.Y; velocity.Y = 0f; }

		else if(dir == StaticVectorLibrary.Front)
		{ diff.Z = sqpoint.Z - position.Z; velocity.Z = 0f; }

		else if(dir == StaticVectorLibrary.Back)
		{ diff.Z = sqpoint.Z - position.Z; velocity.Z = 0f; }

		#endregion
	}

	public virtual void Squash(Clipper clipper,
		StaticVectorLibrary.Direction dir,
		Vector3 box1, Vector3 box2, Vector3 sqpoint)
	{
		Squash(dir, box1, box2, sqpoint);
	}
}
