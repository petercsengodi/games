package hu.csega.superstition.t3dcreator.operations;

public abstract class Operation
{
	private bool done = false;
	public bool Done
	{
		get{ return done; }
	}

	public Operation()
	{
	}

	public void Transform()
	{
		if(done) return;
		OnTransform();
		done = true;
	}

	public abstract void OnTransform();

	public void Invert()
	{
		if(!done) return;
		OnInvert();
		done = false;
	}

	public abstract void OnInvert();

}
