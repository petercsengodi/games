package hu.csega.superstition.game.state;

class LoadState extends State
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

	public State trigger(Object Object)
	{
		if(Object == null) return play;
		else return (State)Object;
	}

	//	public void enter()
	//	{
	//		super.enter();
	//		IModel imodel = new LoadingTitle();
	//		imodel.Initialize(engine);
	//		model = imodel;
	//	}
	//
	//
	//	public void exit()
	//	{
	//		super.exit();
	//		IModel imodel = model;
	//		model = null;
	//		imodel.Dispose();
	//	}

} // End of Load State