using System;
using System.Drawing;
using Microsoft.DirectX;

namespace Model
{
	using Engine;
	using GameLib;

	/// <summary>
	/// Class for decorated lights.
	/// </summary>
	class DecoratedLight : MapObject
	{
		protected string meshFrame, meshGlow;
		protected Element frame, glow;
		protected Color color;
		protected Vector3 light;
		protected Light l;
		protected float angle, range;
		protected Engine engine;
        
		public DecoratedLight(Vector3 position, Vector3 corner1, Vector3 corner2,
			float angle)
			: base(position + corner1, position + corner2){
			this.angle = angle;
			this.position = position;
			
			Select();
		}
		
		public DecoratedLight(Vector3 position, Vector3 corner1, Vector3 corner2)
			: base(position + corner1, position + corner2)
		{
			this.angle = 0f;
			this.position = position;
			
			Select();
		}

		/// <summary>
		/// Selects object kind.
		/// </summary>
		protected void Select()
		{
			this.meshFrame = "lamp1.x";
			this.meshGlow = "lamp2.x";
			this.color = Color.DarkRed;
			this.range = 20f;
			this.light = position;
			this.light.X += (float)(Math.Cos(angle) * 0.2);
			this.light.Z += (float)(Math.Sin(angle) * 0.2);
		}

		public override void Build(Engine engine)
		{
			this.engine = engine;
			frame = Library.Meshes().getMesh(meshFrame, false);
			glow = Library.Meshes().getMesh(meshGlow, false, color);
			l = engine.GetPointLight(range, color, light);
		}

		public override void PreRender()
		{
			l.Activate();
		}

		public override void PostRender()
		{
			l.DeActivate();
		}


		/// <summary>
		/// Returns light.
		/// </summary>
		public Light Light { get { return l; } }

		/// <summary>
		/// Serializable data class for Decorated Light.
		/// </summary>
		[Serializable]
		protected class DecoratedLightData : GameObjectData
		{
			public float angle, range;
			public Vector3 corner1, corner2, position;
			public string meshFrame, meshGlow;
			public Color color;
			public Vector3 light;

			public DecoratedLightData()
			{
				description = "Decorated Light";
			}

			public override object create()
			{
				return new DecoratedLight(this);
			}

		}

		public DecoratedLight(GameObjectData data)
			: base(new Vector3(), new Vector3())
		{
			DecoratedLightData d = data as DecoratedLightData;
			this.angle = d.angle;
			this.corner1 = d.corner1;
			this.corner2 = d.corner2;
			this.meshFrame = d.meshFrame;
			this.meshGlow = d.meshGlow;
			this.position = d.position;
			this.color = d.color;
            this.light = d.light;
			this.range = d.range;
		}

		public override GameObjectData getData()
		{
			DecoratedLightData ret = new DecoratedLightData();
			ret.angle = this.angle;
			ret.corner1 = this.corner1;
			ret.corner2 = this.corner2;
			ret.meshFrame = this.meshFrame;
			ret.meshGlow = this.meshGlow;
			ret.position = this.position;
			ret.color = this.color;
			ret.light = this.light;
			ret.range = this.range;
			return ret;
		}

		public override void Render()
		{
			frame.Render(position, 0f, angle, 0f);
			glow.Render(position, 0f, angle, 0f);
		}

	}// End of Decorative Light

} // End of Namespace