package hu.csega.game.adapters.swing;

import javax.swing.JFrame;

import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameControl;
import hu.csega.games.engine.GameEngine;

public class SwingGameAdapter implements GameAdapter {

	@Override
	public GameControl createWindow(GameEngine engine) {
		SwingFrame frame = new SwingFrame(engine.getDescriptor().getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		canvas = new SwingCanvas(engine);
		frame.getContentPane().add(canvas);

		frame.pack();
		frame.setVisible(true);

		frame.start(engine, canvas);

		return frame.swingControl;
	}

	private SwingCanvas canvas;
}
