package hu.csega.superstition.game.elements;

public class EPlane extends Primitive {

	protected Vector3 Min, Max;
	protected StaticVectorLibrary.Direction direction;
	protected int x, y, z;

	public StaticVectorLibrary.Direction Direction
	{
		get{ return direction; }
	}

	public EPlane(Engine engine, Vector3 MinVec, Vector3 MaxVec,
			StaticVectorLibrary.Direction _direction, Texture _face)
	{
		super(engine);
		Min = MinVec;
		Max = MaxVec;
		direction = _direction;
		face = _face;
		float STEP = engine.Options.detail;

		x = (int) Math.Ceiling((Max.X - Min.X) / STEP);
		y = (int) Math.Ceiling((Max.Y - Min.Y) / STEP);
		z = (int) Math.Ceiling((Max.Z - Min.Z) / STEP);

		if((direction == StaticVectorLibrary.Left) || (direction == StaticVectorLibrary.Right))
		{
			x = 1;
			count = (z + 1) * 2 * y /* For simplified version */ + 6;
		}

		if((direction == StaticVectorLibrary.Front) || (direction == StaticVectorLibrary.Back))
		{
			z = 1;
			count = (x + 1) * 2 * y  /* For simplified version */ + 6;
		}

		if((direction == StaticVectorLibrary.Top) || (direction == StaticVectorLibrary.Bottom))
		{
			y = 1;
			count = (x + 1) * 2 * z /* For simplified version */ + 6;
		}

		ReCreate();
	}

	public EPlane(Engine engine, Vector3 MinVec, Vector3 MaxVec,
			StaticVectorLibrary.Direction _direction, Texture _face, VertexBuffer buffer)
	{
		super(engine, buffer);
		Min = MinVec;
		Max = MaxVec;
		direction = _direction;
		face = _face;

		float STEP = engine.Options.detail;
		x = (int) Math.Ceiling((Max.X - Min.X) / STEP);
		y = (int) Math.Ceiling((Max.Y - Min.Y) / STEP);
		z = (int) Math.Ceiling((Max.Z - Min.Z) / STEP);

		if((direction == StaticVectorLibrary.Left) || (direction == StaticVectorLibrary.Right))
		{
			x = 1;
			count = (z + 1) * 2 * y /* For simplified version */ + 6;
		}

		if((direction == StaticVectorLibrary.Front) || (direction == StaticVectorLibrary.Back))
		{
			z = 1;
			count = (x + 1) * 2 * y  /* For simplified version */ + 6;
		}

		if((direction == StaticVectorLibrary.Top) || (direction == StaticVectorLibrary.Bottom))
		{
			y = 1;
			count = (x + 1) * 2 * z /* For simplified version */ + 6;
		}


		ReCreate();
	}


	public void Initialize(Object buf, EventArgs ea)
	{
		GraphicsStream stream = buffer.Lock(lock_index, 0, 0);

		int i, j;
		float stepx = (Max.X - Min.X) / x, stepy = (Max.Y - Min.Y) / y, stepz = (Max.Z - Min.Z) / z;

		if(direction == StaticVectorLibrary.Left)
		{
			for(j = 0; j < y; j++)
			{
				for(i = 0; i <= z; i++)
				{
					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Max.X, Min.Y + (j + 1) * stepy, Min.Z + i * stepz),
							new Vector3(-1f, 0f, 0f),
							Min.Z + i * stepz, Min.Y + (j + 1) * stepy
							));

					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Min.X, Min.Y + j * stepy, Min.Z + i * stepz),
							new Vector3(-1f, 0f, 0f),
							Min.Z + i * stepz, Min.Y + j * stepy
							));
				}
			}
		} // End of: Left

		if(direction == StaticVectorLibrary.Right)
		{
			for(j = 0; j < y; j++)
			{
				for(i = 0; i <= z; i++)
				{
					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Min.X, Min.Y + j * stepy, Min.Z + i * stepz),
							new Vector3(1f, 0f, 0f),
							Min.Z + i * stepz, Min.Y + j * stepy
							));

					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Max.X, Min.Y + (j + 1) * stepy, Min.Z + i * stepz),
							new Vector3(1f, 0f, 0f),
							Min.Z + i * stepz, Min.Y + (j + 1) * stepy
							));
				}
			}
		} // End of: Right

		if(direction == StaticVectorLibrary.Front)
		{
			for(j = 0; j < y; j++)
			{
				for(i = 0; i <= x; i++)
				{
					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Min.X + i * stepx, Min.Y + (j + 1) * stepy, Max.Z),
							new Vector3(0f, 0f, 1f),
							Min.X + i * stepx, Min.Y + (j + 1) * stepy
							));

					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Min.X + i * stepx, Min.Y + j * stepy, Min.Z),
							new Vector3(0f, 0f, 1f),
							Min.X + i * stepx, Min.Y + j * stepy
							));
				}
			}
		} // End of: Front

		if(direction == StaticVectorLibrary.Back)
		{
			for(j = 0; j < y; j++)
			{
				for(i = 0; i <= x; i++)
				{
					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Min.X + i * stepx, Min.Y + j * stepy, Min.Z),
							new Vector3(0f, 0f, -1f),
							Min.X + i * stepx, Min.Y + j * stepy
							));

					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Min.X + i * stepx, Min.Y + (j + 1) * stepy, Max.Z),
							new Vector3(0f, 0f, -1f),
							Min.X + i * stepx, Min.Y + (j + 1) * stepy
							));
				}
			}
		} // End of: Back

		if(direction == StaticVectorLibrary.Top)
		{
			for(j = 0; j < z; j++)
			{
				for(i = 0; i <= x; i++)
				{
					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Min.X + i * stepx, Max.Y, Min.Z  + j * stepz),
							new Vector3(0f, 1f, 0f),
							Min.X + i * stepx, Min.Z + j * stepz
							));

					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Min.X + i * stepx, Min.Y, Min.Z + (j + 1) * stepz),
							new Vector3(0f, 1f, 0f),
							Min.X + i * stepx, Min.Z + (j + 1) * stepz
							));
				}
			}
		} // End of: Top

		if(direction == StaticVectorLibrary.Bottom)
		{
			for(j = 0; j < z; j++)
			{
				for(i = 0; i <= x; i++)
				{
					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Min.X + i * stepx, Min.Y, Min.Z + (j + 1) * stepz),
							new Vector3(0f, -1f, 0f),
							Min.X + i * stepx, Min.Z + (j + 1) * stepz
							));

					stream.Write(new CustomVertex.PositionNormalTextured(
							new Vector3(Min.X + i * stepx, Max.Y, Min.Z  + j * stepz),
							new Vector3(0f, -1f, 0f),
							Min.X + i * stepx, Min.Z + j * stepz
							));
				}
			}
		} // End of: Bottom

		/* For simplified version */

		stream.Write(direction.SquarePoint(Min, Max, 0));
		stream.Write(direction.SquarePoint(Min, Max, 1));
		stream.Write(direction.SquarePoint(Min, Max, 2));
		stream.Write(direction.SquarePoint(Min, Max, 2));
		stream.Write(direction.SquarePoint(Min, Max, 3));
		stream.Write(direction.SquarePoint(Min, Max, 0));

		buffer.Unlock();
	}

	@Override
	public void Render()
	{
		if(engine.IsShadowRendering)
		{
			if(shadow) RenderShadow();
			return;
		}
		if(face == null) return;

		//		RenderShadow(); return;

		engine.Device.SetTexture(0, face);

		if(NotEffectedByLight && !(engine.IsLighted))
		{
			/* For simplified version */
			engine.Device.SetStreamSource(0, buffer, lock_index);
			engine.Device.VertexFormat = CustomVertex.PositionNormalTextured.Format;
			engine.Device.DrawPrimitives(PrimitiveType.TriangleList, count - 6, 2);
		}
		else
		{
			engine.Device.SetStreamSource(0, buffer, lock_index);
			engine.Device.VertexFormat = CustomVertex.PositionNormalTextured.Format;

			if((direction == StaticVectorLibrary.Left) || (direction == StaticVectorLibrary.Right))
			{
				for(int i = 0; i < y; i ++)
					engine.Device.DrawPrimitives(PrimitiveType.TriangleStrip, ((z+1) * 2) * i , z * 2);
			}

			else if((direction == StaticVectorLibrary.Front) || (direction == StaticVectorLibrary.Back))
			{
				for(int i = 0; i < y; i ++)
					engine.Device.DrawPrimitives(PrimitiveType.TriangleStrip, ((x+1) * 2) * i , x * 2);
			}

			else if((direction == StaticVectorLibrary.Top) || (direction == StaticVectorLibrary.Bottom))
			{
				for(int i = 0; i < z; i ++)
					engine.Device.DrawPrimitives(PrimitiveType.TriangleStrip, ((x+1) * 2) * i , x * 2);
			}
		}

		engine.Device.SetTexture(0, null);
	}

	@Override
	public void RenderShadow()
	{
		if(direction == StaticVectorLibrary.Left)
		{
			engine.RenderVolume(Min, new Vector3(Min.X, Min.Y, Max.Z), Max);
			engine.RenderVolume(Max, new Vector3(Min.X, Max.Y, Min.Z), Min);
		}

		if(direction == StaticVectorLibrary.Right)
		{
			engine.RenderVolume(Min, new Vector3(Min.X, Max.Y, Min.Z), Max);
			engine.RenderVolume(Max, new Vector3(Min.X, Min.Y, Max.Z), Min);
		}

		if(direction == StaticVectorLibrary.Front)
		{
			engine.RenderVolume(Min, new Vector3(Max.X, Min.Y, Min.Z), Max);
			engine.RenderVolume(Max, new Vector3(Min.X, Max.Y, Min.Z), Min);
		}

		if(direction == StaticVectorLibrary.Back)
		{
			engine.RenderVolume(Min, new Vector3(Min.X, Max.Y, Min.Z), Max);
			engine.RenderVolume(Max, new Vector3(Max.X, Min.Y, Min.Z), Min);
		}

		if(direction == StaticVectorLibrary.Top)
		{
			engine.RenderVolume(Min, new Vector3(Min.X, Min.Y, Max.Z), Max);
			engine.RenderVolume(Max, new Vector3(Max.X, Min.Y, Min.Z), Min);
		}

		if(direction == StaticVectorLibrary.Bottom)
		{
			engine.RenderVolume(Min, new Vector3(Max.X, Min.Y, Min.Z), Max);
			engine.RenderVolume(Max, new Vector3(Min.X, Min.Y, Max.Z), Min);
		}
	}


}