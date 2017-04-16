package hu.csega.games.engine;

import java.awt.Component;

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

	/**
	 * @param keyTarget Where the KeyListener should be attached to.
	 * @return Graphic canvas.
	 */
	public static Component startCanvas(Component keyTarget, GameDescriptor descriptor,
			GameAdapter adapter, GameImplementation implementation,
			GamePhysics physics, GameRendering rendering) {

		GameEngine engine = new GameEngine();
		engine.descriptor = descriptor;
		engine.adapter = adapter;
		engine.implementation = implementation;
		engine.physics = physics;
		engine.rendering = rendering;

		Component canvas = adapter.createCanvas(engine);

		engine.control = adapter.createWindow(engine);

		engine.physics.setGameControl(engine.control);

		return canvas;
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
