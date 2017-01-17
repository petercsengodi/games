package hu.csega.superstition.game.state;

class MainMenuState : State
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