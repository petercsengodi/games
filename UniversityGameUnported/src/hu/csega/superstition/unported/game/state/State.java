package hu.csega.superstition.game.state;

abstract class State
{
	public IModel model;
	public IModel Model { get { return model; } }

	public Action entryAction, exitAction, innerAction;

	public State()
	{
		// Default initialization
		model = null;
		entryAction = null;
		exitAction = null;
		innerAction = null;
	}

	public State trigger(Object Object)
	{
		if(Object == null) Object = "<null>";
		MainFrame.WriteConsole("> Trigger of " +
			this.ToString() + " : " + Object.ToString());
		return this;
	}

	public void enter()
	{
		MainFrame.WriteConsole("> Entering " + this.ToString());
		if(entryAction != null) entryAction();
	}

	public void exit()
	{
		MainFrame.WriteConsole("> Exiting " + this.ToString());
		if(exitAction != null) exitAction();
	}

	public void stay()
	{
		MainFrame.WriteConsole("> Staying " + this.ToString());
		if(innerAction != null) innerAction();
	}

} // End of base class State