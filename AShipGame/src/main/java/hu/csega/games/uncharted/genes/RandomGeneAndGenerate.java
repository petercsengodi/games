package hu.csega.games.uncharted.genes;

import hu.csega.games.adapters.swing.SwingGameAdapter;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g2d.GameColor;
import hu.csega.games.engine.g2d.GamePoint;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameDescriptor;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.games.engine.intf.GameGraphics;

public class RandomGeneAndGenerate {

	public static void main(String[] args) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("genes");
		descriptor.setTitle("Rendering Random Genes");
		descriptor.setVersion("v00.00.0002");
		descriptor.setDescription("Create a random gene, and generates a monster out of it.");

		GameAdapter adapter = new SwingGameAdapter();

		final RandomGeneAndGenerate implementation = new RandomGeneAndGenerate();

		GameEngine engine = GameEngine.create(descriptor, adapter);

		engine.step(GameEngineStep.RENDER, new GameEngineCallback() {

			@Override
			public Object call(GameEngineFacade facade) {
				implementation.render(facade);
				return facade;
			}
		});

		engine.startInNewWindow();
	}

	public void render(GameEngineFacade facade) {
		GameGraphics g = facade.graphics();
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

}
