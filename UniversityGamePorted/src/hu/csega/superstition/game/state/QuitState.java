package hu.csega.superstition.game.state;

class QuitState extends State
{
	protected Form main;

	public Quit_State(Form main)
	{
		this.main = main;
	}

	@Override
	public void enter()
	{
		super.enter();
		(new Thread(new ThreadStart(main.Close))).Start();
	}


} // End of Quit State