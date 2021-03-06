package hu.csega.superstition.game.room;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import hu.csega.superstition.gamelib.network.GameObjectData;

abstract class Hole implements IClipping, IDisposable, IGameObject, IRenderObject
{
	public static float STEP = 0.2f,
			HALF_STEP = STEP/2f,
			DoorBottom = 0.05f,
			DoorSide = 0.15f,
			DoorCeiling = 1f,
			DoorExtrude = 0.1f;

	protected List<Object> planes = new ArrayList<>(), clippers = new ArrayList<>();
	protected Room lower_room, upper_room;
	protected Vector3f box_upper, box_lower;
	protected StaticVectorLibrary.Direction direction;

	public Hole(Room room1, Room room2, StaticVectorLibrary.Direction direction)
	{
		box_upper = new Vector3f();
		box_lower = new Vector3f();


		if(direction == StaticVectorLibrary.Left)
		{
			lower_room = room1;
			upper_room = room2;
			this.direction = StaticVectorLibrary.Left;

		}
		else if(direction == StaticVectorLibrary.Right)
		{
			lower_room = room2;
			upper_room = room1;
			this.direction = StaticVectorLibrary.Left;
		}
		else if(direction == StaticVectorLibrary.Front)
		{
			lower_room = room2;
			upper_room = room1;
			this.direction = StaticVectorLibrary.Front;
		}
		else if(direction == StaticVectorLibrary.Back)
		{
			lower_room = room1;
			upper_room = room2;
			this.direction = StaticVectorLibrary.Front;
		}

		if(this.direction == StaticVectorLibrary.Left)
		{
			box_upper.X = lower_room.Upper.X - DoorExtrude;
			box_lower.X = lower_room.Upper.X + DoorExtrude;
			box_upper.Y = Math.Min(lower_room.Upper.Y, upper_room.Upper.Y) - DoorCeiling;
			box_lower.Y = Math.Max(lower_room.Lower.Y, upper_room.Lower.Y) + DoorBottom;
			box_upper.Z = Math.Min(lower_room.Upper.Z, upper_room.Upper.Z) - DoorSide;
			box_lower.Z = Math.Max(lower_room.Lower.Z, upper_room.Lower.Z) + DoorSide;
		}
		else
		{
			box_upper.X = Math.Min(lower_room.Upper.X, upper_room.Upper.X) - DoorSide;
			box_lower.X = Math.Max(lower_room.Lower.X, upper_room.Lower.X) + DoorSide;
			box_upper.Y = Math.Min(lower_room.Upper.Y, upper_room.Upper.Y) - DoorCeiling;
			box_lower.Y = Math.Max(lower_room.Lower.Y, upper_room.Lower.Y) + DoorBottom;
			box_upper.Z = lower_room.Upper.Z - DoorExtrude;
			box_lower.Z = lower_room.Upper.Z + DoorExtrude;
		}

	}

	/// <summary>
	/// Build the entrance rendering and clipping.
	/// </summary>
	/// <param name="engine">Engine</param>
	public abstract void Build(Engine engine);

	public abstract GameObjectData getData();

	public void Render()
	{
		EPlane plane;
		for(Object o : planes)
		{
			plane = o as EPlane;
			plane.Render();
		}
	}

	public void dispose()
	{
		for(Object o : planes)
		{
			(o as IDisposable).dispose();
		}
	}


	public void Clip(Clipable clipable)
	{
		Clipper clipper;
		for(Object o : clippers)
		{
			clipper = o as Clipper;
			clipper.Clip(clipable);
		}
	}


	public void PreRender()
	{
	}

	public void PostRender()
	{
	}

	public void Period()
	{
	}

}