package hu.csega.superstition.game.enemy;

import org.joml.Vector3f;

import hu.csega.superstition.game.animation.Animation;
import hu.csega.superstition.game.object.DynamicObject;
import hu.csega.superstition.storygenerator.Room;

public abstract class Enemy extends DynamicObject {

	protected Animation[] animations;
	protected int slow;
	protected int rotation;
	protected Animation current;
	protected EnemyState state;

	public Vector3f direction, up;

	public Enemy()
	{
		slow = 3; // Default value
		rotation = 0;
		current = null;
		state = EnemyState.Stand; // Default value
	}

	@Override
	public void Render()
	{
		current.Render(position, -direction, up, rotation / slow);
	}

	@Override
	public void period()
	{
		super.period();
		Room OldRoom = CurrentRoom;
		if(CurrentRoom != null)
		{
			CurrentRoom.followPlayer(this);
		}

		if(OldRoom != CurrentRoom)
		{
			if(OldRoom != null)
				OldRoom.removeObjectTurn(this);
			if(CurrentRoom != null)
				CurrentRoom.addObjectTurn(this);
		}

		if(current == null) return;
		rotation++;
		if(rotation >= current.getMaxScenes() * slow)
			rotation = 0;

		int scene = rotation / slow;
		corner1 = current.getBoundingBox1(scene);
		corner2 = current.getBoundingBox2(scene);
	}



}