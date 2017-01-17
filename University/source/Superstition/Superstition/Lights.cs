using Microsoft.DirectX;
using Microsoft.DirectX.Direct3D;
using System.Drawing;

namespace Engine
{
	/// <summary>
	/// Base class for lights.
	/// </summary>
	public abstract class Light
	{
		protected Engine engine;
		protected int index;
		protected bool activated = false;

		/// <summary>
		/// Sets up light parameters. Called from Activate().
		/// </summary>
		protected abstract void SetParameters();

		/// <summary>
		/// Activates light for next renderings. There must
		/// be a free light slot.
		/// </summary>
		/// <returns>True, if activated.</returns>
		public bool Activate()
		{
			if(activated) return false;
			if((index = engine.RegisterLight(this)) == -1) return false;
			SetParameters();
			engine.Device.Lights[index].Enabled = true;
			engine.Device.Lights[index].Update();
			return (activated = true);
		}

		/// <summary>
		/// Deactivates light for next renderings.
		/// </summary>
		/// <returns>True, if deactivated.</returns>
		public bool DeActivate()
		{
			if(!activated) return false;
			if(index == -1) return false;
			engine.Device.Lights[index].Enabled = false;
			engine.Device.Lights[index].Update();
			activated = false;
			return engine.UnRegisterLight(this, index);
		}
	}

	/// <summary>
	/// Point light class.
	/// </summary>
	public class PointLight : Light
	{
		private float range;
		private Color color;
		private Vector3 position;

		/// <summary>
		/// Position of the light.
		/// </summary>
		public Vector3 Position
		{
			get { return position; }
			
			set 
			{
				position = value; 
				if(index > -1)
				{
					engine.Device.Lights[index].Position = value; 
					engine.Device.Lights[index].Update();
				}
			}
		}
		
		/// <summary>
		/// 
		/// </summary>
		/// <param name="_engine">Engine.</param>
		/// <param name="_range">Range of the light.</param>
		/// <param name="_color">Color of the light.</param>
		/// <param name="_position">Position of the lightsource.</param>
		public PointLight(Engine _engine, float _range, Color _color, Vector3 _position)
		{
			engine = _engine;
			range = _range;
			color = _color;
			position = _position;
		}
		
		protected override void SetParameters()
		{
			engine.Device.Lights[index].Diffuse = color;
			engine.Device.Lights[index].Type = LightType.Point;
			engine.Device.Lights[index].Position = position;
			engine.Device.Lights[index].Range = range;
			engine.Device.Lights[index].Attenuation0 = 0f;
			engine.Device.Lights[index].Attenuation1 = 0.1f;
			engine.Device.Lights[index].Attenuation2 = 0.1f; // 0.25f
		}
	}


	/// <summary>
	/// Directional light class.
	/// </summary>
	public class DirectedLight : Light
	{
		private Color color;
		private Vector3 direction;

		/// <summary>
		/// Direction of the light
		/// </summary>
		public Vector3 Direction
		{
			get { return direction; }
			
			set 
			{
				direction = value;
				if(index > -1)
				{
					engine.Device.Lights[index].Direction = value; 
					engine.Device.Lights[index].Update();
				}
			}
		}
		
		/// <summary>
		/// A virtual position for the light. Used for
		/// generating shadow volumes.
		/// </summary>
		public Vector3 Position
		{
			get 
			{ 
				Vector3 ret = direction;
				ret.Normalize();
				return -20f * ret; 
			}
			
			set 
			{
				Vector3 ret = value;
				ret.Normalize();
				direction = 1f * ret; 
				if(index > -1)
				{
					engine.Device.Lights[index].Direction = value; 
					engine.Device.Lights[index].Update();
				}
			}
		}

		/// <summary>
		/// 
		/// </summary>
		/// <param name="_engine">Engine.</param>
		/// <param name="_color">Color of the light.</param>
		/// <param name="_direction">Direction of the rays.</param>
		public DirectedLight(Engine _engine, Color _color, Vector3 _direction)
		{
			engine = _engine;
			color = _color;
			direction = _direction;
		}
		
		protected override void SetParameters()
		{
			engine.Device.Lights[index].Diffuse = color;
			engine.Device.Lights[index].Type = LightType.Directional;
			engine.Device.Lights[index].Direction = direction;
		}
	}
}