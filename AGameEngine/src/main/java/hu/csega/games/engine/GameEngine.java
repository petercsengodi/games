package hu.csega.games.engine;

import hu.csega.games.engine.g2d.GameControlImpl;
import hu.csega.games.engine.impl.DefaultGameWindowListener;

public class GameEngine {

	public static GameEngine create(GameDescriptor descriptor,
			GameAdapter adapter, GameImplementation implementation,
			GamePhysics physics, GameRendering rendering) {

		GameEngine engine = new GameEngine();
		engine.descriptor = descriptor;
		engine.adapter = adapter;
		engine.implementation = implementation;
		engine.physics = physics;
		engine.rendering = rendering;
		engine.control = new GameControlImpl();
		return engine;
	}

	public void startInNewWindow() {
		GameWindow gameWindow = adapter.createWindow(this);
		startIn(gameWindow);
	}

	public void startIn(GameWindow gameWindow) {
		this.window = gameWindow;
		this.canvas = adapter.createCanvas(this);
		this.thread = adapter.createThread(this);

		thread.start();
		window.add(canvas);
		window.register(new DefaultGameWindowListener(this.thread));
		window.showWindow();
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

	public GameCanvas getCanvas() {
		return canvas;
	}

	public GameControl getControl() {
		return control;
	}

	private GameEngine() {
	}

	private GameDescriptor descriptor;
	private GameAdapter adapter;
	private GameImplementation implementation;
	private GamePhysics physics;
	private GameRendering rendering;
	private GameWindow window;
	private GameCanvas canvas;
	private GameThread thread;
	private GameControl control;
}
