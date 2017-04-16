package hu.csega.games.adapters.swing;

import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameCanvas;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameThread;
import hu.csega.games.engine.GameWindow;

public class SwingGameAdapter implements GameAdapter {

	@Override
	public GameWindow createWindow(GameEngine engine) {
		SwingFrame frame = new SwingFrame(engine);
		return frame;
	}

	@Override
	public GameCanvas createCanvas(GameEngine engine) {
		return new SwingCanvas(engine);
	}

	@Override
	public GameThread createThread(GameEngine engine) {
		return new SwingThread(engine.getPhysics(), engine.getCanvas());
	}
}
