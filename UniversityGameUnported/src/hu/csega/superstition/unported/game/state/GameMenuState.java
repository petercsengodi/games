package hu.csega.superstition.game.state;

import hu.csega.superstition.game.menu.MainMenu;
import hu.csega.superstition.game.menu.MainMenuSelection;
import hu.csega.superstition.game.menu.TriggerParams;
import hu.csega.superstition.gamelib.network.HostData;
import hu.csega.superstition.gamelib.network.MapBuffer;
import hu.csega.superstition.gamelib.network.UserInfo;
import hu.csega.superstition.unported.game.IModel;
import hu.csega.superstition.unported.game.Model;
import hu.csega.superstition.unported.game.ModelIO;

class GameMenuState extends State {

	protected QuitState quit;
	protected PlayState play;
	protected LoadState load;
	protected Engine engine;
	protected Model gameModel;
	protected String filename;
	protected Network.NetHost host;
	protected IPAddress address;
	protected HostData host_data;
	protected UserInfo user;
	protected MapBuffer map;
	protected MainMenu_State mstate;
	protected StateControl cstate;

	public GameMenu_State(Engine engine, Model gameModel, QuitState quit,
			PlayState play, LoadState load, MainMenu_State mstate,
			StateControl cstate) {

		this.quit = quit;
		this.play = play;
		this.load = load;
		this.mstate = mstate;
		this.cstate = cstate;
		this.engine = engine;
		this.gameModel = gameModel;
	}

	@Override
	public State trigger(Object Object)
	{
		super.trigger(Object);

		TriggerParams selection = (TriggerParams)Object;

		switch(selection.command) {
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
			gameModel.dispose();
			return quit;
		}

		return play;
	}

	@Override
	public void enter()
	{
		super.enter();
		IModel imodel = new MainMenu(engine, true, gameModel);
		imodel.Initialize(engine);
		model = imodel;
	}


	@Override
	public void exit()
	{
		super.exit();
		IModel imodel = model;
		model = null;
		imodel.dispose();
	}

	private void loading()
	{
		gameModel.dispose();
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
		gameModel.dispose();
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

		//		Thread.CurrentThread.Abort();
	}

	private void loadgame()
	{
		gameModel.dispose();
		ModelIO.LoadModelFromFile(gameModel, filename);
		gameModel.Initialize(engine);
		Thread.CurrentThread.Abort();
	}

	private void densemap()
	{
		gameModel.dispose();
		gameModel.DenseMap();
		gameModel.Initialize(engine);
		Thread.CurrentThread.Abort();
	}

	private void quitgame()
	{
		gameModel.dispose();
		cstate.trigger(mstate);
		Thread.CurrentThread.Abort();
	}


} // End of Game Menu State