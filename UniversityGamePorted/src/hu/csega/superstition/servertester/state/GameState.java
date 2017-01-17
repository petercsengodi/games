package hu.csega.superstition.servertester.state;

class GameState : State
{
	public override void enter()
	{
		base.enter();
		Tester.Instance.ReadOnly = true;
	}

	public override void exit()
	{
		base.exit();
		Tester.Instance.UdpDisconnect();
		Tester.Instance.ReadOnly = false;
	}


	public override State trigger(object Object)
	{
		base.trigger (Object);
		string trigger_string = Object as string;
		if((trigger_string == null) || (trigger_string.Length == 0))
			return this;

		if(trigger_string.Equals("disconnect")) return new DisconnectState();
		if(trigger_string.Equals("quit")) return new QuitState();
		return this;
	}

}