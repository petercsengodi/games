package hu.csega.superstition.game.lights;

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