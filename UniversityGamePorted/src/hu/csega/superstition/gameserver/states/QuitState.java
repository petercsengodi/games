package hu.csega.superstition.gameserver.states;

class QuitState : State
{
	public override void enter()
	{
		base.enter();
		Server.Instance.ReadOnly = true;
	}

}
