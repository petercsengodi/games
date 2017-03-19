package hu.csega.superstition.gameserver.states;

public class StopState extends State
{

	@Override
	public State trigger(Object Object)
	{
		base.trigger(Object);
		String trigger_string = Object as string;
		if((trigger_string == null) || (trigger_string.Length == 0))
			return this;
		if(trigger_string.Equals("start")) return new StartState();
		if(trigger_string.Equals("quit")) return new QuitState();
		return this;
	}

}