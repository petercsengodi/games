package hu.csega.superstition.game.room;

class OldHole : Hole
{
	private String face = @"..\textures\stair_textures\wood.bmp";

	public OldHole(Room room1, Room room2, StaticVectorLibrary.Direction direction)
		: base(room1, room2, direction)
	{
	}

	public override void Build(Engine engine)
	{
		if(direction == StaticVectorLibrary.Left)
		{
			#region Code for Left / Right direction

			// First side outer tile
			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, upper_room.Lower.Y, upper_room.Lower.Z),
				new Vector3(box_lower.X, upper_room.Upper.Y, box_lower.Z),
				StaticVectorLibrary.Right,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_lower.Y - DoorBottom, box_lower.Z),
				new Vector3(box_lower.X, box_lower.Y, box_upper.Z),
				StaticVectorLibrary.Right,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
				new Vector3(box_lower.X, upper_room.Upper.Y, box_upper.Z),
				StaticVectorLibrary.Right,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, upper_room.Lower.Y, box_upper.Z),
				new Vector3(box_lower.X, upper_room.Upper.Y, upper_room.Upper.Z),
				StaticVectorLibrary.Right,
				face
				));

			// Second side outer tile
			planes.Add(engine.Pr_Plane(
				new Vector3(box_upper.X, lower_room.Lower.Y, lower_room.Lower.Z),
				new Vector3(box_upper.X, lower_room.Upper.Y, box_lower.Z),
				StaticVectorLibrary.Left,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_upper.X, box_lower.Y - DoorBottom, box_lower.Z),
				new Vector3(box_upper.X, box_lower.Y, box_upper.Z),
				StaticVectorLibrary.Left,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_upper.X, box_upper.Y, box_lower.Z),
				new Vector3(box_upper.X, lower_room.Upper.Y, box_upper.Z),
				StaticVectorLibrary.Left,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_upper.X, lower_room.Lower.Y, box_upper.Z),
				new Vector3(box_upper.X, lower_room.Upper.Y, lower_room.Upper.Z),
				StaticVectorLibrary.Left,
				face
				));

			// Inner tile
			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_lower.Y, box_upper.Z),
				new Vector3(box_upper.X, box_upper.Y, box_upper.Z),
				StaticVectorLibrary.Back,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_lower.Y, box_lower.Z),
				new Vector3(box_upper.X, box_upper.Y, box_lower.Z),
				StaticVectorLibrary.Front,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_lower.Y, box_lower.Z),
				new Vector3(box_upper.X, box_lower.Y, box_upper.Z),
				StaticVectorLibrary.Top,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
				new Vector3(box_upper.X, box_upper.Y, box_upper.Z),
				StaticVectorLibrary.Bottom,
				face
				));

			// Clippers
			clippers.Add(new Clipper(
				new Vector3(box_lower.X, Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), Math.Min(upper_room.Lower.Z, lower_room.Lower.Z)),
				new Vector3(box_upper.X, Math.Max(upper_room.Upper.Y, lower_room.Upper.Y), box_lower.Z)
				));

			clippers.Add(new Clipper(
				new Vector3(box_lower.X, Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), box_lower.Z),
				new Vector3(box_upper.X, box_lower.Y, box_upper.Z)
				));

			clippers.Add(new Clipper(
				new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
				new Vector3(box_upper.X, Math.Min(upper_room.Upper.Y, lower_room.Upper.Y), box_upper.Z)
				));

			clippers.Add(new Clipper(
				new Vector3(box_lower.X, Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), box_upper.Z),
				new Vector3(box_upper.X, Math.Max(upper_room.Upper.Y, lower_room.Upper.Y), Math.Max(upper_room.Upper.Z, lower_room.Upper.Z))
				));

			#endregion
		}
		else
		{
			#region Code for Front / Back direction

			// First side outer tile
			planes.Add(engine.Pr_Plane(
				new Vector3(lower_room.Lower.X, lower_room.Lower.Y, box_upper.Z),
				new Vector3(box_lower.X, lower_room.Upper.Y, box_upper.Z),
				StaticVectorLibrary.Back,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_lower.Y - DoorBottom, box_upper.Z),
				new Vector3(box_upper.X, box_lower.Y, box_upper.Z),
				StaticVectorLibrary.Back,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_upper.Y, box_upper.Z),
				new Vector3(box_upper.X, lower_room.Upper.Y, box_upper.Z),
				StaticVectorLibrary.Back,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_upper.X, lower_room.Lower.Y, box_upper.Z),
				new Vector3(lower_room.Upper.X, lower_room.Upper.Y, box_upper.Z),
				StaticVectorLibrary.Back,
				face
				));

			// Second side outer tile
			planes.Add(engine.Pr_Plane(
				new Vector3(upper_room.Lower.X, upper_room.Lower.Y, box_lower.Z),
				new Vector3(box_lower.X, upper_room.Upper.Y, box_lower.Z),
				StaticVectorLibrary.Front,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_lower.Y - DoorBottom, box_lower.Z),
				new Vector3(box_upper.X, box_lower.Y, box_lower.Z),
				StaticVectorLibrary.Front,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
				new Vector3(box_upper.X, upper_room.Upper.Y, box_lower.Z),
				StaticVectorLibrary.Front,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_upper.X, upper_room.Lower.Y, box_lower.Z),
				new Vector3(upper_room.Upper.X, upper_room.Upper.Y, box_lower.Z),
				StaticVectorLibrary.Front,
				face
				));

			// Inner tile
			planes.Add(engine.Pr_Plane(
				new Vector3(box_upper.X, box_lower.Y, box_lower.Z),
				new Vector3(box_upper.X, box_upper.Y, box_upper.Z),
				StaticVectorLibrary.Left,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_lower.Y, box_lower.Z),
				new Vector3(box_lower.X, box_upper.Y, box_upper.Z),
				StaticVectorLibrary.Right,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_lower.Y, box_lower.Z),
				new Vector3(box_upper.X, box_lower.Y, box_upper.Z),
				StaticVectorLibrary.Top,
				face
				));

			planes.Add(engine.Pr_Plane(
				new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
				new Vector3(box_upper.X, box_upper.Y, box_upper.Z),
				StaticVectorLibrary.Bottom,
				face
				));

			// Clippers
			clippers.Add(new Clipper(
				new Vector3(Math.Min(upper_room.Lower.X, lower_room.Lower.X), Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), box_lower.Z),
				new Vector3(box_lower.X, Math.Max(upper_room.Upper.Y, lower_room.Upper.Y), box_upper.Z)
				));

			clippers.Add(new Clipper(
				new Vector3(box_lower.X, Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), box_lower.Z),
				new Vector3(box_upper.X, box_lower.Y, box_upper.Z)
				));

			clippers.Add(new Clipper(
				new Vector3(box_lower.X, box_upper.Y, box_lower.Z),
				new Vector3(box_upper.X, Math.Max(upper_room.Upper.Y, lower_room.Upper.Y), box_upper.Z)
				));

			clippers.Add(new Clipper(
				new Vector3(box_upper.X, Math.Min(upper_room.Lower.Y, lower_room.Lower.Y), box_lower.Z),
				new Vector3(Math.Max(upper_room.Upper.X, lower_room.Upper.X), Math.Max(upper_room.Upper.Y, lower_room.Upper.Y), box_upper.Z)
				));

			#endregion
		}
	}

	public override GameObjectData getData()
	{
		return new GameObjectData("Old Hole");
	}

}