package hu.csega.superstition.game.room;

class OldStair extends Stair
{
	public OldStair(Room room1, Room room2, StaticVectorLibrary.Direction direction) {
		super(room1, room2, direction);
	}

	public void Build(Engine engine)
	{
		float dense;
		int steps;
		String face = "/res/textures/stair_textures/images.bmp";

		steps = (int)Math.Ceiling((box_upper.Y - box_lower.Y) / HALFSTEP);
		dense = (box_upper.Y - box_lower.Y) / steps;

		Vector3
		upper = box_upper,
		lower = box_lower,
		last_upper = upper,
		last_lower = lower;

		if(direction == StaticVectorLibrary.Left)
		{

			lower.X = upper.X;
			last_lower = lower;
			for(int i = 0; i < steps;  i++)
			{
				lower.X += STEP;
				lower.Z = Math.Max( lower.Z - HALFSTEP, lower_room.Lower.Z );
				upper.Z = Math.Min( upper.Z + HALFSTEP, lower_room.Upper.Z );

				// Front part of stair
				planes.Add(engine.Pr_Plane(
						new Vector3f(lower.X, upper.Y, last_lower.Z),
						new Vector3f(last_lower.X, upper.Y, last_upper.Z),
						StaticVectorLibrary.Top,
						face
						));
				planes.Add(engine.Pr_Plane(
						new Vector3f(lower.X, upper.Y, last_lower.Z),
						new Vector3f(lower.X, upper.Y - dense, last_upper.Z),
						StaticVectorLibrary.Right,
						face
						));

				// Upper Z part
				if(last_upper.Z < lower_room.Upper.Z)
				{
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, last_upper.Z),
							new Vector3f(upper.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, last_upper.Z),
							new Vector3f(lower.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Right,
							face
							));
				}
				if(upper.Z < lower_room.Upper.Z)
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, upper.Z),
							new Vector3f(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Front,
							face
							));

				// Lower Z part
				if(last_lower.Z > lower_room.Lower.Z)
				{
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, lower.Z),
							new Vector3f(upper.X, upper.Y, last_lower.Z),
							StaticVectorLibrary.Top,
							face
							));
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, lower.Z),
							new Vector3f(lower.X, upper.Y - dense, last_lower.Z),
							StaticVectorLibrary.Right,
							face
							));
				}
				if(lower.Z > lower_room.Lower.Z)
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, lower.Z),
							new Vector3f(upper.X, upper.Y - dense, lower.Z),
							StaticVectorLibrary.Back,
							face
							));

				clippers.Add(new Clipper(lower, upper));

				last_upper = upper;
				last_lower = lower;

				upper.Y -= dense;
			}

		}
		else if(direction == StaticVectorLibrary.Right)
		{

			upper.X = lower.X;
			last_upper = upper;
			for(int i = 0; i < steps;  i++)
			{
				upper.X -= STEP;
				lower.Z = Math.Max( lower.Z - HALFSTEP, lower_room.Lower.Z );
				upper.Z = Math.Min( upper.Z + HALFSTEP, lower_room.Upper.Z );

				// Front part of stair
				planes.Add(engine.Pr_Plane(
						new Vector3f(last_upper.X, upper.Y, last_lower.Z),
						new Vector3f(upper.X, upper.Y, last_upper.Z),
						StaticVectorLibrary.Top,
						face
						));
				planes.Add(engine.Pr_Plane(
						new Vector3f(upper.X, upper.Y, last_lower.Z),
						new Vector3f(upper.X, upper.Y - dense, last_upper.Z),
						StaticVectorLibrary.Left,
						face
						));

				// Upper Z part
				if(last_upper.Z < lower_room.Upper.Z)
				{
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, last_upper.Z),
							new Vector3f(upper.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
					planes.Add(engine.Pr_Plane(
							new Vector3f(upper.X, upper.Y, last_upper.Z),
							new Vector3f(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Left,
							face
							));
				}
				if(upper.Z < lower_room.Upper.Z)
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, upper.Z),
							new Vector3f(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Front,
							face
							));

				// Lower Z part
				if(last_lower.Z > lower_room.Lower.Z)
				{
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, lower.Z),
							new Vector3f(upper.X, upper.Y, last_lower.Z),
							StaticVectorLibrary.Top,
							face
							));
					planes.Add(engine.Pr_Plane(
							new Vector3f(upper.X, upper.Y, lower.Z),
							new Vector3f(upper.X, upper.Y - dense, last_lower.Z),
							StaticVectorLibrary.Left,
							face
							));
				}
				if(lower.Z > lower_room.Lower.Z)
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, lower.Z),
							new Vector3f(upper.X, upper.Y - dense, lower.Z),
							StaticVectorLibrary.Back,
							face
							));

				clippers.Add(new Clipper(lower, upper));

				last_upper = upper;
				last_lower = lower;

				upper.Y -= dense;
			}


		}
		else if(direction == StaticVectorLibrary.Front)
		{

			lower.Z = upper.Z;
			last_lower = lower;
			for(int i = 0; i < steps;  i++)
			{
				lower.Z -= STEP;
				lower.X = Math.Max( lower.X - HALFSTEP, lower_room.Lower.X );
				upper.X = Math.Min( upper.X + HALFSTEP, lower_room.Upper.X );

				// Front part of stair
				planes.Add(engine.Pr_Plane(
						new Vector3f(last_lower.X, upper.Y, lower.Z),
						new Vector3f(last_upper.X, upper.Y, last_lower.Z),
						StaticVectorLibrary.Top,
						face
						));
				planes.Add(engine.Pr_Plane(
						new Vector3f(last_lower.X, upper.Y, lower.Z),
						new Vector3f(last_upper.X, upper.Y - dense, lower.Z),
						StaticVectorLibrary.Back,
						face
						));

				// Upper X part
				if(last_upper.X < lower_room.Upper.X)
				{
					planes.Add(engine.Pr_Plane(
							new Vector3f(last_upper.X, upper.Y, lower.Z),
							new Vector3f(upper.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
					planes.Add(engine.Pr_Plane(
							new Vector3f(last_upper.X, upper.Y, lower.Z),
							new Vector3f(upper.X, upper.Y - dense, lower.Z),
							StaticVectorLibrary.Back,
							face
							));
				}
				if(upper.X < lower_room.Upper.X)
					planes.Add(engine.Pr_Plane(
							new Vector3f(upper.X, upper.Y, lower.Z),
							new Vector3f(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Right,
							face
							));

				// Lower X part
				if(last_lower.X > lower_room.Lower.X)
				{
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, lower.Z),
							new Vector3f(last_lower.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, lower.Z),
							new Vector3f(last_lower.X, upper.Y - dense, lower.Z),
							StaticVectorLibrary.Back,
							face
							));
				}
				if(lower.X > lower_room.Lower.X)
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, lower.Z),
							new Vector3f(lower.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Left,
							face
							));

				clippers.Add(new Clipper(lower, upper));

				last_upper = upper;
				last_lower = lower;

				upper.Y -= dense;
			}

		}
		else if(direction == StaticVectorLibrary.Back)
		{

			upper.Z = lower.Z;
			last_upper = upper;
			for(int i = 0; i < steps;  i++)
			{
				upper.Z += STEP;
				lower.X = Math.Max( lower.X - HALFSTEP, lower_room.Lower.X );
				upper.X = Math.Min( upper.X + HALFSTEP, lower_room.Upper.X );

				// Front part of stair
				planes.Add(engine.Pr_Plane(
						new Vector3f(last_lower.X, upper.Y, last_upper.Z),
						new Vector3f(last_upper.X, upper.Y, upper.Z),
						StaticVectorLibrary.Top,
						face
						));
				planes.Add(engine.Pr_Plane(
						new Vector3f(last_lower.X, upper.Y, upper.Z),
						new Vector3f(last_upper.X, upper.Y - dense, upper.Z),
						StaticVectorLibrary.Front,
						face
						));

				// Upper X part
				if(last_upper.X < lower_room.Upper.X)
				{
					planes.Add(engine.Pr_Plane(
							new Vector3f(last_upper.X, upper.Y, lower.Z),
							new Vector3f(upper.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
					planes.Add(engine.Pr_Plane(
							new Vector3f(last_upper.X, upper.Y, upper.Z),
							new Vector3f(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Front,
							face
							));
				}
				if(upper.X < lower_room.Upper.X)
					planes.Add(engine.Pr_Plane(
							new Vector3f(upper.X, upper.Y, lower.Z),
							new Vector3f(upper.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Right,
							face
							));

				// Lower X part
				if(last_lower.X > lower_room.Lower.X)
				{
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, lower.Z),
							new Vector3f(last_lower.X, upper.Y, upper.Z),
							StaticVectorLibrary.Top,
							face
							));
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, upper.Z),
							new Vector3f(last_lower.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Front,
							face
							));
				}
				if(lower.X > lower_room.Lower.X)
					planes.Add(engine.Pr_Plane(
							new Vector3f(lower.X, upper.Y, lower.Z),
							new Vector3f(lower.X, upper.Y - dense, upper.Z),
							StaticVectorLibrary.Left,
							face
							));

				clippers.Add(new Clipper(lower, upper));

				last_upper = upper;
				last_lower = lower;

				upper.Y -= dense;
			}

		}

	}

	public GameObjectData getData()
	{
		return new GameObjectData("Old Stair");
	}


}