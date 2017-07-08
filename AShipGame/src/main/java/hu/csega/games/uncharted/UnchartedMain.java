package hu.csega.games.uncharted;

import hu.csega.games.adapters.swing.SwingGameAdapter;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameDescriptor;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.games.uncharted.play.UnchartedModel;
import hu.csega.games.uncharted.play.UnchartedRenderingOptions;

public class UnchartedMain {

	public static void main(String[] args) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("uncharted");
		descriptor.setTitle("Uncharted");
		descriptor.setVersion("v00.00.0002");
		descriptor.setDescription("Plain \"shoot'em all\" game with randomly generated maps.");

		GameAdapter adapter = new SwingGameAdapter();

		UnchartedRenderingOptions options = new UnchartedRenderingOptions();
		options.renderHitShapes = true;

		final UnchartedModel model = new UnchartedModel(options);

		GameEngine engine = GameEngine.create(descriptor, adapter);

		engine.step(GameEngineStep.INIT, new GameEngineCallback() {

			@Override
			public Object call(GameEngineFacade facade) {
				model.init();
				return facade;
			}
		});

		engine.step(GameEngineStep.MODIFY, new GameEngineCallback() {

			@Override
			public Object call(GameEngineFacade facade) {
				model.call(facade);
				return facade;
			}
		});

		engine.step(GameEngineStep.RENDER, new GameEngineCallback() {

			@Override
			public Object call(GameEngineFacade facade) {
				model.render(facade);
				return facade;
			}
		});

		engine.startInNewWindow();
	}

}
