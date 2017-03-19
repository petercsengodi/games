package hu.csega.superstition.servertester.state;

class GameState : State
{
	public void enter()
	{
		base.enter();
		Tester.Instance.ReadOnly = true;
	}

	public void exit()
	{
		base.exit();
		Tester.Instance.UdpDisconnect();
		Tester.Instance.ReadOnly = false;
	}


	public State trigger(Object Object)
	{
		base.trigger (Object);
		String trigger_string = Object as string;
		if((trigger_string == null) || (trigger_string.Length == 0))
			return this;

		if(trigger_string.Equals("disconnect")) return new DisconnectState();
		if(trigger_string.Equals("quit")) return new QuitState();
		return this;
	}

}