package hu.csega.game.uncharted.genes;

import hu.csega.game.adapters.swing.SwingGameAdapter;
import hu.csega.game.engine.GameAdapter;
import hu.csega.game.engine.GameColor;
import hu.csega.game.engine.GameControl;
import hu.csega.game.engine.GameDescriptor;
import hu.csega.game.engine.GameEngine;
import hu.csega.game.engine.GameGraphics;
import hu.csega.game.engine.GameImplementation;
import hu.csega.game.engine.GamePhysics;
import hu.csega.game.engine.GamePoint;
import hu.csega.game.engine.GameRendering;

public class RandomGeneAndGenerate implements GameImplementation, GamePhysics, GameRendering {

	public static void main(String[] args) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("genes");
		descriptor.setTitle("Rendering Random Genes");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Create a random gene, and generates a monster out of it.");

		GameAdapter adapter = new SwingGameAdapter();

		RandomGeneAndGenerate implementation = new RandomGeneAndGenerate();
		GameEngine.start(descriptor, adapter, implementation, implementation, implementation);
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
