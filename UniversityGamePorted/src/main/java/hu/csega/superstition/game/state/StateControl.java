package hu.csega.superstition.game.state;

public class StateControl
{
	private State state;
	public IModel Model { get { return state.Model; } }

	public StateControl(Engine engine, Form main)
	{
		state = new Initial_State(engine, main, this);
		if(state != null) state.enter();
	}

	public void trigger(Object Object)
	{
		if(state == null) return;
		State new_state = state.trigger(Object);
		if(!new_state.Equals(state))
		{
			if(state != null) state.exit();
			state = new_state;
			if(state != null) state.enter();
		}
		else
		{
			if(state != null) state.stay();
		}
	}
} // End of class State Control