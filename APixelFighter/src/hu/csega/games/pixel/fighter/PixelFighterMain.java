package hu.csega.games.pixel.fighter;

import hu.csega.games.adapters.swing.SwingGameAdapter;
import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameDescriptor;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameImplementation;
import hu.csega.games.engine.g2d.GameObject;

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

		GameEngine engine = GameEngine.create(descriptor, adapter, implementation, physics, rendering);
		engine.startInNewWindow();
	}

}
