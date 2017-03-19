package hu.csega.superstition.game.state;

class GameMenuState extends State
{
	protected Quit_State quit;
	protected Play_State play;
	protected Load_State load;
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

	public State trigger(Object Object)
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

	@Override
	public void enter()
	{
		base.enter();
		IModel imodel = new MainMenu(engine, true, gameModel);
		imodel.Initialize(engine);
		model = imodel;
	}


	@Override
	public void exit()
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

		//		Thread.CurrentThread.Abort();
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