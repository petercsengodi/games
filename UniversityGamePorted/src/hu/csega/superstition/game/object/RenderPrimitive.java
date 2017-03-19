package hu.csega.superstition.game.object;

public class RenderPrimitive implements IRenderObject, IDisposable
{
	protected Primitive primitive;
	protected boolean needToDispose = true;

	/// <summary>
	/// Whether this object has to dispose itself or not.
	/// </summary>
	public boolean NeedToDispose
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

	public void dispose()
	{
		if(needToDispose) primitive.Dispose();
	}

}