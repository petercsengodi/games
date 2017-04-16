package hu.csega.superstition.game.idontknow;

import org.joml.Vector3f;

class StartPlace implements IGameObject {

	protected Vector3f position, direction;

	public StartPlace(Vector3f position)
	{
		this.position = position;
		this.direction = new Vector3f(0f, 0f, 1f);
	}

	public StartPlace(Vector3f position, Vector3f direction)
	{
		this.position = position;
		this.direction = direction;
	}

	public class StartData : GameObjectData
	{
		public Vector3f position, direction;

		public StartData()
		{
			description = "Start Place";
		}

		public Object create()
		{
			return new StartPlace(position, direction);
		}


	}

	public GameObjectData getData()
	{
		StartData ret = new StartData();
		ret.position = position;
		ret.direction = direction;
		return ret;
	}

	public Vector3f Position
	{
		get { return position; }
	}

	public Vector3f Direction
	{
		get { return direction; }
	}

	public void Build(Engine engine)
	{
	}

	public void PreRender()
	{
	}

	public void PostRender()
	{
	}

	public void Render()
	{
	}

	public void Period()
	{
	}

}