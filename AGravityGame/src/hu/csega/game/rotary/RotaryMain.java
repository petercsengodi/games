package hu.csega.game.rotary;

import hu.csega.game.adapters.swing.SwingGameAdapter;
import hu.csega.game.rotary.play.RotaryPhysics;
import hu.csega.game.rotary.play.RotaryRendering;
import hu.csega.game.rotary.play.RotaryRenderingOptions;
import hu.csega.game.rotary.play.RotaryUniverse;
import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameDescriptor;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameImplementation;

public class RotaryMain implements GameImplementation {

	public static void main(String[] args) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("rotary");
		descriptor.setTitle("Rotary");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Gravitational pull direction for player can be changed dynamically.");

		GameAdapter adapter = new SwingGameAdapter();

		RotaryRenderingOptions options = new RotaryRenderingOptions();
		options.renderHitShapes = true;

		RotaryMain implementation = new RotaryMain();
		RotaryPhysics physics = new RotaryPhysics();
		RotaryRendering rendering = new RotaryRendering(options);

		RotaryUniverse universe = new RotaryUniverse();
		universe.init();
		physics.universe = universe;
		rendering.universe = universe;

		GameEngine.start(descriptor, adapter, implementation, physics, rendering);
	}

}
