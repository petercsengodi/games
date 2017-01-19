package hu.csega.superstition.game;

import org.joml.Vector3f;

public class StaticVectorLibrary {

	static public boolean inited = false;

	public class Direction
	{
		public Vector3f NormalVector;
		public SquarePointFunction SquarePoint;
	}

	static public Direction Left = null, Right = null, Front = null,
			Back = null, Top = null, Bottom = null;

	static {
		if(inited) return; else inited = true;

		Left = new Direction(); Left.NormalVector = new Vector3(-1f, 0f, 0f);
		Left.SquarePoint = new SquarePointFunction(LeftSquarePoint);
		Right = new Direction(); Right.NormalVector = new Vector3(1f, 0f, 0f);
		Right.SquarePoint = new SquarePointFunction(RightSquarePoint);
		Front = new Direction(); Front.NormalVector = new Vector3(0f, 0f, 1f);
		Front.SquarePoint = new SquarePointFunction(FrontSquarePoint);
		Back = new Direction(); Back.NormalVector = new Vector3(0f, 0f, -1f);
		Back.SquarePoint = new SquarePointFunction(BackSquarePoint);
		Top = new Direction(); Top.NormalVector = new Vector3(0f, 1f, 0f);
		Top.SquarePoint = new SquarePointFunction(TopSquarePoint);
		Bottom = new Direction(); Bottom.NormalVector = new Vector3(0f, -1f, 0f);
		Bottom.SquarePoint = new SquarePointFunction(BottomSquarePoint);
	}

	/// <summary>
	/// Left Square Point Function.
	/// </summary>
	/// <param name="Min">Minimum Vector.</param>
	/// <param name="Max">Maximum Vector.</param>
	/// <param name="num">Square Point number.</param>
	/// <returns>Direct 3D vertex for Square Point.</returns>
	static public CustomVertex.PositionNormalTextured LeftSquarePoint(Vector3 Min, Vector3 Max, int num)
	{
		switch(num)
		{
		case 1:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Min.Y, Max.Z, -1f, 0f, 0f, -Max.Z, -Min.Y);

		case 2:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Max.Y, Max.Z, -1f, 0f, 0f, -Max.Z, -Max.Y);

		case 3:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Max.Y, Min.Z, -1f, 0f, 0f, -Min.Z, -Max.Y);

		case 0:
		default:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Min.Y, Min.Z, -1f, 0f, 0f, -Min.Z, -Min.Y);
		}
	}

	/// <summary>
	/// Right Square Point Function.
	/// </summary>
	/// <param name="Min">Minimum Vector.</param>
	/// <param name="Max">Maximum Vector.</param>
	/// <param name="num">Square Point number.</param>
	/// <returns>Direct 3D vertex for Square Point.</returns>
	static public CustomVertex.PositionNormalTextured RightSquarePoint(Vector3 Min, Vector3 Max, int num)
	{
		switch(num)
		{
		case 1:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Max.Y, Min.Z, 1f, 0f, 0f, Min.Z, -Max.Y);

		case 2:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Max.Y, Max.Z, 1f, 0f, 0f, Max.Z, -Max.Y);

		case 3:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Min.Y, Max.Z, 1f, 0f, 0f, Max.Z, -Min.Y);

		case 0:
		default:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Min.Y, Min.Z, 1f, 0f, 0f, Min.Z, -Min.Y);
		}
	}

	/// <summary>
	/// Front Square Point Function.
	/// </summary>
	/// <param name="Min">Minimum Vector.</param>
	/// <param name="Max">Maximum Vector.</param>
	/// <param name="num">Square Point number.</param>
	/// <returns>Direct 3D vertex for Square Point.</returns>
	static public CustomVertex.PositionNormalTextured FrontSquarePoint(Vector3 Min, Vector3 Max, int num)
	{
		switch(num)
		{
		case 1:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Min.Y, Max.Z, 0f, 0f, 1f, -Max.X, -Min.Y);

		case 2:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Max.Y, Max.Z, 0f, 0f, 1f, -Max.X, -Max.Y);

		case 3:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Max.Y, Max.Z, 0f, 0f, 1f, -Min.X, -Max.Y);

		case 0:
		default:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Min.Y, Max.Z, 0f, 0f, 1f, -Min.X, -Min.Y);
		}
	}

	/// <summary>
	/// Back Square Point Function.
	/// </summary>
	/// <param name="Min">Minimum Vector.</param>
	/// <param name="Max">Maximum Vector.</param>
	/// <param name="num">Square Point number.</param>
	/// <returns>Direct 3D vertex for Square Point.</returns>
	static public CustomVertex.PositionNormalTextured BackSquarePoint(Vector3 Min, Vector3 Max, int num)
	{
		switch(num)
		{
		case 1:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Max.Y, Min.Z, 0f, 0f, -1f, Min.X, -Max.Y);

		case 2:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Max.Y, Min.Z, 0f, 0f, -1f, Max.X, -Max.Y);

		case 3:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Min.Y, Min.Z, 0f, 0f, -1f, Max.X, -Min.Y);

		case 0:
		default:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Min.Y, Min.Z, 0f, 0f, -1f, Min.X, -Min.Y);			}
	}

	/// <summary>
	/// Top Square Point Function.
	/// </summary>
	/// <param name="Min">Minimum Vector.</param>
	/// <param name="Max">Maximum Vector.</param>
	/// <param name="num">Square Point number.</param>
	/// <returns>Direct 3D vertex for Square Point.</returns>
	static public CustomVertex.PositionNormalTextured TopSquarePoint(Vector3 Min, Vector3 Max, int num)
	{
		switch(num)
		{
		case 1:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Min.Y, Max.Z, 0f, 1f, 0f, Min.X, Max.Z);

		case 2:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Min.Y, Max.Z, 0f, 1f, 0f, Max.X, Max.Z);

		case 3:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Min.Y, Min.Z, 0f, 1f, 0f, Max.X, Min.Z);

		case 0:
		default:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Min.Y, Min.Z, 0f, 1f, 0f, Min.X, Min.Z);
		}
	}

	/// <summary>
	/// Bottom Square Point Function.
	/// </summary>
	/// <param name="Min">Minimum Vector.</param>
	/// <param name="Max">Maximum Vector.</param>
	/// <param name="num">Square Point number.</param>
	/// <returns>Direct 3D vertex for Square Point.</returns>
	static public CustomVertex.PositionNormalTextured BottomSquarePoint(Vector3 Min, Vector3 Max, int num)
	{
		switch(num)
		{
		case 1:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Max.Y, Min.Z, 0f, -1f, 0f, Max.X, Min.Z);

		case 2:
			return new CustomVertex.PositionNormalTextured(
					Max.X, Max.Y, Max.Z, 0f, -1f, 0f, Max.X, Max.Z);

		case 3:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Max.Y, Max.Z, 0f, -1f, 0f, Min.X, Max.Z);

		case 0:
		default:
			return new CustomVertex.PositionNormalTextured(
					Min.X, Max.Y, Min.Z, 0f, -1f, 0f, Min.X, Min.Z);
		}
	}

}
