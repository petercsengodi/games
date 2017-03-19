package hu.csega.superstition.servertester.state;

class ConnectState extends State
{
	@Override
	public void enter()
	{
		super.enter();
		Tester.Instance.ReadOnly = true;
	}

	@Override
	public void exit()
	{
		super.exit();
		Tester.Instance.TcpDisconnect();
		Tester.Instance.ReadOnly = false;
	}

	public State trigger(Object Object)
	{
		super.trigger(Object);
		string trigger_string = Object as string;
		if((trigger_string == null) || (trigger_string.Length == 0))
			return this;

		if(trigger_string.Equals("game"))
		{
			if(Tester.Instance.UdpConnect())
			{
				return new GameState();
			}
			else
			{
				return new DisconnectState();
			}
		}

		if(trigger_string.Equals("disconnect")) return new DisconnectState();
		if(trigger_string.Equals("quit")) return new QuitState();
		return this;
	}

}