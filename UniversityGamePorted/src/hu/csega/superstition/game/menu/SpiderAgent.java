package hu.csega.superstition.game.menu;

class SpiderAgent : IPeriod
{
	protected Animation animation;
	protected Vector3 up, min, max;
	protected Vector3 position,
		direction, velocity;
	protected const int slow = 3;
	protected int state = 0;
	protected float size;
	protected float ch = 0.4f;

	public SpiderAgent(Animation animation,
		Vector3 up, Vector3 min, Vector3 max)
	{
		this.animation = animation;
		this.min = Vector3.Minimize(min, max);
		this.max = Vector3.Maximize(min, max);
		this.up = up;

		this.position = new Vector3(
			StaticRandomLibrary.FloatValue(min.X, max.X),
			StaticRandomLibrary.FloatValue(min.Y, max.Y),
			StaticRandomLibrary.FloatValue(min.Z, max.Z));
		this.direction = max - min;
		this.velocity = direction * (1f / direction.Length()) * 0.02f;

		this.size = StaticRandomLibrary.FloatValue(1f) + 0.75f;
	}

	public void Render()
	{
		Matrix m = Matrix.Scaling(size, size, size) *
			Animation.LookAt(position, -direction, up);
		animation.Draw(m, state / slow);
	}

	#region IPeriod Members

	public void Period()
	{
		this.velocity = direction * (1f / direction.Length()) * 0.02f;
		int condition = 0;
		if(!Vector3.Minimize(position + velocity, min).Equals(min))
		{
			velocity = -velocity;
			direction = -direction;
			condition++;
		}
		if(!Vector3.Maximize(position + velocity, max).Equals(max))
		{
			velocity = -velocity;
			direction = -direction;
			condition++;
		}

		if(condition < 2) position += velocity;
		// else {} // TODO

		state++;
		if(state >= animation.MaxScenes * slow)
			state = 0;

		Vector3 change = new Vector3(
			StaticRandomLibrary.FloatValue(-ch, ch),
			StaticRandomLibrary.FloatValue(-ch, ch),
			0f);
		direction += change;
	}

	#endregion
}