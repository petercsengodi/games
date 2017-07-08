package hu.csega.superstition.servertester.state;

class QuitState : State
{
	public void enter()
	{
		super.enter();
		Tester.Instance.ReadOnly = true;
	}
}