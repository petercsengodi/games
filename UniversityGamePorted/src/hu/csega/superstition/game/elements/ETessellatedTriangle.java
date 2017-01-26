package hu.csega.superstition.game.elements;

public class ETesselatedTriangle extends Primitive {

	private Vector3 a, b, c, normal;
	private float lAB, lAC, lBC;
	private int n;

	public ETesselatedTriangle(Engine engine, Vector3 a, Vector3 b, Vector3 c, Texture face)
	{

		super(engine);
		this.a = a;
		this.b = b;
		this.c = c;
		this.face = face;
		Construct();
	}

	public ETesselatedTriangle(Engine engine, VertexBuffer buffer, Vector3 a, Vector3 b, Vector3 c, Texture face)
	{
		super(engine, buffer);
		this.a = a;
		this.b = b;
		this.c = c;
		this.face = face;
		Construct();
	}

	private void Construct()
	{
		Vector3 AB = b - a, AC = c - a, BC = c - b;
		float l = engine.Options.detail;
		n = Math.Max( (int)Math.Ceiling(AB.Length() / l) , (int)Math.Ceiling(AC.Length() / l));
		n = Math.Max( (int)Math.Ceiling(BC.Length() / l) , n);
		lAB = AB.Length() / n;
		lAC = AC.Length() / n;
		lBC = BC.Length() / n;

		count = 0;
		int prev = 0, next = 1;
		for(int i = 0; i < n; i++)
		{
			prev = next;
			next++;
			count += prev + next;
		}

		count += 3;
		normal = Vector3.Cross(AB, AC);
		normal.Normalize();

		ReCreate();
	}

	public override void Initialize(object buf, EventArgs ea)
	{
		GraphicsStream stream = ((VertexBuffer)buf).Lock(lock_index, 0, 0);
		Vector3 AB = b - a, AC = c - a, BC = c - b, tB, tC, tX;
		Vector3[] pre_array, next_array;
		pre_array = new Vector3[1];
		pre_array[0] = a;

		//	Counting matrix for Textures
		Matrix m,
		t1 = Matrix.Identity,
		t2 = Matrix.Identity;

		t2.M11 =  normal.X;
		t2.M13 = -normal.Z;
		t2.M31 =  normal.Z;
		t2.M33 =  normal.X;

		Vector3 temp = Vector3.TransformCoordinate(normal, t2);

		t1.M11 =  temp.X;
		t1.M12 = -temp.Y;
		t1.M21 =  temp.Y;
		t1.M22 =  temp.X;

		m = Matrix.Multiply(t2, t1);

		for(int i = 1; i <= n; i++)
		{
			tB = ((float)i / (float)n) * (b - a) + a;
			tC = ((float)i / (float)n) * (c - a) + a;

			next_array = new Vector3[i+1];
			for(int j = 0; j <= i; j++)
			{
				next_array[j] = tB * ((float)(i-j) / (float)i) + tC * ((float)j / (float)i);
			}

			tX = Vector3.TransformCoordinate(next_array[i], m);
			stream.Write(new CustomVertex.PositionNormalTextured(
					next_array[i], normal, tX.Z, tX.Y
					));

			for(int j = i - 1; j >= 0; j--)
			{
				tX = Vector3.TransformCoordinate(pre_array[j], m);
				stream.Write(new CustomVertex.PositionNormalTextured(
						pre_array[j], normal, tX.Z, tX.Y
						));

				tX = Vector3.TransformCoordinate(next_array[j], m);
				stream.Write(new CustomVertex.PositionNormalTextured(
						next_array[j], normal, tX.Z, tX.Y
						));
			}

			pre_array = next_array;
		}

		tX = Vector3.TransformCoordinate(a, m);
		stream.Write(new CustomVertex.PositionNormalTextured(
				a, normal, tX.Z, tX.Y
				));
		tX = Vector3.TransformCoordinate(b, m);
		stream.Write(new CustomVertex.PositionNormalTextured(
				b, normal, tX.Z, tX.Y
				));
		tX = Vector3.TransformCoordinate(c, m);
		stream.Write(new CustomVertex.PositionNormalTextured(
				c, normal, tX.Z, tX.Y
				));

		((VertexBuffer)(buf)).Unlock();
	}

	@Override
	public override void Render()
	{
		if(engine.IsShadowRendering)
		{
			if(shadow) RenderShadow();
			return;
		}

		//	RenderShadow(); return;

		engine.Device.SetTexture(0, face);
		engine.Device.SetStreamSource(0, buffer, lock_index);
		engine.Device.VertexFormat = CustomVertex.PositionNormalTextured.Format;

		if(!notEffectedByLight)
		{
			for(int j = 1, t = 0; j <= n; j++)
			{
				engine.Device.DrawPrimitives(PrimitiveType.TriangleStrip, t, (j-1)*2 + 1);
				t += j*2 + 1;
			}
		}
		else
		{
			engine.Device.DrawPrimitives(PrimitiveType.TriangleStrip, count - 3, 1);
		}

		engine.Device.SetTexture(0, null);
	}

	@Override
	public override void RenderShadow()
	{
		engine.RenderVolume(a, b, c);
		engine.RenderVolume(c, b, a);
	}
}