package hu.csega.superstition.game.object;

public class Clipper : IClipping
{
	public Vector3 position, // Position of the object
		corner1, corner2; // Relative to position;
	// usually corner1 negative, corner 2 positive
	public static float STEP = 0.2f, HALFSTEP = STEP / 2f;

	public Clipper(Vector3 _corner1, Vector3 _corner2)
	{
		corner1 = Vector3.Minimize(_corner1, _corner2);
		corner2 = Vector3.Maximize(_corner1, _corner2);
		position = new Vector3(0f, 0f, 0f);
	}

	public Clipper(Vector3 _corner1, Vector3 _corner2, Vector3 _position)
	{
		corner1 = Vector3.Minimize(_corner1, _corner2);
		corner2 = Vector3.Maximize(_corner1, _corner2);
		position = _position;
	}

	public Vector3 Position
	{
		get { return position; }
		set { position = value; }
	}

	#region IClipping Members

	public virtual void Clip(Clipable clipable)
	{
		Vector3 box1 = Vector3.Add(Vector3.Subtract(corner1, clipable.corner2), position);
		Vector3 box2 = Vector3.Add(Vector3.Subtract(corner2, clipable.corner1), position);
		float t, x, y, z;

		// Clipping for all directions
		if((clipable.position.X <= box1.X) && (clipable.position.X + clipable.diff.X > box1.X))
		{ // Left -> Right
			t = (box1.X - clipable.position.X) / clipable.diff.X;
			y = clipable.position.Y + clipable.diff.Y * t;
			z = clipable.position.Z + clipable.diff.Z * t;
			if(StaticMathLibrary.InSquare(y, z, box1.Y, box1.Z, box2.Y, box2.Z))
				clipable.Squash(this,
					StaticVectorLibrary.Right, box1, box2,
					new Vector3(box1.X, y, z));
		}
		else if((clipable.position.X >= box2.X) && (clipable.position.X + clipable.diff.X < box2.X))
		{ // Right -> Left
			t = (box2.X - clipable.position.X) / clipable.diff.X;
			y = clipable.position.Y + clipable.diff.Y * t;
			z = clipable.position.Z + clipable.diff.Z * t;
			if(StaticMathLibrary.InSquare(y, z, box1.Y, box1.Z, box2.Y, box2.Z))
				clipable.Squash(this,
					StaticVectorLibrary.Left, box1, box2,
					new Vector3(box2.X, y, z));
		}

		if((clipable.position.Y <= box1.Y) && (clipable.position.Y + clipable.diff.Y > box1.Y))
		{ // Bottom -> Top
			t = (box1.Y - clipable.position.Y) / clipable.diff.Y;
			x = clipable.position.X + clipable.diff.X * t;
			z = clipable.position.Z + clipable.diff.Z * t;
			if(StaticMathLibrary.InSquare(x, z, box1.X, box1.Z, box2.X, box2.Z))
				clipable.Squash(this,
					StaticVectorLibrary.Top, box1, box2,
					new Vector3(x, box1.Y, z));
		}
		else if((clipable.position.Y >= box2.Y) && (clipable.position.Y + clipable.diff.Y < box2.Y))
		{ // Top -> Bottom
			t = (box2.Y - clipable.position.Y) / clipable.diff.Y;
			x = clipable.position.X + clipable.diff.X * t;
			z = clipable.position.Z + clipable.diff.Z * t;
			if(StaticMathLibrary.InSquare(x, z, box1.X, box1.Z, box2.X, box2.Z))
				clipable.Squash(this,
					StaticVectorLibrary.Bottom, box1, box2,
					new Vector3(x, box2.Y, z));
		}

		if((clipable.position.Z <= box1.Z) && (clipable.position.Z + clipable.diff.Z > box1.Z))
		{ // Front -> Back
			t = (box1.Z - clipable.position.Z) / clipable.diff.Z;
			x = clipable.position.X + clipable.diff.X * t;
			y = clipable.position.Y + clipable.diff.Y * t;
			if(StaticMathLibrary.InSquare(x, y, box1.X, box1.Y, box2.X, box2.Y))
				clipable.Squash(this,
					StaticVectorLibrary.Back, box1, box2,
					new Vector3(x, y, box1.Z));
		}
		else if((clipable.position.Z >= box2.Z) && (clipable.position.Z + clipable.diff.Z < box2.Z))
		{ // Back -> Front
			t = (box2.Z - clipable.position.Z) / clipable.diff.Z;
			x = clipable.position.X + clipable.diff.X * t;
			y = clipable.position.Y + clipable.diff.Y * t;
			if(StaticMathLibrary.InSquare(x, y, box1.X, box1.Y, box2.X, box2.Y))
				clipable.Squash(this,
					StaticVectorLibrary.Front, box1, box2,
					new Vector3(x, y, box2.Z));
		}
	}

	#endregion

	public virtual void PlayerEffect(object player)
	{
	}
}
