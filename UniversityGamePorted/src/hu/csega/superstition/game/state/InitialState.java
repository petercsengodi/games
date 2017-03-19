package hu.csega.superstition.game.state;

public class InitialState extends State
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

	public State trigger(Object Object)
	{
		super.trigger(Object);

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