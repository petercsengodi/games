package hu.csega.games.uncharted;

import hu.csega.games.adapters.swing.SwingGameAdapter;
import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameDescriptor;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameImplementation;
import hu.csega.games.uncharted.play.UnchartedPhysics;
import hu.csega.games.uncharted.play.UnchartedRendering;
import hu.csega.games.uncharted.play.UnchartedRenderingOptions;
import hu.csega.games.uncharted.play.UnchartedUniverse;

public class UnchartedMain implements GameImplementation {

	public static void main(String[] args) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("uncharted");
		descriptor.setTitle("Uncharted");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Plain \"shoot'em all\" game with randomly generated maps.");

		GameAdapter adapter = new SwingGameAdapter();

		UnchartedRenderingOptions options = new UnchartedRenderingOptions();
		options.renderHitShapes = true;

		UnchartedMain implementation = new UnchartedMain();
		UnchartedPhysics physics = new UnchartedPhysics();
		UnchartedRendering rendering = new UnchartedRendering(options);

		UnchartedUniverse universe = new UnchartedUniverse();
		universe.init();
		physics.universe = universe;
		rendering.universe = universe;

		GameEngine engine = GameEngine.create(descriptor, adapter, implementation, physics, rendering);
		engine.startInNewWindow();
	}

}