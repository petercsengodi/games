package hu.csega.games.engine;

public class GameEngine {

	public static void start(GameDescriptor descriptor,
			GameAdapter adapter, GameImplementation implementation,
			GamePhysics physics, GameRendering rendering) {

		GameEngine engine = new GameEngine();
		engine.descriptor = descriptor;
		engine.adapter = adapter;
		engine.implementation = implementation;
		engine.physics = physics;
		engine.rendering = rendering;

		engine.control = adapter.createWindow(engine);

		engine.physics.setGameControl(engine.control);

	}

	public GamePhysics getPhysics() {
		return physics;
	}

	public GameRendering getRendering() {
		return rendering;
	}

	public GameDescriptor getDescriptor() {
		return descriptor;
	}

	private GameEngine() {
	}

	private GameDescriptor descriptor;
	private GameAdapter adapter;
	private GameImplementation implementation;
	private GamePhysics physics;
	private GameRendering rendering;

	private GameControl control;
}
