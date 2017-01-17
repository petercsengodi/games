package hu.csega.superstition.game.enemy;

abstract class Enemy : DynamicObject
{
	protected Animation[] animations;
	protected int slow;
	protected int rotation;
	protected Animation current;
	protected EnemyState state;

	public Vector3 direction, up;

	public Enemy()
	{
		slow = 3; // Default value
		rotation = 0;
		current = null;
		state = EnemyState.Stand; // Default value
	}

	public override void Render()
	{
		current.Render(position, -direction, up, rotation / slow);
	}

	public override void Period()
	{
		base.Period();
		Room OldRoom = CurrentRoom;
		if(CurrentRoom != null)
		{
			CurrentRoom.FollowPlayer(this);
		}

		if(OldRoom != CurrentRoom)
		{
			if(OldRoom != null)
				OldRoom.RemoveObjectTurn(this);
			if(CurrentRoom != null)
				CurrentRoom.AddObjectTurn(this);
		}

		if(current == null) return;
		rotation++;
		if(rotation >= current.MaxScenes * slow)
			rotation = 0;

		int scene = rotation / slow;
		corner1 = current.GetBoundingBox1(scene);
		corner2 = current.GetBoundingBox2(scene);
	}



}