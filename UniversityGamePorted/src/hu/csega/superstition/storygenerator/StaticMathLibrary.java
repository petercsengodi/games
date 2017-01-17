package hu.csega.superstition.storygenerator;

public class StaticMathLibrary
{
	public static bool InSquare(float x, float y, float x1, float y1, float x2, float y2)
	{
		if((x < x1) || (x > x2)) return false;
		if((y < y1) || (y > y2)) return false;
		return true;
	}

	public static bool InBox(Vector3 p, Vector3 c1, Vector3 c2)
	{
		if((p.X < c1.X) || (p.X > c2.X)) return false;
		if((p.Y < c1.Y) || (p.Y > c2.Y)) return false;
		if((p.Z < c1.Z) || (p.Z > c2.Z)) return false;
		return true;
	}

	public static Vector3 Center(Vector3 _corner1, Vector3 _corner2)
	{
		return new Vector3(
			(_corner1.X + _corner2.X) / 2f,
			(_corner1.Y + _corner2.Y) / 2f,
			(_corner1.Z + _corner2.Z) / 2f);
	}
}