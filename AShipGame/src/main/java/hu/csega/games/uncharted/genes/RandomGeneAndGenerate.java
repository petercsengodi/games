package hu.csega.games.uncharted.genes;

import hu.csega.games.adapters.swing.SwingGameAdapter;
import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameControl;
import hu.csega.games.engine.GameDescriptor;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameGraphics;
import hu.csega.games.engine.GameImplementation;
import hu.csega.games.engine.GamePhysics;
import hu.csega.games.engine.GameRendering;
import hu.csega.games.engine.g2d.GameColor;
import hu.csega.games.engine.g2d.GamePoint;

public class RandomGeneAndGenerate implements GameImplementation, GamePhysics, GameRendering {

	public static void main(String[] args) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("genes");
		descriptor.setTitle("Rendering Random Genes");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Create a random gene, and generates a monster out of it.");

		GameAdapter adapter = new SwingGameAdapter();

		RandomGeneAndGenerate implementation = new RandomGeneAndGenerate();

		GameEngine engine = GameEngine.create(descriptor, adapter, implementation, implementation, implementation);
		engine.startInNewWindow();
	}

	@Override
	public void render(GameGraphics g) {
		GameColor red = new GameColor(255, 0, 0);
		GameColor blue = new GameColor(0, 0, 255);

		g.drawTriangles(
				new GameColor[] { red, blue },
				new GamePoint[] {
						new GamePoint(0, 0),
						new GamePoint(100, 0),
						new GamePoint(0, 100),
						new GamePoint(0, 0),
						new GamePoint(-100, 0),
						new GamePoint(0, -100)
				}
			);
	}

	@Override
	public void call(long nanoTimeNow, long nanoTimeLastTime) {
	}

	@Override
	public void setGameControl(GameControl control) {
	}

}
