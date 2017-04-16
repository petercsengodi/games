package hu.csega.pixel.fighter;

import hu.csega.game.adapters.swing.SwingGameAdapter;
import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameDescriptor;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameImplementation;
import hu.csega.games.engine.GameObject;

public class PixelFighterMain implements GameImplementation {

	public static void main(String[] args) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("pixelFighter");
		descriptor.setTitle("Pixel Fighter");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Fighter game based on pixel graphics.");

		GameAdapter adapter = new SwingGameAdapter();

		PixelFighterMain implementation = new PixelFighterMain();
		PixelFighterPhysics physics = new PixelFighterPhysics();
		PixelFighterRendering rendering = new PixelFighterRendering(physics);

		physics.setPlayer(new GameObject());

		GameEngine.start(descriptor, adapter, implementation, physics, rendering);
	}

}
