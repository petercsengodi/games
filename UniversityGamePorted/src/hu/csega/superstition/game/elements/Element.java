package hu.csega.superstition.game.elements;

import org.joml.Vector3f;

public abstract class Element {

	protected Engine engine;
	private static Vector3 vup = new Vector3f(0f, 1f, 0f);
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