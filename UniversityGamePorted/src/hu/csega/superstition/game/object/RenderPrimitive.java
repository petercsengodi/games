package hu.csega.superstition.game.object;

public class RenderPrimitive implements IRenderObject, IDisposable
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

	@Override
	public void Render()
	{
		primitive.Render();
	}

	public void Dispose()
	{
		if(needToDispose) primitive.Dispose();
	}

}