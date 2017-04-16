package hu.csega.superstition.game.lights;

import org.joml.Vector3f;

public class PointLight extends Light {

	private float range;
	private Color color;
	private Vector3f position;

	/// <summary>
	/// Position of the light.
	/// </summary>
	public Vector3f Position
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
	public PointLight(Engine _engine, float _range, Color _color, Vector3f _position)
	{
		engine = _engine;
		range = _range;
		color = _color;
		position = _position;
	}

	@Override
	protected void SetParameters()
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
