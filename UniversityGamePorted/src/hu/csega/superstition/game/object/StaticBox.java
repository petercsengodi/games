package hu.csega.superstition.game.object;

public class StaticBox extends MapObject implements IDisposable
{
	protected Primitive[] walls;
	protected int FLAGS;
	protected string face;

	public static int FLAG_LEFT = 1, FLAG_RIGHT = 2, FLAG_BACK = 4,
			FLAG_FRONT = 8, FLAG_BOTTOM = 16, FLAG_TOP = 32;

	public StaticBox(Vector3 _corner1, Vector3 _corner2, string face)
	: base(_corner1, _corner2)
	{
		FLAGS = FLAG_LEFT | FLAG_RIGHT | FLAG_FRONT | FLAG_BACK | FLAG_TOP | FLAG_BOTTOM;
		this.face = face;
	}

	public StaticBox(Vector3 _corner1, Vector3 _corner2, string face, int FLAGS)
	: base(_corner1, _corner2)
	{
		this.FLAGS = FLAGS;
		this.face = face;
	}


	protected class BoxData extends GameObjectData
	{
		public Vector3 corner1, corner2, position;
		public int flags;
		public string face;

		public BoxData()
		{
			description = "Static Box";
		}

		public override object create()
		{
			return new StaticBox(this);
		}
	}

	public StaticBox(GameObjectData data)
	: base(new Vector3(), new Vector3())
	{
		BoxData d = data as BoxData;
		this.face = d.face;
		this.corner1 = d.corner1;
		this.corner2 = d.corner2;
		this.FLAGS = d.flags;
		this.position = d.position;
	}

	public override GameObjectData getData()
	{
		BoxData ret = new BoxData();
		ret.face = this.face;
		ret.flags = this.FLAGS;
		ret.corner1 = this.corner1;
		ret.corner2 = this.corner2;
		ret.position = this.position;
		return ret;
	}

	/// <summary>
	/// Builds box visuality.
	/// </summary>
	/// <param name="engine">Game engine.</param>
	public override void Build(Engine engine)
	{
		int i = 0, c_FLAGS = FLAGS;

		for(int j = 0; j < 6; j++)
		{
			i += c_FLAGS % 2;
			c_FLAGS >>= 1;
		}

		walls = new Primitive[i];
		i = 0;


		if( (FLAGS & FLAG_LEFT) > 0 )
		{
			walls[i++] = engine.Pr_Plane(
					corner1,
					new Vector3(corner1.X, corner2.Y, corner2.Z),
					StaticVectorLibrary.Left, face);
		}

		if( (FLAGS & FLAG_RIGHT) > 0 )
		{
			walls[i++] = engine.Pr_Plane(
					new Vector3(corner2.X, corner1.Y, corner1.Z),
					corner2,
					StaticVectorLibrary.Right, face);
		}

		if( (FLAGS & FLAG_BACK) > 0 )
		{
			walls[i++] = engine.Pr_Plane(
					corner1,
					new Vector3(corner2.X, corner2.Y, corner1.Z),
					StaticVectorLibrary.Back, face);
		}

		if( (FLAGS & FLAG_FRONT) > 0 )
		{
			walls[i++] = engine.Pr_Plane(
					new Vector3(corner1.X, corner1.Y, corner2.Z),
					corner2,
					StaticVectorLibrary.Front, face);
		}

		if( (FLAGS & FLAG_BOTTOM) > 0 )
		{
			walls[i++] = engine.Pr_Plane(
					corner1,
					new Vector3(corner2.X, corner1.Y, corner2.Z),
					StaticVectorLibrary.Bottom, face);
		}

		if( (FLAGS & FLAG_TOP) > 0 )
		{
			walls[i++] = engine.Pr_Plane(
					new Vector3(corner1.X, corner2.Y, corner1.Z),
					corner2,
					StaticVectorLibrary.Top, face);
		}

	}

	@Override
	public override void Render()
	{
		for(int i=0; i< walls.Length; i++)
		{
			walls[i].Render();
		} // End of FOR
	}

	public void Dispose()
	{
		for(int i = 0; i < walls.Length; i++)
		{
			if(walls[i] != null) walls[i].Dispose();
		}
	}
}