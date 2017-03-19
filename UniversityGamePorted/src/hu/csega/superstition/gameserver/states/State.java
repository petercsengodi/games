package hu.csega.superstition.gameserver.states;

public abstract class State
{
	public State()
	{
		// Default initialization
	}

	public State trigger(Object Object)
	{
		if(Object == null) Object = "<null>";
		System.Console.WriteLine("> Trigger of {0} : {1}", this.ToString(), Object.ToString());
		return this;
	}

	public void enter()
	{
		System.Console.WriteLine("> Entering {0}", this.ToString());
	}

	public void exit()
	{
		System.Console.WriteLine("> Exiting {0}", this.ToString());
	}

	public void stay()
	{
		System.Console.WriteLine("> Staying {0}", this.ToString());
	}

} // End of base class State
