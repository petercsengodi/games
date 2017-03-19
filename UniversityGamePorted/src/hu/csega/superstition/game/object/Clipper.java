package hu.csega.superstition.game.object;

import org.joml.Vector3f;

import hu.csega.superstition.collection.VectorStack;
import hu.csega.superstition.game.StaticVectorLibrary;
import hu.csega.superstition.util.StaticMathLibrary;
import hu.csega.superstition.util.Vectors;

public class Clipper implements IClipping {

	public Vector3f position = new Vector3f(); // Position of the object
	public Vector3f corner1 = new Vector3f(), corner2 = new Vector3f(); // Relative to position;

	// usually corner1 negative, corner 2 positive
	public static float STEP = 0.2f, HALFSTEP = STEP / 2f;

	public Clipper(Vector3f _corner1, Vector3f _corner2) {
		Vectors.minimize(_corner1, _corner2, corner1);
		Vectors.maximize(_corner1, _corner2, corner2);
	}

	public Clipper(Vector3f _corner1, Vector3f _corner2, Vector3f _position) {
		Vectors.minimize(_corner1, _corner2, corner1);
		Vectors.maximize(_corner1, _corner2, corner2);
		this.position.set(_position);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position.set(position);
	}

	@Override
	public void Clip(Clipable clipable) {

		Vector3f box1 = VectorStack.newVector3f();
		Vector3f box2 = VectorStack.newVector3f();
		Vector3f tmp = VectorStack.newVector3f();

		// box1 = corner1 - clipable.corner2 + position
		corner1.sub(clipable.corner2, tmp);
		tmp.add(position, box1);

		// box2 = corner2 - clipable.corner1 + position
		corner2.sub(clipable.corner1, tmp);
		tmp.add(position, box2);

		float t, x, y, z;

		// Clipping for all directions
		if((clipable.position.x <= box1.x) && (clipable.position.x + clipable.diff.x > box1.x))
		{ // Left -> Right

			t = (box1.x - clipable.position.x) / clipable.diff.x;
			y = clipable.position.y + clipable.diff.y * t;
			z = clipable.position.z + clipable.diff.z * t;

			tmp.set(box1.x, y, z);

			if(StaticMathLibrary.inSquare(y, z, box1.y, box1.z, box2.y, box2.z))
				clipable.squash(this, StaticVectorLibrary.Right, box1, box2, tmp);

		} else if((clipable.position.x >= box2.x) && (clipable.position.x + clipable.diff.x < box2.x))
		{ // Right -> Left

			t = (box2.x - clipable.position.x) / clipable.diff.x;
			y = clipable.position.y + clipable.diff.y * t;
			z = clipable.position.z + clipable.diff.z * t;

			tmp.set(box2.x, y, z);

			if(StaticMathLibrary.inSquare(y, z, box1.y, box1.z, box2.y, box2.z))
				clipable.squash(this, StaticVectorLibrary.Left, box1, box2, tmp);
		}

		if((clipable.position.y <= box1.y) && (clipable.position.y + clipable.diff.y > box1.y))
		{ // Bottom -> Top

			t = (box1.y - clipable.position.y) / clipable.diff.y;
			x = clipable.position.x + clipable.diff.x * t;
			z = clipable.position.z + clipable.diff.z * t;

			tmp.set(x, box1.y, z);

			if(StaticMathLibrary.inSquare(x, z, box1.x, box1.z, box2.x, box2.z))
				clipable.squash(this, StaticVectorLibrary.Top, box1, box2, tmp);

		} else if((clipable.position.y >= box2.y) && (clipable.position.y + clipable.diff.y < box2.y))
		{ // Top -> Bottom
			t = (box2.y - clipable.position.y) / clipable.diff.y;
			x = clipable.position.x + clipable.diff.x * t;
			z = clipable.position.z + clipable.diff.z * t;

			tmp.set(x, box2.y, z);

			if(StaticMathLibrary.inSquare(x, z, box1.x, box1.z, box2.x, box2.z))
				clipable.squash(this, StaticVectorLibrary.Bottom, box1, box2, tmp);
		}

		if((clipable.position.z <= box1.z) && (clipable.position.z + clipable.diff.z > box1.z))
		{ // Front -> Back

			t = (box1.z - clipable.position.z) / clipable.diff.z;
			x = clipable.position.x + clipable.diff.x * t;
			y = clipable.position.y + clipable.diff.y * t;

			tmp.set(x, y, box1.z);

			if(StaticMathLibrary.inSquare(x, y, box1.x, box1.y, box2.x, box2.y))
				clipable.squash(this, StaticVectorLibrary.Back, box1, box2, tmp);

		} else if((clipable.position.z >= box2.z) && (clipable.position.z + clipable.diff.z < box2.z))
		{ // Back -> Front
			t = (box2.z - clipable.position.z) / clipable.diff.z;
			x = clipable.position.x + clipable.diff.x * t;
			y = clipable.position.y + clipable.diff.y * t;

			tmp.set(x, y, box2.z);

			if(StaticMathLibrary.inSquare(x, y, box1.x, box1.y, box2.x, box2.y))
				clipable.squash(this, StaticVectorLibrary.Front, box1, box2, tmp);
		}

		VectorStack.release(tmp);
		VectorStack.release(box2);
		VectorStack.release(box1);
	}

	public void PlayerEffect(Object player)
	{
	}
}
