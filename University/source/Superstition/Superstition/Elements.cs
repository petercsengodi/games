using System;
using System.Drawing;
using System.Collections;
using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;

namespace Engine
{

	/// <summary>
	/// Class for Renderable Elements constructed by primitives.
	/// </summary>
	public abstract class Element : IDisposable
	{
		protected Engine engine;
		private static Vector3 vup = new Vector3(0f, 1f, 0f);
		protected bool shadow = true;
		protected Matrix inverz = Matrix.Identity;

		/// <summary>
		/// Turn on/off element shadow. (True as default.)
		/// </summary>
		public bool Shadow
		{
			get{ return shadow; }
			set{ shadow = value; }
		}

		/// <summary>
		/// Class for Renderable Elements.
		/// </summary>
		/// <param name="_device">Device used by DirectX Engine.</param>
		public Element(Engine _engine)
		{
			engine = _engine;
		}

		/// <summary>
		/// Rendering the element.
		/// </summary>
		/// <param name="position">Position of the Object in the World.</param>
		/// <param name="orientation">Orientation of the Oject in the World.</param>
		public void Render(Vector3 translation, Vector3 orientation)
		{
			Matrix world;
			inverz = Matrix.LookAtLH(translation, 
				Vector3.Add(translation, orientation), vup);
			world = inverz;
			world.Invert();
			engine.Device.Transform.World = world;

			Render();

			engine.Device.Transform.World = Matrix.Identity;
		}

		/// <summary>
		/// Rendering the element.
		/// </summary>
		/// <param name="world">World matrix for element.</param>
		public void Render(Matrix world)
		{
			inverz = world;
			inverz.Invert();
			engine.Device.Transform.World = world;
			Render();
			engine.Device.Transform.World = Matrix.Identity;
		}

		/// <summary>
		/// Rendering the element.
		/// </summary>
		/// <param name="translation">Translation Vector.</param>
		public void Render(Vector3 translation)
		{
			engine.Device.Transform.World = Matrix.Translation(translation);
			inverz = Matrix.Translation(-translation);
			
			Render();

			engine.Device.Transform.World = Matrix.Identity;
		}

		/// <summary>
		/// Rendering the element.
		/// </summary>
		/// <param name="translation">Position of the Object in the World.</param>
		/// <param name="xRotation">Rotation of the Object at Axis X in the World.</param>
		/// <param name="yRotation">Rotation of the Object at Axis Y in the World.</param>
		/// <param name="zRotation">Rotation of the Object at Axis Z in the World.</param>
		public void Render(Vector3 translation, float xRotation, float yRotation, float zRotation)
		{
			engine.Device.Transform.World = 
				Matrix.Multiply(Matrix.RotationX(xRotation),
				Matrix.Multiply(Matrix.RotationY(yRotation),
				Matrix.Multiply(Matrix.RotationZ(zRotation),
				Matrix.Translation(translation))));

			inverz = Matrix.Multiply(Matrix.Translation(-translation),
				Matrix.Multiply(Matrix.RotationZ(-zRotation),
				Matrix.Multiply(Matrix.RotationY(-yRotation),
				Matrix.RotationX(-xRotation))));
			
			Render();

			engine.Device.Transform.World = Matrix.Identity;
		}

		/// <summary>
		/// Rendering the element.
		/// </summary>
		public abstract void Render();

		/// <summary>
		/// Rendering the shadow volume of an element.
		/// </summary>
		public virtual void RenderShadow(){}

		/// <summary>
		/// Disposes this element. (Gets itself out of Engine's 
		/// dispose list)
		/// </summary>
		abstract public void Dispose();

	} // End of Element

	/// <summary>
	/// Class representing mesh's shadow
	/// </summary>
	class Shadow : IDisposable
	{
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
			int[,] adjacency)
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

			Vector3[,] positions = new Vector3[n, 3];
			Vector3[] normals = new Vector3[n];

			#region Getting Vertex Positions

			for(int i = 0; i < n; i++)
			{
				positions[i,0] = vertices[indices[i * 3]].Position;
				positions[i,1] = vertices[indices[i * 3 + 1]].Position;
				positions[i,2] = vertices[indices[i * 3 + 2]].Position;
			}

			#endregion

			#region Counting Normals of Triangles

			Vector3[] t = new Vector3[3];
			Vector3[] vn = new Vector3[3];
			Vector3 ntmp;
			for(int i = 0; i < n; i++)
			{
				// extracting vertex position informations
				t[0] = positions[i, 0];
				t[1] = positions[i, 1];
				t[2] = positions[i, 2];

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

			#endregion

			temp.UnlockVertexBuffer();
			temp.UnlockIndexBuffer();
			temp.Dispose();

			#region Build Shadow Volume Triangles

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

//					Console.Write(res[0] + " " + res[2] + " " + res[1]);
//					Console.WriteLine();

				}
			}

			istream.Dispose();
			ibuffer.Unlock();

			#endregion
		}


		public short[] GetCommonVertices(Vector3[,] positions, int idx, int jdx)
		{
			short[] ret = null;
			for(int i = 0; i < 3; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					if( positions[idx, i].Equals(positions[jdx, (j+1)%3]) &&
						positions[idx, (i+1)%3].Equals(positions[jdx, j]))
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

	/// <summary>
	/// Class for meshes.
	/// </summary>
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

//			// For testing
//			if(shadow_mesh != null) 
//			{
//				shadow_mesh.Render(inverz);
//			} 

	
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

	/// <summary>
	/// Class for text meshes. Sholud be used only for the menu.
	/// </summary>
	public class MeshText : Element
	{
		private Material material;
		private Mesh mesh;
		private GlyphMetricsFloat[] metrics;
		private float extrusion;
		private Vector3 center = new Vector3(0f, 0f, 0f);
		public Vector3 Center { get { return center; } }
		public string test;

		public MeshText(Engine engine, System.Drawing.Font font, string text, float deviation, float extrusion)
			: base(engine)
		{
			test = text;
			mesh = Mesh.TextFromFont(engine.Device, font, text, deviation, extrusion, out metrics);
			engine.AddToDisposeList(mesh);
			material = new Material();
			material.Ambient = Color.White;
			material.Diffuse = Color.White;

			this.extrusion = extrusion;

			for(int i = 0; i < metrics.Length; i++)
			{
				center.X += metrics[i].BlackBoxX;
				center.Y = Math.Max(center.Y, metrics[i].BlackBoxY);
			}
			center.Z = -extrusion / 2f;
		}

		public override void Render()
		{
			if(engine.IsShadowRendering)
			{ 
				if(shadow)
				{ 
					if(engine.Options.renderMeshShadow) RenderShadow();
					return; 
				}
			}
			
//			RenderShadow(); return; 

			Material temp = engine.Device.Material;
			engine.Device.Material = material;
			mesh.DrawSubset(0);
			engine.Device.Material = temp;
		}

		public override void Dispose()
		{
			engine.RemoveFromDisposeList(mesh);
			mesh.Dispose();
		}

		public override void RenderShadow()
		{
			Matrix temp = engine.Device.Transform.World;
			Matrix temp2 = Matrix.Identity;
			
			Vector3 light = engine.LightPosition; 
			Vector3 b = Vector3.TransformCoordinate(
				new Vector3(center.X, center.Y, 0f), 
				engine.Device.Transform.World);
			Vector3 a = Vector3.TransformCoordinate( 
				new Vector3(center.X, center.Y, -extrusion), 
				engine.Device.Transform.World);
			float l = (light - a).Length();
			float d = l / (20f + l);
			float z1 = a.Z, z2 = b.Z;

			temp2.M31 = (d - 1f) * light.X / (z2 - z1);
			temp2.M32 = (d - 1f) * light.Y / (z2 - z1);
			temp2.M33 = (d - 1f) * light.Z / (z2 - z1);
			temp2.M34 = (d - 1f) / (z2 - z1);
			temp2.M41 = - z1 * (d - 1f) * light.X / (z2 - z1);
			temp2.M42 = - z1 * (d - 1f) * light.Y / (z2 - z1);
			temp2.M43 = - z1 * (d - 1f) * light.Z / (z2 - z1);
			temp2.M44 = 1 - z1 * (d - 1f) / (z2 - z1);
			engine.Device.Transform.World = temp * temp2;


			engine.Device.RenderState.CullMode = Cull.CounterClockwise;
//			Device.RenderState.CullMode = Cull.CounterClockwise;
			engine.Device.RenderState.StencilPass = StencilOperation.Increment;
			mesh.DrawSubset(0);

			engine.Device.RenderState.CullMode = Cull.Clockwise;
//			Device.RenderState.CullMode = Cull.Clockwise;
			engine.Device.RenderState.StencilPass = StencilOperation.Decrement;
			mesh.DrawSubset(0);

			engine.Device.RenderState.CullMode = Cull.CounterClockwise;
			engine.Device.Transform.World = temp;
		}

	}


	/// <summary>
	/// Class for Renderable Primitives.
	/// </summary>
	public abstract class Primitive : Element
	{
		protected Texture face = null;
		protected VertexBuffer buffer = null;
		protected int count, lock_index;
		protected bool foreign_buffer = false;

		protected bool notEffectedByLight = false;
		public bool NotEffectedByLight{ get { return notEffectedByLight; } set { notEffectedByLight = value; }}
		public int Count{ get { return count; } }

		/// <summary>
		/// Constructor.
		/// </summary>
		/// <param name="device">Device used by DirectX Engine.</param>
		public Primitive(Engine engine) : base(engine)
		{
			buffer = null;
			lock_index = 0;
			foreign_buffer = false;
		}

		/// <summary>
		/// Constructor with foreign buffer.
		/// </summary>
		/// <param name="engine"></param>
		/// <param name="buffer"></param>
		/// <param name="buffer_index"></param>
		public Primitive(Engine engine, VertexBuffer buffer) : base (engine)
		{
			this.buffer = buffer;
			foreign_buffer = true;
			lock_index = buffer.SizeInBytes;
		}

		/// <summary>
		/// Recreating Primitive.
		/// </summary>
		public virtual void ReCreate()
		{
			if(face == null) return;

			if(!foreign_buffer)
			{
				buffer = new VertexBuffer(typeof(CustomVertex.PositionNormalTextured),
					count, engine.Device, 0, CustomVertex.PositionNormalTextured.Format, Pool.Managed);
				engine.AddToDisposeList(buffer);
			}
			
			buffer.Created += new EventHandler(Initialize);
			Initialize(buffer, null);
		}

		/// <summary>
		/// Initializing Primitive.
		/// </summary>
		/// <param name="buf">VertexBuffer.</param>
		/// <param name="ea">Not Used.</param>
		abstract public void Initialize(object buf, EventArgs ea);

		/// <summary>
		/// ReInitialization of Primitive.
		/// </summary>
		public virtual void ReInit(){ Initialize(buffer, null); }

		/// <summary>
		/// Dispose function.
		/// </summary>
		public override void Dispose()
		{
			engine.RemoveFromDisposeList(buffer);
			if(buffer != null) buffer.Dispose();
			buffer = null;
		}

	} // End of Primitive


	/// <summary>
	/// Class for a tessellated Plane with faces of six directions.
	/// </summary>
	public class EPlane : Primitive
	{
		protected Vector3 Min, Max;
		protected StaticVectorLibrary.Direction direction;
		protected int x, y, z;
		
		public StaticVectorLibrary.Direction Direction
		{
			get{ return direction; }
		}

		public EPlane(Engine engine, Vector3 MinVec, Vector3 MaxVec, 
			StaticVectorLibrary.Direction _direction, Texture _face) : base(engine)
		{
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
			StaticVectorLibrary.Direction _direction, Texture _face, VertexBuffer buffer) : base(engine, buffer)
		{
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


		public override void Initialize(object buf, EventArgs ea)
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

		public override void Render()
		{
			if(engine.IsShadowRendering)
			{ 
				if(shadow) RenderShadow(); 
				return;
			}
			if(face == null) return;

//			RenderShadow(); return;

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

		public override void RenderShadow()
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

	/// <summary>
	/// A Triangle that's tesselated, keeping the culling.
	/// </summary>
	public class ETesselatedTriangle : Primitive
	{
		private Vector3 a, b, c, normal;
		private float lAB, lAC, lBC;
		private int n;

		public ETesselatedTriangle(Engine engine, Vector3 a, Vector3 b, Vector3 c, Texture face) : base(engine)
		{
			this.a = a;
			this.b = b;
			this.c = c;
			this.face = face;
			Construct();
		}

		public ETesselatedTriangle(Engine engine, VertexBuffer buffer, Vector3 a, Vector3 b, Vector3 c, Texture face) : base(engine, buffer)
		{
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

		public override void RenderShadow()
		{
			engine.RenderVolume(a, b, c);
			engine.RenderVolume(c, b, a);
		}
	}


}