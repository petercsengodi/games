package hu.csega.superstition.game.elements;

import org.joml.Vector3f;

public class Shadow {

	private VertexBuffer vbuffer; // Rendering only the vbuffer
	// you get the original mesh
	private IndexBuffer ibuffer; // Rendering with help of ibuffer
	// you get the shadow volume sides
	private Engine engine;
	// private int triangles;
	private int originals;
	private int sides;
	private Mesh mesh;
	private int subsets;

	public Shadow(Engine engine, Mesh mesh, int subsets,
			int[][] adjacency)
	{
		this.engine = engine;
		this.mesh = mesh;
		this.subsets = subsets;

		Mesh temp = mesh.Clone( MeshFlags.Use32Bit,
				CustomVertex.PositionNormal.Format, engine.Device);

		CustomVertex.PositionNormal[] vertices =
				(CustomVertex.PositionNormal[])temp.LockVertexBuffer(
						typeof(CustomVertex.PositionNormal),
						LockFlags.ReadOnly, temp.NumberVertices);

		int[] indices = (int[])
				temp.LockIndexBuffer(typeof(System.Int32),
						LockFlags.ReadOnly, temp.NumberFaces * 3);

		int n = temp.NumberFaces;

		Vector3f[] positions = new Vector3f[n][3];
		Vector3f[] normals = new Vector3f[n];

		for(int i = 0; i < n; i++)
		{
			positions[i][0] = vertices[indices[i * 3]].Position;
			positions[i][1] = vertices[indices[i * 3 + 1]].Position;
			positions[i][2] = vertices[indices[i * 3 + 2]].Position;
		}

		Vector3f[] t = new Vector3f[3];
		Vector3f[] vn = new Vector3f[3];
		Vector3f ntmp;
		for(int i = 0; i < n; i++)
		{
			// extracting vertex position informations
			t[0] = positions[i][0];
			t[1] = positions[i][1];
			t[2] = positions[i][2];

			// if null-triangle, then compute normals of vertex-normals
			if((t[0] == t[1]) || (t[0] == t[2]) || (t[1] == t[2]))
			{
				// extracting vertex normal informations
				vn[0] = vertices[indices[i * 3]].Normal;
				vn[1] = vertices[indices[i * 3 + 1]].Normal;
				vn[2] = vertices[indices[i * 3 + 2]].Normal;

				// counting average normal
				ntmp = (vn[0] + vn[1] + vn[2]) * (1f / 3f);
				ntmp.Normalize();
				normals[i] = ntmp;
			}
			else  // compute normals of face-normals
			{
				normals[i] = Vector3.Normalize(
						Vector3.Cross(t[1] - t[0], t[2] - t[0]));
			}
		}

		temp.UnlockVertexBuffer();
		temp.UnlockIndexBuffer();
		temp.Dispose();

		originals = n;
		sides = n * 3;

		vbuffer = new VertexBuffer(typeof(CustomVertex.PositionNormal),
				n * 3, engine.Device, 0,
				CustomVertex.PositionNormal.Format, Pool.Managed);

		ibuffer = new IndexBuffer(typeof(short),
				sides * 3, engine.Device, 0, Pool.Managed);

		GraphicsStream vstream = vbuffer.Lock(0, 0, 0);

		for(int i = 0; i < originals; i++)
			for(int j = 0; j < 3; j++)
			{
				vstream.Write(new CustomVertex.PositionNormal(
						positions[i, j], normals[i]));
			}

		vstream.Dispose();
		vbuffer.Unlock();

		GraphicsStream istream = ibuffer.Lock(0, 0, 0);

		for(int idx = 0; idx < originals; idx++)
		{
			for(int i = 0; i < 3; i++)
			{
				int jdx = adjacency[idx, i];

				short[] res = GetCommonVertices(
						positions, idx, jdx);

				istream.Write(res[0]);
				istream.Write(res[2]);
				istream.Write(res[1]);

				//				Console.Write(res[0] + " " + res[2] + " " + res[1]);
				//				Console.WriteLine();

			}
		}

		istream.Dispose();
		ibuffer.Unlock();

	}


	public short[] GetCommonVertices(Vector3f[][] positions, int idx, int jdx)
	{
		short[] ret = null;
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if( positions[idx][i].Equals(positions[jdx][(j+1)%3]) &&
						positions[idx][(i+1)%3].Equals(positions[jdx][j]))
				{
					ret = new short[4];
					ret[0] = (short)(idx * 3 + i);
					ret[1] = (short)(idx * 3 + (i + 1) % 3);
					ret[2] = (short)(jdx * 3 + j);
					ret[3] = (short)(jdx * 3 + (j + 1) % 3);
					return ret;
				}
			}
		}
		return ret;
	}

	public void Render(Matrix inverse)
	{
		engine.RenderVolume(new VolumeRender(RenderVolume),
				mesh, subsets, inverse);
	}

	protected void RenderVolume()
	{
		if(engine.Options.depth_algorythm == DepthAlgorythm.Pass)
		{
			// Depth Pass - Used in Menu
			engine.Device.SetStreamSource(0, vbuffer, 0);
			engine.Device.VertexFormat = CustomVertex.PositionNormal.Format;
			engine.Device.Indices = ibuffer;

			// Rendering front-face
			engine.Device.RenderState.CullMode = Cull.CounterClockwise;
			engine.Device.RenderState.StencilPass = StencilOperation.Increment;
			engine.Device.DrawIndexedPrimitives(PrimitiveType.TriangleList,
					0, 0, originals * 3, 0, sides);

			// Rendering back-face
			engine.Device.RenderState.CullMode = Cull.Clockwise;
			engine.Device.RenderState.StencilPass = StencilOperation.Decrement;
			engine.Device.DrawIndexedPrimitives(PrimitiveType.TriangleList,
					0, 0, originals * 3, 0, sides);

			engine.Device.RenderState.CullMode = Cull.CounterClockwise;
		}
		else
		{
			// Carmack's Reverse - Used in Game

			engine.Device.Indices = ibuffer;
			engine.Device.SetStreamSource(0, vbuffer, 0);
			engine.Device.VertexFormat = CustomVertex.PositionNormal.Format;

			// Rendering back-face
			engine.Device.RenderState.CullMode = Cull.Clockwise;
			engine.Device.RenderState.StencilZBufferFail = StencilOperation.Increment;
			engine.Device.DrawIndexedPrimitives(PrimitiveType.TriangleList,
					0, 0, originals * 3, 0, sides);
			engine.Device.RenderState.ZBufferFunction = Compare.Never;
			engine.Device.DrawPrimitives(PrimitiveType.TriangleList, 0, originals);
			engine.Device.RenderState.ZBufferFunction = Compare.LessEqual;

			// Rendering front-face
			engine.Device.RenderState.CullMode = Cull.CounterClockwise;
			engine.Device.RenderState.StencilZBufferFail = StencilOperation.Decrement;
			engine.Device.DrawIndexedPrimitives(PrimitiveType.TriangleList,
					0, 0, originals * 3, 0, sides);
			engine.Device.RenderState.ZBufferFunction = Compare.Never;
			engine.Device.DrawPrimitives(PrimitiveType.TriangleList, 0, originals);
			engine.Device.RenderState.ZBufferFunction = Compare.LessEqual;

		}
	}

	public void Dispose()
	{
		vbuffer.Dispose();
		ibuffer.Dispose();
	}
}