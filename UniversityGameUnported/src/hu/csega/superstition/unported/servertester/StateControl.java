package hu.csega.superstition.servertester;

public class StateControl
	{
		private State state;

		public StateControl()
		{
			state = new NoneState();
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