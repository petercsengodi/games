namespace StateControl
{
	using ServerTester;

	public delegate void Action();

	public class StateControl
	{
		private State state;

		public StateControl()
		{
			state = new NoneState();
			if(state != null) state.enter();
		}

		public void trigger(object Object)
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

	abstract class State
	{
		public State()
		{
			// Default initialization
		}

		public virtual State trigger(object Object)
		{
			if(Object == null) Object = "<null>";
			System.Console.WriteLine("> Trigger of {0} : {1}", this.ToString(), Object.ToString());
			return this;
		}

		public virtual void enter()
		{
			System.Console.WriteLine("> Entering {0}", this.ToString());
		}

		public virtual void exit()
		{
			System.Console.WriteLine("> Exiting {0}", this.ToString());
		}

		public virtual void stay()
		{
			System.Console.WriteLine("> Staying {0}", this.ToString());
		}

	} // End of base class State

	class NoneState : State
	{
		public override State trigger(object Object)
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

	class ConnectState : State
	{
		public override void enter()
		{
			base.enter();
			Tester.Instance.ReadOnly = true;
		}

		public override void exit()
		{
			base.exit();
			Tester.Instance.TcpDisconnect();
			Tester.Instance.ReadOnly = false;
		}

		public override State trigger(object Object)
		{
			base.trigger(Object);
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

	class DisconnectState : State
	{
		public override State trigger(object Object)
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

	class QuitState : State
	{
		public override void enter()
		{
			base.enter();
			Tester.Instance.ReadOnly = true;
		}
	}
}
