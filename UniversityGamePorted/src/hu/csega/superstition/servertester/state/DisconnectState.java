package hu.csega.superstition.servertester.state;

class DisconnectState : State
{
	public State trigger(object Object)
	{
		base.trigger(Object);
		string trigger_string = Object as string;
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