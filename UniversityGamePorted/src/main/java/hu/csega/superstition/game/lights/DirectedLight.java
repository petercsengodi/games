package hu.csega.superstition.game.lights;

import org.joml.Vector3f;

import hu.csega.superstition.game.Engine;

public class DirectedLight extends Light {

	private Color color;
	private Vector3f direction;

	/// <summary>
	/// Direction of the light
	/// </summary>
	public Vector3f Direction
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
	/// A position for the light. Used for
	/// generating shadow volumes.
	/// </summary>
	public Vector3f Position
	{
		get
		{
			Vector3f ret = direction;
			ret.Normalize();
			return -20f * ret;
		}

		set
		{
			Vector3f ret = value;
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
	public DirectedLight(Engine _engine, Color _color, Vector3f _direction)
	{
		engine = _engine;
		color = _color;
		direction = _direction;
	}

	@Override
	protected void SetParameters()
	{
		engine.Device.Lights[index].Diffuse = color;
		engine.Device.Lights[index].Type = LightType.Directional;
		engine.Device.Lights[index].Direction = direction;
	}
}