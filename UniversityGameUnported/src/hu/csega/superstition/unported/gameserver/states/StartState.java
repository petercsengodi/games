package hu.csega.superstition.gameserver.states;

import hu.csega.superstition.gameserver.Server;

public class StartState extends State {

	@Override
	public void enter()
	{
		super.enter();
		Server.Instance.Start();
		Server.Instance.ReadOnly = true;
	}

	@Override
	public void exit()
	{
		super.exit();
		Server.Instance.Stop();
		Server.Instance.ReadOnly = false;
	}

	@Override
	public State trigger(Object Object)
	{
		super.trigger(Object);
		String trigger_string = Object as string;
		if((trigger_string == null) || (trigger_string.Length == 0))
			return this;
		if(trigger_string.Equals("stop")) return new StopState();
		if(trigger_string.Equals("quit")) return new QuitState();
		return this;
	}

}