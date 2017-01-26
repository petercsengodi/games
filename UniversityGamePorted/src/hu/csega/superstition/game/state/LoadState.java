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

	public override State trigger(object Object)
	{
		if(Object == null) return play;
		else return (State)Object;
	}

	//	public override void enter()
	//	{
	//		base.enter();
	//		IModel imodel = new LoadingTitle();
	//		imodel.Initialize(engine);
	//		model = imodel;
	//	}
	//
	//
	//	public override void exit()
	//	{
	//		base.exit();
	//		IModel imodel = model;
	//		model = null;
	//		imodel.Dispose();
	//	}

} // End of Load State