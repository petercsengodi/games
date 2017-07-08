package hu.csega.superstition.gameserver.states;

public class QuitState extends State
{
	@Override
	public void enter()
	{
		super.enter();
		Server.Instance.ReadOnly = true;
	}

}
