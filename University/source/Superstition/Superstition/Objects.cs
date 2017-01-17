using System;
using System.Xml;
using System.Drawing;
using System.Collections;
using Superstition;
using Mathematics;
using Microsoft.DirectX;
using System.Windows.Forms;

namespace Model
{
	using Engine;
	using GameLib;

	/// <summary>
	/// Interface for Renderable Model Elements.
	/// </summary>
	public interface IRenderObject
	{
		void Render();
	}

	/// <summary>
	/// Base class for primitives wrapped into the model.
	/// </summary>
	public class RenderPrimitive : IRenderObject, IDisposable
	{
		protected Primitive primitive;
		protected bool needToDispose = true;

		/// <summary>
		/// Whether this object has to dispose itself or not.
		/// </summary>
		public bool NeedToDispose
		{
			get{ return needToDispose; }
			set{ needToDispose = value; }
		}

		public RenderPrimitive(Primitive _primitive)
		{
			primitive = _primitive;
		}

		#region IRenderObject Members

		public void Render()
		{
			primitive.Render();
		}

		#endregion

		#region IDisposable Members

		public void Dispose()
		{
			if(needToDispose) primitive.Dispose();
		}

		#endregion
	}

	/// <summary>
	/// Interface for Objects to serialize
	/// </summary>
	public interface IGameObject : IRenderObject, IPeriod
	{
		/// <summary>
		/// Reads serializable data.
		/// </summary>
		/// <returns>Data object</returns>
		GameObjectData getData();

		/// <summary>
		/// Build object.
		/// </summary>
		/// <param name="engine">Game engine.</param>
		void Build(Engine engine);

		/// <summary>
		/// Pre-Render functionalities. F.e.: Light activation.
		/// </summary>
		void PreRender();

		/// <summary>
		/// Post-Render functionalities. F.e.: Light deactivation.
		/// </summary>
		void PostRender();
	}

	/// <summary>
	/// Interface for Clip-objects;
	/// </summary>
	public interface IClipping
	{
		/// <summary>
		/// Clips an other object.
		/// </summary>
		/// <param name="clipable">Clipable object.</param>
		void Clip(Clipable clipable);
	}

	/// <summary>
	/// A base class for Clipping objects.
	/// </summary>
	public class Clipper : IClipping
	{
		public Vector3 position, // Position of the object
			corner1, corner2; // Relative to position; 
		// usually corner1 negative, corner 2 positive
		public static float STEP = 0.2f, HALFSTEP = STEP / 2f;

		public Clipper(Vector3 _corner1, Vector3 _corner2)
		{
			corner1 = Vector3.Minimize(_corner1, _corner2);
			corner2 = Vector3.Maximize(_corner1, _corner2);
			position = new Vector3(0f, 0f, 0f);
		}

		public Clipper(Vector3 _corner1, Vector3 _corner2, Vector3 _position)
		{
			corner1 = Vector3.Minimize(_corner1, _corner2);
			corner2 = Vector3.Maximize(_corner1, _corner2);
			position = _position;
		}

		public Vector3 Position
		{
			get { return position; }
			set { position = value; }
		}

		#region IClipping Members

		public virtual void Clip(Clipable clipable)
		{
			Vector3 box1 = Vector3.Add(Vector3.Subtract(corner1, clipable.corner2), position);
			Vector3 box2 = Vector3.Add(Vector3.Subtract(corner2, clipable.corner1), position);
			float t, x, y, z; 
			
			// Clipping for all directions
			if((clipable.position.X <= box1.X) && (clipable.position.X + clipable.diff.X > box1.X))
			{ // Left -> Right
				t = (box1.X - clipable.position.X) / clipable.diff.X;
				y = clipable.position.Y + clipable.diff.Y * t;
				z = clipable.position.Z + clipable.diff.Z * t;
				if(StaticMathLibrary.InSquare(y, z, box1.Y, box1.Z, box2.Y, box2.Z))
					clipable.Squash(this,
						StaticVectorLibrary.Right, box1, box2,
						new Vector3(box1.X, y, z));
			} 
			else if((clipable.position.X >= box2.X) && (clipable.position.X + clipable.diff.X < box2.X))
			{ // Right -> Left
				t = (box2.X - clipable.position.X) / clipable.diff.X;
				y = clipable.position.Y + clipable.diff.Y * t;
				z = clipable.position.Z + clipable.diff.Z * t;
				if(StaticMathLibrary.InSquare(y, z, box1.Y, box1.Z, box2.Y, box2.Z))
					clipable.Squash(this,
						StaticVectorLibrary.Left, box1, box2,
						new Vector3(box2.X, y, z));
			}

			if((clipable.position.Y <= box1.Y) && (clipable.position.Y + clipable.diff.Y > box1.Y))
			{ // Bottom -> Top
				t = (box1.Y - clipable.position.Y) / clipable.diff.Y;
				x = clipable.position.X + clipable.diff.X * t;
				z = clipable.position.Z + clipable.diff.Z * t;
				if(StaticMathLibrary.InSquare(x, z, box1.X, box1.Z, box2.X, box2.Z))
					clipable.Squash(this,
						StaticVectorLibrary.Top, box1, box2,
						new Vector3(x, box1.Y, z));
			} 
			else if((clipable.position.Y >= box2.Y) && (clipable.position.Y + clipable.diff.Y < box2.Y))
			{ // Top -> Bottom
				t = (box2.Y - clipable.position.Y) / clipable.diff.Y;
				x = clipable.position.X + clipable.diff.X * t;
				z = clipable.position.Z + clipable.diff.Z * t;
				if(StaticMathLibrary.InSquare(x, z, box1.X, box1.Z, box2.X, box2.Z))
					clipable.Squash(this,
						StaticVectorLibrary.Bottom, box1, box2,
						new Vector3(x, box2.Y, z));
			}

			if((clipable.position.Z <= box1.Z) && (clipable.position.Z + clipable.diff.Z > box1.Z))
			{ // Front -> Back
				t = (box1.Z - clipable.position.Z) / clipable.diff.Z;
				x = clipable.position.X + clipable.diff.X * t;
				y = clipable.position.Y + clipable.diff.Y * t;
				if(StaticMathLibrary.InSquare(x, y, box1.X, box1.Y, box2.X, box2.Y))
					clipable.Squash(this,
						StaticVectorLibrary.Back, box1, box2,
						new Vector3(x, y, box1.Z));
			} 
			else if((clipable.position.Z >= box2.Z) && (clipable.position.Z + clipable.diff.Z < box2.Z))
			{ // Back -> Front
				t = (box2.Z - clipable.position.Z) / clipable.diff.Z;
				x = clipable.position.X + clipable.diff.X * t;
				y = clipable.position.Y + clipable.diff.Y * t;
				if(StaticMathLibrary.InSquare(x, y, box1.X, box1.Y, box2.X, box2.Y))
					clipable.Squash(this,
						StaticVectorLibrary.Front, box1, box2,
						new Vector3(x, y, box2.Z));
			}
		}

		#endregion

		public virtual void PlayerEffect(object player)
		{
		}
	}

	/// <summary>
	/// Abstract base class for Moving objects should be clipped.
	/// </summary>
	public abstract class Clipable : Clipper
	{
		public Vector3 diff, // Relative to position
			velocity;

		public Clipable(Vector3 _position, Vector3 _corner1, Vector3 _corner2)
			: base (_corner1, _corner2, _position)
		{
			velocity = diff = new Vector3(0f, 0f, 0f);			
		}

		/// <summary>
		/// Behaviour when clipped.
		/// </summary>
		/// <param name="dir">Direction of hit.</param>
		/// <param name="box1">Box hit - lower coordinates.</param>
		/// <param name="box2">Box hit - upper coordinates.</param>
		/// <param name="sqpoint">Hit position.</param>
		public virtual void Squash(StaticVectorLibrary.Direction dir, 
			Vector3 box1, Vector3 box2, Vector3 sqpoint)
		{
			#region Clipping behaviour - Stays at borders

			if(dir == StaticVectorLibrary.Left)
			{ diff.X = sqpoint.X - position.X; velocity.X = 0f; }
			
			else if(dir == StaticVectorLibrary.Right)
			{ diff.X = sqpoint.X - position.X; velocity.X = 0f; }
			
			else if(dir == StaticVectorLibrary.Top)
			{ diff.Y = sqpoint.Y - position.Y; velocity.Y = 0f; }

			else if(dir == StaticVectorLibrary.Bottom)
			{ diff.Y = sqpoint.Y - position.Y; velocity.Y = 0f; }

			else if(dir == StaticVectorLibrary.Front)
			{ diff.Z = sqpoint.Z - position.Z; velocity.Z = 0f; }

			else if(dir == StaticVectorLibrary.Back)
			{ diff.Z = sqpoint.Z - position.Z; velocity.Z = 0f; }

			#endregion
		}

		public virtual void Squash(Clipper clipper, 
			StaticVectorLibrary.Direction dir, 
			Vector3 box1, Vector3 box2, Vector3 sqpoint)
		{
			Squash(dir, box1, box2, sqpoint);
		}
	}

	/// <summary>
	/// Base class for static map objects.
	/// </summary>
	public abstract class MapObject : Clipper, IGameObject
	{
		public MapObject(Vector3 _corner1, Vector3 _corner2) : base(_corner1, _corner2)
		{
		}

		/// <summary>
		/// Render this object.
		/// </summary>
		public abstract void Render();
		public abstract GameObjectData getData();
		public abstract void Build(Engine engine);

		#region IGameObject Members

		public virtual void PreRender(){}
		public virtual void PostRender(){}

		#endregion

		#region IPeriod Members

		public void Period()
		{
		}

		#endregion
	}

	/// <summary>
	/// Abstract base class for Dynamic objects.
	/// </summary>
	abstract class DynamicObject : Clipable, IGameObject
	{
		protected bool alive;
		public bool Alive { get { return alive; } }

		// Others
		public Room CurrentRoom = null;
		protected Engine engine; // !! Only for On-Line building

		public DynamicObject(Vector3 _position, Vector3 _corner1, Vector3 _corner2) : base(_position, _corner1, _corner2)
		{
			alive = true;
		}

		public DynamicObject() 
			: base(new Vector3(0f, 0f, 0f), 
			new Vector3(0f, 0f, 0f), new Vector3(0f, 0f, 0f))
		{
			alive = true;
		}

		#region IRenderObject Members

		public abstract void Render();

		#endregion

		/// <summary>
		/// Preparation of rendeing. For example: light activation.
		/// </summary>
		public virtual void PreRender(){}

		/// <summary>
		/// Post functions of rendeing. For example: light deactivation.
		/// </summary>
		public virtual void PostRender(){}

		public abstract GameObjectData getData();
		public abstract void Build(Engine engine);

		public virtual void Period()
		{
			float deltat = 0.04f;
			position = Vector3.Add(position, diff);
			diff = Vector3.Multiply(velocity, deltat);
		}
	}

	/// <summary>
	/// Class for Torch.
	/// </summary>
	class ThrowableTorch : DynamicObject
	{
		protected PointLight light;
		protected Element element;
		protected bool stand;

		/// <summary>
		/// Serializable data class for torches.
		/// </summary>
		[Serializable]
		protected class TorchData : GameObjectData
		{
			public Vector3 position, corner1, corner2, velocity, diff;
			public bool stand, alive;

			public TorchData()
			{
				description = "Thorch";
			}

			public override object create()
			{
				return new ThrowableTorch(this);
			}
		}
		
		/// <summary>
		/// Torch stands or moving.
		/// </summary>
		public bool Stand
		{
			get { return stand; }
			set { stand = value; }
		}
		
		public ThrowableTorch(Vector3 position, Vector3 speed)
			: base(position, new Vector3(-0.125f, -0.125f, -0.125f), new Vector3(0.125f, 0.125f, 0.125f))
		{
			alive = true;
			this.velocity = speed;
		}

		public ThrowableTorch(GameObjectData data):
			base(new Vector3(), new Vector3(), new Vector3())
		{
			TorchData d = data as TorchData;
			this.alive = d.alive;
			this.corner1 = d.corner1;
			this.corner2 = d.corner2;
			this.diff = d.diff;
			this.stand = d.stand;
			this.velocity = d.velocity;
			this.position = d.position;
		}

		/// <summary>
		/// Builds visuality.
		/// </summary>
		/// <param name="engine">Game Engine.</param>
		public override void Build(Engine engine)
		{
			this.engine = engine;
			this.element = Library.Meshes().getMesh(@"..\meshes\torch.x", false, Color.White);
			light = engine.GetPointLight(100f /* 15f */, Color.FromArgb(255, 255, 0), position);
		}

		public override GameObjectData getData()
		{
			TorchData ret = new TorchData();
			ret.alive = this.alive;
			ret.corner1 = this.corner1;
			ret.corner2 = this.corner2;
			ret.diff = this.diff;
			ret.stand = this.stand;
			ret.velocity = this.velocity;
			ret.position = this.position;
			return ret;
		}

		public override void PreRender()
		{
			light.Position = position;
			if(engine.IsLighted) light.Activate();
		}

		public override void Render()
		{
			element.Render(position);	
		}

		public override void PostRender()
		{
			if(engine.IsLighted) light.DeActivate();
		}

		public override void Squash(StaticVectorLibrary.Direction dir, Vector3 box1, Vector3 box2, Vector3 sqpoint)
		{
			#region Falls Back with Energy Loss
			
			if((dir == StaticVectorLibrary.Left) || (dir == StaticVectorLibrary.Right))
			{ 
				diff.X = 2 * (sqpoint.X - position.X) - diff.X; 
				velocity.X = -0.4f * velocity.X; 
				if(Math.Abs(velocity.X) < 0.01f) velocity.X = 0.00f;
			}
			
			if((dir == StaticVectorLibrary.Top) || (dir == StaticVectorLibrary.Bottom))
			{
				diff.Y = 2 * (sqpoint.Y - position.Y) - diff.Y; 
				velocity.Y = -0.6f * velocity.Y;
				velocity.X = 0.6f * velocity.X;
				velocity.Z = 0.6f * velocity.Z;
				if(Math.Abs(velocity.Y) < 0.01f) velocity.Y = 0.00f;
			}

			if((dir == StaticVectorLibrary.Front) || (dir == StaticVectorLibrary.Back))
			{ 
				diff.Z = 2 * (sqpoint.Z - position.Z) - diff.Z; 
				velocity.Z = -0.4f * velocity.Z; 
				if(Math.Abs(velocity.Z) < 0.01f) velocity.Z = 0.00f;
			}

			#endregion
		}

		public override void Period()
		{
			float deltat = 0.04f;
			if(!stand) velocity.Y -= 10f * deltat / 2f;
			base.Period();
		}
	}

	/// <summary>
	/// Static box object for the map.
	/// </summary>
	public class StaticBox : MapObject, IDisposable
	{
		protected Primitive[] walls;
		protected int FLAGS;
		protected string face;

		public static int FLAG_LEFT = 1, FLAG_RIGHT = 2, FLAG_BACK = 4, 
			FLAG_FRONT = 8, FLAG_BOTTOM = 16, FLAG_TOP = 32;

		public StaticBox(Vector3 _corner1, Vector3 _corner2, string face)
			: base(_corner1, _corner2)
		{
			FLAGS = FLAG_LEFT | FLAG_RIGHT | FLAG_FRONT | FLAG_BACK | FLAG_TOP | FLAG_BOTTOM;
			this.face = face;
		}

		public StaticBox(Vector3 _corner1, Vector3 _corner2, string face, int FLAGS)
			: base(_corner1, _corner2)
		{
			this.FLAGS = FLAGS;
			this.face = face;
		}

		/// <summary>
		/// Serializable data class fo box.
		/// </summary>
		[Serializable]
		protected class BoxData : GameObjectData
		{
			public Vector3 corner1, corner2, position;
			public int flags;
			public string face;

			public BoxData()
			{
				description = "Static Box";
			}

			public override object create()
			{
				return new StaticBox(this);
			}
		}

		public StaticBox(GameObjectData data)
			: base(new Vector3(), new Vector3())
		{
			BoxData d = data as BoxData;
			this.face = d.face;
			this.corner1 = d.corner1;
			this.corner2 = d.corner2;
			this.FLAGS = d.flags;
			this.position = d.position;
		}

		public override GameObjectData getData()
		{
			BoxData ret = new BoxData();
			ret.face = this.face;
			ret.flags = this.FLAGS;
			ret.corner1 = this.corner1;
			ret.corner2 = this.corner2;
			ret.position = this.position;
			return ret;
		}

		/// <summary>
		/// Builds box visuality.
		/// </summary>
		/// <param name="engine">Game engine.</param>
		public override void Build(Engine engine)
		{
			int i = 0, c_FLAGS = FLAGS;
			
			for(int j = 0; j < 6; j++)
			{
				i += c_FLAGS % 2;
				c_FLAGS >>= 1;
			}

			walls = new Primitive[i];
			i = 0;

			#region Wall Planes

			if( (FLAGS & FLAG_LEFT) > 0 )
			{
				walls[i++] = engine.Pr_Plane(
					corner1,
					new Vector3(corner1.X, corner2.Y, corner2.Z),
					StaticVectorLibrary.Left, face);
			}

			if( (FLAGS & FLAG_RIGHT) > 0 )
			{
				walls[i++] = engine.Pr_Plane(
					new Vector3(corner2.X, corner1.Y, corner1.Z),
					corner2,
					StaticVectorLibrary.Right, face);
			}

			if( (FLAGS & FLAG_BACK) > 0 )
			{
				walls[i++] = engine.Pr_Plane(
					corner1,
					new Vector3(corner2.X, corner2.Y, corner1.Z),
					StaticVectorLibrary.Back, face);
			}

			if( (FLAGS & FLAG_FRONT) > 0 )
			{
				walls[i++] = engine.Pr_Plane(
					new Vector3(corner1.X, corner1.Y, corner2.Z),
					corner2,
					StaticVectorLibrary.Front, face);
			}

			if( (FLAGS & FLAG_BOTTOM) > 0 )
			{
				walls[i++] = engine.Pr_Plane(
					corner1,
					new Vector3(corner2.X, corner1.Y, corner2.Z),
					StaticVectorLibrary.Bottom, face);
			}

			if( (FLAGS & FLAG_TOP) > 0 )
			{
				walls[i++] = engine.Pr_Plane(
					new Vector3(corner1.X, corner2.Y, corner1.Z),
					corner2,
					StaticVectorLibrary.Top, face);
			}

			#endregion
		}

		public override void Render()
		{
			for(int i=0; i< walls.Length; i++) 
			{
				walls[i].Render();
			} // End of FOR
		}

		public void Dispose()
		{
			for(int i = 0; i < walls.Length; i++)
			{
				if(walls[i] != null) walls[i].Dispose();
			}
		}
	}

	/// <summary>
	/// Symbol class.
	/// </summary>
	class Symbol : IPeriod, IGameObject
	{
		protected Vector3 position;
		protected Element element;
		protected float Angle;
		protected string mesh;
		protected Color sparkling;
		protected float range;
		protected Light light;

		protected static float speed = 0.01f, limit = (float)(Math.PI * 2.0);

		public Symbol(Vector3 position, string mesh, Color sparkling, float range)
		{
			this.position = position;
			this.mesh = mesh;
			this.sparkling = sparkling;
		}

		/// <summary>
		/// Serializable data class for symbol.
		/// </summary>
		[Serializable]
		protected class SymbolData : GameObjectData
		{
			public float angle;
			public string mesh;
			public Vector3 position;
			public Color sparkling;
			public float range;

			public SymbolData()
			{
				description = "Symbol";
			}

			public override object create()
			{
				return new Symbol(this);
			}

		}

		public Symbol(GameObjectData data)
		{
			SymbolData d = data as SymbolData;
			this.Angle = d.angle;
			this.mesh = d.mesh;
			this.position = d.position;
			this.sparkling = d.sparkling;
			this.range = d.range;
		}

		public virtual GameObjectData getData()
		{
			SymbolData ret = new SymbolData();
			ret.angle = this.Angle;
			ret.mesh = this.mesh;
			ret.position = this.position;
			ret.sparkling = this.sparkling;
			ret.range = this.range;
			return ret;
		}

		#region IPeriod Members

		public void Period()
		{
			Angle += 0.005f;
			if(Angle > limit) Angle -= limit;
		}

		#endregion

		#region IGameObject Members

		public virtual void Build(Engine engine)
		{
			element = Library.Meshes().getMesh(mesh, sparkling);
			light = engine.GetPointLight(range, sparkling, position);
		}

		#endregion

		#region IRenderObject Members

		public void Render()
		{
			element.Render(position, 0f, Angle, 0f);
		}

		#endregion

		#region IGameObject Members

		public void PreRender()
		{
			light.Activate();
		}

		public void PostRender()
		{
			light.DeActivate();
		}

		#endregion
	}

	/// <summary>
	/// Static Mesh Object.
	/// </summary>
	class MeshMapObject : MapObject
	{
		protected Element element;
		protected float Angle;
		protected string mesh;

		public MeshMapObject(string mesh, Vector3 position)
			: base(position, position)
		{
			this.position = position;
			this.Angle = 0f;
			this.mesh = mesh;
		}

		/// <summary>
		/// Serializable data class for Mesh Map Object.
		/// </summary>
		[Serializable]
		protected class MeshMapObjectData : GameObjectData
		{
			public float angle;
			public Vector3 position;
			public string mesh;

			public MeshMapObjectData()
			{
				description = "Mesh Map Object";
			}

			public override object create()
			{
				return new MeshMapObject(this);
			}

		}

		public MeshMapObject(GameObjectData data) 
			: base(new Vector3(), new Vector3())
		{
			MeshMapObjectData d = data as MeshMapObjectData;
			this.Angle = d.angle;
			this.position = d.position;
			this.mesh = d.mesh;
			element = Library.Meshes().getMesh(mesh);
		}

		public override GameObjectData getData()
		{
			MeshMapObjectData ret = new MeshMapObjectData();
			ret.angle = this.Angle;
			ret.mesh = this.mesh;
			ret.position = this.position;
			return ret;
		}

		public override void Build(Engine engine)
		{
			element = Library.Meshes().getMesh(mesh);
		}


		public override void Render()
		{
			element.Render(position, 0f, Angle, 0f);
		}
	}

}