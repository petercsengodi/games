package hu.csega.superstition.game.idontknow;

import org.joml.Vector3f;

class StartPlace implements IGameObject {

	protected Vector3f position, direction;

	public StartPlace(Vector3 position)
	{
		this.position = position;
		this.direction = new Vector3(0f, 0f, 1f);
	}

	public StartPlace(Vector3 position, Vector3 direction)
	{
		this.position = position;
		this.direction = direction;
	}

	public class StartData : GameObjectData
	{
		public Vector3 position, direction;

		public StartData()
		{
			description = "Start Place";
		}

		public object create()
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

	public Vector3 Position
	{
		get { return position; }
	}

	public Vector3 Direction
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