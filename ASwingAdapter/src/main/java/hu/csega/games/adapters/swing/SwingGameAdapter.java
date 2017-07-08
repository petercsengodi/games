package hu.csega.games.adapters.swing;

import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameWindow;

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
	public GameModelStore createStore(GameEngine engine) {
		return null;
	}
}
