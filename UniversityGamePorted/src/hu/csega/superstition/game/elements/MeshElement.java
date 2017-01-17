package hu.csega.superstition.game.elements;

public class MeshElement : Element
{
	private Material[] mats;
	private Texture[] texs;
	private Mesh mesh/*, shadowMesh2*/;
	private int texes = 0;
	private AttributeRange[] attributes;
	private Shadow shadow_mesh;

	public MeshElement(Engine engine, string filename, bool shadow)
		: base(engine)
	{
		ExtendedMaterial[] exmats;
		GraphicsStream stream;

		if(filename.Equals("spider_leg.x"))
		{
			Console.WriteLine();
		}

		mesh = Mesh.FromFile(@"..\meshes\" + filename,
			MeshFlags.SystemMemory, engine.Device,
			out stream, out exmats);

		#region Extracting Material Information

		texs = new Texture[exmats.Length];
		mats = new Material[exmats.Length];

		for(int i = 0; i < exmats.Length; i++)
		{
			if(exmats[i].TextureFilename != null) texs[i] =
				Library.Textures().getTexture(
				@"..\textures\mesh_textures\" +
				exmats[i].TextureFilename);
			mats[i] = exmats[i].Material3D;
			mats[i].Ambient = exmats[i].Material3D.Diffuse;
		}

		#endregion

		#region Extracting Adjacency Information

		int[,] adjacency = (int[,])stream.Read(
			typeof(System.Int32), mesh.NumberFaces, 3);
		stream.Close();

		#endregion

		texes = exmats.Length;
		engine.AddToDisposeList(mesh);
		attributes = mesh.GetAttributeTable();

		if(shadow)
		{
			this.shadow_mesh = new Shadow(engine, mesh,
				attributes.Length, adjacency);
			engine.AddToDisposeList(this.shadow_mesh);
		}
		else
		{
			this.shadow_mesh = null;
		}
	}

	public void SetAttrib(Color color)
	{
		for(int i = 0; i < texes; i++)
			mats[i].Emissive = color;
	}

	public override void Render()
	{
		if(engine.IsShadowRendering)
		{
			if(shadow && (shadow_mesh != null) &&
				engine.Options.renderMeshShadow)
			{
				shadow_mesh.Render(inverz);
			}
			return;
		}

//		// For testing
//		if(shadow_mesh != null)
//		{
//			shadow_mesh.Render(inverz);
//		}


		Material temp = engine.Device.Material;
		for(int i = 0; i < attributes.Length; i++)
		{
			if(texs[i] != null) engine.Device.SetTexture(0, texs[i]);
			engine.Device.Material = mats[i];
			mesh.DrawSubset(i);
		}

		engine.Device.Material = temp;
	}

	public override void Dispose()
	{
		engine.RemoveFromDisposeList(mesh);
		mesh.Dispose();

		if(shadow_mesh != null)
		{
			engine.RemoveFromDisposeList(shadow_mesh);
			shadow_mesh.Dispose();
		}
	}

	public float Radius()
	{
		float ret = 0f;
		Vector3 average = new Vector3(0f, 0f, 0f);

		#region Counting Boundig Sphere

		CustomVertex.PositionNormalTextured[] array =
			(CustomVertex.PositionNormalTextured[])
			mesh.LockVertexBuffer(
			typeof(CustomVertex.PositionNormalTextured),
			LockFlags.ReadOnly,
			mesh.NumberVertices);

		for(int i = 0; i < array.Length; i++)
		{
			average += array[i].Position *
				(1f / mesh.NumberVertices);
		}

		float length;
		for(int i = 0; i < array.Length; i++)
		{
			length = (array[i].Position - average).Length();
			ret = Math.Max(ret, length);
		}

		mesh.UnlockVertexBuffer();

		#endregion

		return ret;
	}

	public Vector3[] Borders()
	{
		#region Counting Bounding Box

		Vector3[] corners = null;

		CustomVertex.PositionNormalTextured[] array =
			(CustomVertex.PositionNormalTextured[])
			mesh.LockVertexBuffer(
			typeof(CustomVertex.PositionNormalTextured),
			LockFlags.ReadOnly,
			mesh.NumberVertices);

		if(array.Length > 0)
		{
			corners = new Vector3[2];
			corners[0] = array[0].Position;
			corners[1] = array[0].Position;

			for(int i = 1; i < array.Length; i++)
			{
				corners[0] = Vector3.Minimize(
					corners[0], array[i].Position);
				corners[1] = Vector3.Maximize(
					corners[1], array[i].Position);
			}
		}

		mesh.UnlockVertexBuffer();

		#endregion

		return corners;
	}

	public bool Shot(Vector3 start, Vector3 end, bool infinity)
	{
		if(infinity)
		{
			return mesh.Intersect(start, end - start);
		}

		IntersectInformation closest;
		bool res = mesh.Intersect(start, end - start, out closest);
		if(!res) return false;
		float len = (end - start).Length();
		return len > closest.Dist;
	}

} // End of class Mesh Element