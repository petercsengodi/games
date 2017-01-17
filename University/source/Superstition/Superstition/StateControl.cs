using System.Threading;
using System.Windows;
using System.Windows.Forms;
using System.Drawing;
using System.Net;

namespace StateControl
{
	using Engine;
	using Menu;
	using Model;
	using GameLib;
	using Superstition;

	public delegate void Action();

	public class StateControl
	{
		private State state;
		public IModel Model { get { return state.Model; } }

		public StateControl(Engine engine, Form main)
		{
			state = new Initial_State(engine, main, this);
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
		public IModel model;
		public IModel Model { get { return model; } }

		public Action entryAction, exitAction, innerAction;

		public State()
		{
			// Default initialization
			model = null;
			entryAction = null;
			exitAction = null;
			innerAction = null;
		}

		public virtual State trigger(object Object)
		{
			if(Object == null) Object = "<null>";
			MainFrame.WriteConsole("> Trigger of " + 
				this.ToString() + " : " + Object.ToString());
			return this;
		}

		public virtual void enter()
		{
			MainFrame.WriteConsole("> Entering " + this.ToString());
			if(entryAction != null) entryAction();
		}

		public virtual void exit()
		{
			MainFrame.WriteConsole("> Exiting " + this.ToString());
			if(exitAction != null) exitAction();
		}

		public virtual void stay()
		{
			MainFrame.WriteConsole("> Staying " + this.ToString());
			if(innerAction != null) innerAction();
		}

	} // End of base class State

	class Play_State : State
	{
		protected GameMenu_State menu;
		protected MainMenu_State mstate;
		protected StateControl cstate;
		protected Load_State load;
		protected Engine engine;

		public Play_State(Engine engine, Model model)
		{
			this.model = model; 
			this.engine = engine;
		}

		public void setGameMenu(GameMenu_State menu,
			MainMenu_State mstate, StateControl cstate, 
			Load_State load)
		{
			this.menu = menu;
			this.load = load;
			this.mstate = mstate;
			this.cstate = cstate;
		}

		public override State trigger(object Object)
		{
			base.trigger(Object);
			if(Object == null) return menu;
			else 
			{
//				Monitor.Enter(engine);
				(new Thread(new ThreadStart(quitgame))).Start();
				return load;
//				model.Dispose();
//				Monitor.Exit(model);
//				return mstate;
			}
		}

		public override void enter()
		{
			base.enter();
//			engine.Options.depth_algorythm =
//				DepthAlgorythm.Pass;
			engine.Options.depth_algorythm =
				DepthAlgorythm.Fail;
		}

		public override void exit()
		{
			engine.Options.depth_algorythm =
				DepthAlgorythm.Pass;
			base.exit();
		}

		private void quitgame()
		{
			IModel imodel = model;
			model = null;
			imodel.Dispose();
//			Monitor.Exit(engine);
			cstate.trigger(mstate);
			Thread.CurrentThread.Abort();
		}



	} // End of Play State

	class Load_State : State
	{
		protected Play_State play;
		protected IModel gameModel;
		protected Engine engine;

		public Load_State(Engine engine, IModel gameModel)
		{
			this.engine = engine;
			this.gameModel = gameModel;
			IModel imodel = new LoadingTitle();
			imodel.Initialize(engine);
			model = imodel;
		}

		public void setPlayState(Play_State play)
		{
			this.play = play;
		}

		public override State trigger(object Object)
		{
			if(Object == null) return play;
			else return (State)Object;
		}

//		public override void enter()
//		{
//			base.enter();
//			IModel imodel = new LoadingTitle();
//			imodel.Initialize(engine);
//			model = imodel;
//		}
//
//
//		public override void exit()
//		{
//			base.exit();
//			IModel imodel = model;
//			model = null;
//			imodel.Dispose();
//		}

	} // End of Load State

	class MainMenu_State : State
	{
		protected Quit_State quit;
		protected Play_State play;
		protected Load_State load;
		protected Engine engine;
		protected Model gameModel;
		protected string filename;
		protected Network.NetHost host;
		protected HostData host_data;
		protected UserInfo user;
		protected MapBuffer map;
		protected IPAddress address;
		
		public MainMenu_State(Engine engine, Model gameModel, Quit_State quit, 
			Play_State play, Load_State load)
		{
			this.quit = quit;
			this.play = play;
			this.load = load;
			this.engine = engine;
			this.gameModel = gameModel;
		}

		public override State trigger(object Object)
		{
			base.trigger(Object);
			
			TriggerParams selection = (TriggerParams)Object;
			switch(selection.command)
			{
				case MainMenuSelection.LOAD_MAP:
					filename = selection.StringParameter;
					(new Thread(new ThreadStart(loading))).Start();
					return load;

				case MainMenuSelection.LOAD_GAME:
					filename = selection.StringParameter;
					(new Thread(new ThreadStart(loadgame))).Start();
					return load;

				case MainMenuSelection.DENSE_MAP:
					(new Thread(new ThreadStart(densemap))).Start();
					return load;

				case MainMenuSelection.JOIN_HOST:
					object[] array = (object[])selection.ObjectParameter;
					host = (Network.NetHost)array[0];
					address = (IPAddress)array[1];
					host_data = (HostData)array[2];
					user = (UserInfo)array[3];
					map = (MapBuffer)array[4];
					(new Thread(new ThreadStart(joinhost))).Start();
					return load;

				case MainMenuSelection.SAVE_GAME:
					return this;

				case MainMenuSelection.QUIT:
					return quit;
			}

			return this;
		}

		public override void enter()
		{
			base.enter();
			IModel imodel = new MainMenu(engine);
			imodel.Initialize(engine);
			model = imodel;
		}


		public override void exit()
		{
			base.exit();
			IModel imodel = model;
			model = null;
			imodel.Dispose();
		}

		private void loading()
		{
			gameModel.LoadXmlMap(filename);
			gameModel.Initialize(engine);
			Thread.CurrentThread.Abort();
		}

		private void joinhost()
		{
			gameModel.SetDataModel(
				ModelIO.ExtractModelData(map.map_buffer));
			gameModel.Initialize(engine);

			Network.PlayClient play_client = new Network.PlayClient(
				address, user.game_port, user.client_port, user.userID);
			gameModel.StartNetworkPlay(play_client, (int)user.userID);

			host = null;
			address = null;
			host_data = null;
			user = null;
			map = null;
			Thread.CurrentThread.Abort();
		}

		private void loadgame()
		{
			ModelIO.LoadModelFromFile(gameModel, filename);
			gameModel.Initialize(engine);
			Thread.CurrentThread.Abort();
		}

		private void densemap()
		{
			gameModel.DenseMap();
			gameModel.Initialize(engine);
			Thread.CurrentThread.Abort();
		}

	} // End of Main Menu State

	class GameMenu_State : State
	{
		protected Quit_State quit;
		protected Play_State play;
		protected Load_State load;
		protected Engine engine;
		protected Model gameModel;
		protected string filename;
		protected Network.NetHost host;
		protected IPAddress address;
		protected HostData host_data;
		protected UserInfo user;
		protected MapBuffer map;
		protected MainMenu_State mstate;
		protected StateControl cstate;
		
		public GameMenu_State(Engine engine, Model gameModel, Quit_State quit, 
			Play_State play, Load_State load, MainMenu_State mstate,
			StateControl cstate)
		{
			this.quit = quit;
			this.play = play;
			this.load = load;
			this.mstate = mstate;
			this.cstate = cstate;
			this.engine = engine;
			this.gameModel = gameModel;
		}

		public override State trigger(object Object)
		{
			base.trigger(Object);
			
			TriggerParams selection = (TriggerParams)Object;
			switch(selection.command)
			{
				case MainMenuSelection.LOAD_MAP:
					filename = selection.StringParameter;
					(new Thread(new ThreadStart(loading))).Start();
					return load;

				case MainMenuSelection.LOAD_GAME:
					filename = selection.StringParameter;
					(new Thread(new ThreadStart(loadgame))).Start();
					return load;

				case MainMenuSelection.DENSE_MAP:
					(new Thread(new ThreadStart(densemap))).Start();
					return load;

				case MainMenuSelection.QUIT_GAME:
					(new Thread(new ThreadStart(quitgame))).Start();
					return load;

				case MainMenuSelection.JOIN_HOST:
					object[] array = (object[])selection.ObjectParameter;
					host = (Network.NetHost)array[0];
					address = (IPAddress)array[1];
					host_data = (HostData)array[2];
					user = (UserInfo)array[3];
					map = (MapBuffer)array[4];
					(new Thread(new ThreadStart(joinhost))).Start();
					return load;

				case MainMenuSelection.PUBLISH_HOST:
					object[] array2 = (object[])selection.ObjectParameter;
					host = (Network.NetHost)array2[0];
					address = (IPAddress)array2[1];
					host_data = (HostData)array2[2];
					user = (UserInfo)array2[3];
					publishhost();
					return play;

				case MainMenuSelection.SAVE_GAME:
					filename = selection.StringParameter;
					ModelIO.SaveModelToFile(gameModel, filename);
					return play;

				case MainMenuSelection.QUIT:
					gameModel.Dispose();
					return quit;
			}

			return play;
		}

		public override void enter()
		{
			base.enter();
			IModel imodel = new MainMenu(engine, true, gameModel);
			imodel.Initialize(engine);
			model = imodel;
		}


		public override void exit()
		{
			base.exit();
			IModel imodel = model;
			model = null;
			imodel.Dispose();
		}

		private void loading()
		{
			gameModel.Dispose();
			gameModel.LoadXmlMap(filename);
			gameModel.Initialize(engine);
			host = null;
			host_data = null;
			user = null;
			map = null;
			Thread.CurrentThread.Abort();
		}

		private void joinhost()
		{
			gameModel.Dispose();
			gameModel.SetDataModel(
				ModelIO.ExtractModelData(map.map_buffer));
			gameModel.Initialize(engine);
			
			Network.PlayClient play_client = new Network.PlayClient(
				address, user.game_port, user.client_port, user.userID);
			gameModel.StartNetworkPlay(play_client, (int)user.userID);

			host = null;
			address = null;
			host_data = null;
			user = null;
			map = null;

			Thread.CurrentThread.Abort();
		}

		private void publishhost()
		{
			Network.PlayClient play_client = new Network.PlayClient(
				address, user.game_port, user.client_port, user.userID);
			gameModel.StartNetworkPlay(play_client, (int)user.userID);

			host = null;
			address = null;
			host_data = null;
			user = null;
			map = null;

//			Thread.CurrentThread.Abort();
		}

		private void loadgame()
		{
			gameModel.Dispose();
			ModelIO.LoadModelFromFile(gameModel, filename);
			gameModel.Initialize(engine);
			Thread.CurrentThread.Abort();
		}

		private void densemap()
		{
			gameModel.Dispose();
			gameModel.DenseMap();
			gameModel.Initialize(engine);
			Thread.CurrentThread.Abort();
		}

		private void quitgame()
		{
			gameModel.Dispose();
			cstate.trigger(mstate);
			Thread.CurrentThread.Abort();
		}


	} // End of Game Menu State

	class Initial_State : State
	{
		public Engine engine;
		public Quit_State quit;
		public StateControl cstate;
		
		public Initial_State(Engine engine, Form main, 
			StateControl cstate)
		{
			this.cstate = cstate;
			this.engine = engine;
			quit = new Quit_State(main);
		}

		public override State trigger(object Object)
		{
			base.trigger(Object);
			
			if(Object.Equals(false)) return quit;
			Model gameModel = new Model();
			Play_State play = new Play_State(engine, gameModel);
			Load_State loadState = new Load_State(engine, gameModel);
			MainMenu_State mainMenu = new MainMenu_State(engine,
				gameModel, quit, play, loadState);
			GameMenu_State gameMenu = new GameMenu_State(engine, 
				gameModel, quit, play, loadState, mainMenu, cstate);
			play.setGameMenu(gameMenu, mainMenu, cstate, loadState);
			loadState.setPlayState(play);
			return mainMenu;
		}

	} // End of Initial State

	class Quit_State : State
	{
		protected Form main;

		public Quit_State(Form main)
		{
			this.main = main;	
		}

		public override void enter()
		{
			base.enter();
			(new Thread(new ThreadStart(main.Close))).Start();
		}


	} // End of Quit State

}