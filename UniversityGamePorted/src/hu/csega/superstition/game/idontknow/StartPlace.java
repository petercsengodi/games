package hu.csega.superstition.game.idontknow;

class StartPlace : IGameObject
{
	protected Vector3 position, direction;

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

	#region IGameObject Members

	[Serializable]
	public class StartData : GameObjectData
	{
		public Vector3 position, direction;

		public StartData()
		{
			description = "Start Place";
		}

		public override object create()
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

	#endregion

	#region IRenderObject Members

	public void Render()
	{
	}

	#endregion

	#region IPeriod Members

	public void Period()
	{
	}

	#endregion

}