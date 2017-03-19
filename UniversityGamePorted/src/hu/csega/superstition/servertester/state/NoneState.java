package hu.csega.superstition.servertester.state;

class NoneState : State
{
	public State trigger(Object Object)
	{
		base.trigger(Object);
		String trigger_string = Object as string;
		if((trigger_string == null) || (trigger_string.Length == 0))
			return this;

		if(trigger_string.Equals("connect"))
			if(Tester.Instance.TcpConnect())
				return new ConnectState();
			else return this;

		if(trigger_string.Equals("quit")) return new QuitState();
		return this;
	}

}