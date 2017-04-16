package hu.csega.superstition.game.object;

import org.joml.Vector3f;

import hu.csega.superstition.game.StaticVectorLibrary;

public abstract class Clipable extends Clipper
{
	public Vector3f diff = new Vector3f(); // Relative to position
	public Vector3f velocity = new Vector3f();

	public Clipable(Vector3f _position, Vector3f _corner1, Vector3f _corner2) {
		super(_corner1, _corner2, _position);
	}

	/** Behavior when clipped. */
	public void squash(StaticVectorLibrary.Direction dir,
			Vector3f box1, Vector3f box2, Vector3f sqpoint) {

		if(dir == StaticVectorLibrary.Left)
		{ diff.x = sqpoint.x - position.x; velocity.x = 0f; }

		else if(dir == StaticVectorLibrary.Right)
		{ diff.x = sqpoint.x - position.x; velocity.x = 0f; }

		else if(dir == StaticVectorLibrary.Top)
		{ diff.y = sqpoint.y - position.y; velocity.y = 0f; }

		else if(dir == StaticVectorLibrary.Bottom)
		{ diff.y = sqpoint.y - position.y; velocity.y = 0f; }

		else if(dir == StaticVectorLibrary.Front)
		{ diff.z = sqpoint.z - position.z; velocity.z = 0f; }

		else if(dir == StaticVectorLibrary.Back)
		{ diff.z = sqpoint.z - position.z; velocity.z = 0f; }

	}

	public void squash(Clipper clipper, StaticVectorLibrary.Direction dir,
			Vector3f box1, Vector3f box2, Vector3f sqpoint) {
		squash(dir, box1, box2, sqpoint);
	}
}
