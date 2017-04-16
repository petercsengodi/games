package hu.csega.superstition.game.state;

class PlayState extends State
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

	public State trigger(Object Object)
	{
		super.trigger(Object);
		if(Object == null) return menu;
		else
		{
			//			Monitor.Enter(engine);
			(new Thread(new ThreadStart(quitgame))).Start();
			return load;
			//			model.dispose();
			//			Monitor.Exit(model);
			//			return mstate;
		}
	}

	@Override
	public void enter()
	{
		super.enter();
		//		engine.Options.depth_algorythm =
		//			DepthAlgorythm.Pass;
		engine.Options.depth_algorythm =
				DepthAlgorythm.Fail;
	}

	@Override
	public void exit()
	{
		engine.Options.depth_algorythm =
				DepthAlgorythm.Pass;
		super.exit();
	}

	private void quitgame()
	{
		IModel imodel = model;
		model = null;
		imodel.dispose();
		//		Monitor.Exit(engine);
		cstate.trigger(mstate);
		Thread.CurrentThread.Abort();
	}



} // End of Play State