package hu.csega.superstition.servertester.state;

class QuitState : State
{
	public override void enter()
	{
		base.enter();
		Tester.Instance.ReadOnly = true;
	}
}