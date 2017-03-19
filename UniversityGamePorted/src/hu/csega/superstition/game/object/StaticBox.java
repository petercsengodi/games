package hu.csega.superstition.game.object;

import org.joml.Vector3f;

import hu.csega.superstition.common.Disposable;
import hu.csega.superstition.game.elements.Primitive;
import hu.csega.superstition.gamelib.network.GameObjectData;

public class StaticBox extends MapObject implements Disposable {
	protected Primitive[] walls;
	protected int FLAGS;
	protected String face;

	public static int FLAG_LEFT = 1, FLAG_RIGHT = 2, FLAG_BACK = 4,
			FLAG_FRONT = 8, FLAG_BOTTOM = 16, FLAG_TOP = 32;

	public StaticBox(Vector3f _corner1, Vector3f _corner2, String face) {
		super(_corner1, _corner2);
		FLAGS = FLAG_LEFT | FLAG_RIGHT | FLAG_FRONT | FLAG_BACK | FLAG_TOP | FLAG_BOTTOM;
		this.face = face;
	}

	public StaticBox(Vector3f _corner1, Vector3f _corner2, String face, int FLAGS) {
		super(_corner1, _corner2);
		this.FLAGS = FLAGS;
		this.face = face;
	}


	protected class BoxData extends GameObjectData {

		public Vector3f corner1, corner2, position;
		public int flags;
		public String face;

		public BoxData() {
			description = "Static Box";
		}

		public Object create() {
			return new StaticBox(this);
		}
	}

	public StaticBox(GameObjectData data) {
		super(new Vector3f(), new Vector3f());
		BoxData d = (BoxData)data;
		this.face = d.face;
		this.corner1 = d.corner1;
		this.corner2 = d.corner2;
		this.FLAGS = d.flags;
		this.position = d.position;
	}

	@Override
	public GameObjectData getData() {
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
	public void Build(Engine engine)
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
					new Vector3f(corner1.X, corner2.Y, corner2.Z),
					StaticVectorLibrary.Left, face);
		}

		if( (FLAGS & FLAG_RIGHT) > 0 )
		{
			walls[i++] = engine.Pr_Plane(
					new Vector3f(corner2.X, corner1.Y, corner1.Z),
					corner2,
					StaticVectorLibrary.Right, face);
		}

		if( (FLAGS & FLAG_BACK) > 0 )
		{
			walls[i++] = engine.Pr_Plane(
					corner1,
					new Vector3f(corner2.X, corner2.Y, corner1.Z),
					StaticVectorLibrary.Back, face);
		}

		if( (FLAGS & FLAG_FRONT) > 0 )
		{
			walls[i++] = engine.Pr_Plane(
					new Vector3f(corner1.X, corner1.Y, corner2.Z),
					corner2,
					StaticVectorLibrary.Front, face);
		}

		if( (FLAGS & FLAG_BOTTOM) > 0 )
		{
			walls[i++] = engine.Pr_Plane(
					corner1,
					new Vector3f(corner2.X, corner1.Y, corner2.Z),
					StaticVectorLibrary.Bottom, face);
		}

		if( (FLAGS & FLAG_TOP) > 0 )
		{
			walls[i++] = engine.Pr_Plane(
					new Vector3f(corner1.X, corner2.Y, corner1.Z),
					corner2,
					StaticVectorLibrary.Top, face);
		}

	}

	@Override
	public void Render() {
		for(int i=0; i< walls.length; i++) {
			walls[i].Render();
		} // End of FOR
	}

	@Override
	public void dispose() {
		for(int i = 0; i < walls.length; i++) {
			if(walls[i] != null) walls[i].dispose();
		}
	}
}