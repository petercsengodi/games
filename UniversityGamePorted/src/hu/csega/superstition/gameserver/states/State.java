package hu.csega.superstition.gameserver.states;

abstract class State
{
	public State()
	{
		// Default initialization
	}

	public virtual State trigger(object Object)
	{
		if(Object == null) Object = "<null>";
		System.Console.WriteLine("> Trigger of {0} : {1}", this.ToString(), Object.ToString());
		return this;
	}

	public virtual void enter()
	{
		System.Console.WriteLine("> Entering {0}", this.ToString());
	}

	public virtual void exit()
	{
		System.Console.WriteLine("> Exiting {0}", this.ToString());
	}

	public virtual void stay()
	{
		System.Console.WriteLine("> Staying {0}", this.ToString());
	}

} // End of base class State
