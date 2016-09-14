package hu.csega.game.uncharted;

import hu.csega.game.adapters.swing.SwingGameAdapter;
import hu.csega.game.engine.GameAdapter;
import hu.csega.game.engine.GameDescriptor;
import hu.csega.game.engine.GameEngine;
import hu.csega.game.engine.GameField;
import hu.csega.game.engine.GameImplementation;
import hu.csega.game.uncharted.objects.UnchartedPlayer;
import hu.csega.game.uncharted.play.UnchartedPhysics;
import hu.csega.game.uncharted.play.UnchartedRendering;
import hu.csega.game.uncharted.play.UnchartedRenderingOptions;

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
		UnchartedRendering rendering = new UnchartedRendering(physics, options);

		UnchartedPlayer player = new UnchartedPlayer(new GameField(-399, 399, -299, 299));
		physics.player = player;

		GameEngine.start(descriptor, adapter, implementation, physics, rendering);
	}

}
