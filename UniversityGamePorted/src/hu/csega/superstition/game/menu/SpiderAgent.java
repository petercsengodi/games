package hu.csega.superstition.game.menu;

import org.joml.Vector3f;

import hu.csega.superstition.game.IPeriod;
import hu.csega.superstition.game.animation.Animation;
import hu.csega.superstition.util.StaticRandomLibrary;

class SpiderAgent implements IPeriod {
	protected Animation animation;
	protected Vector3f up, min, max;
	protected Vector3f position, direction, velocity;
	protected static final  int slow = 3;
	protected int state = 0;
	protected float size;
	protected float ch = 0.4f;

	public SpiderAgent(Animation animation, Vector3f up, Vector3f min, Vector3f max)
	{
		this.animation = animation;
		this.min = Vector3.Minimize(min, max);
		this.max = Vector3.Maximize(min, max);
		this.up = up;

		this.position = new Vector3f(
				StaticRandomLibrary.floatValue(min.x, max.x),
				StaticRandomLibrary.floatValue(min.y, max.y),
				StaticRandomLibrary.floatValue(min.z, max.z));
		max.sub(min, this.direction);
		this.velocity = direction * (1f / direction.length()) * 0.02f;

		this.size = StaticRandomLibrary.floatValue() + 0.75f;
	}

	public void Render()
	{
		Matrix m = Matrix.Scaling(size, size, size) *
				Animation.LookAt(position, -direction, up);
		animation.Draw(m, state / slow);
	}

	@Override
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

}
