package hu.csega.superstition.gameserver.states;

public class StopState extends State
{

	public override State trigger(object Object)
	{
		base.trigger(Object);
		string trigger_string = Object as string;
		if((trigger_string == null) || (trigger_string.Length == 0))
			return this;
		if(trigger_string.Equals("start")) return new StartState();
		if(trigger_string.Equals("quit")) return new QuitState();
		return this;
	}

}