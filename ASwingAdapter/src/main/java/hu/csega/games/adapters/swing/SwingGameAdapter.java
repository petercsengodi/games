package hu.csega.games.adapters.swing;

import java.awt.Component;

import javax.swing.JFrame;

import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameControl;
import hu.csega.games.engine.GameEngine;

public class SwingGameAdapter implements GameAdapter {

	private SwingCanvas canvas;

	@Override
	public GameControl createWindow(GameEngine engine) {
		SwingFrame frame = new SwingFrame(engine.getDescriptor().getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		canvas = (SwingCanvas)createCanvas(engine);
		frame.getContentPane().add(canvas);

		frame.pack();
		frame.setVisible(true);

		frame.start(engine, canvas);

		return frame.swingControl;
	}

	@Override
	public Component createCanvas(GameEngine engine) {
		return new SwingCanvas(engine);
	}
}
