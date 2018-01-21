package hu.csega.game.car;

import java.util.ArrayList;
import java.util.List;

import hu.csega.game.car.engine.CarGameInitStep;
import hu.csega.game.car.engine.CarGameModifyingStep;
import hu.csega.game.car.engine.CarGameRenderingOptions;
import hu.csega.game.car.engine.CarGameRenderingStep;
import hu.csega.game.car.engine.CarGameWindowWrapper;
import hu.csega.game.car.model.CarGameModel;
import hu.csega.game.car.view.CarGameView;
import hu.csega.games.adapters.opengl.OpenGLGameAdapter;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameDescriptor;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.games.engine.intf.GameWindowListener;
import hu.csega.toolshed.framework.Tool;
import hu.csega.toolshed.framework.ToolWindow;
import hu.csega.toolshed.framework.impl.AbstractTool;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

@Tool(name = "Car Game")
public class CarGameToolImpl extends AbstractTool implements CarGameTool {

	private List<GameWindowListener> listeners = new ArrayList<>();

	@Override
	public String getTitle() {
		return "Car Game";
	}

	@Override
	public void initialize(ToolWindow window) {
		logger.info("Tool initialization started.");

		createComponents();

		startGameEngine(window);

		logger.info("Tool initialization finished.");
	}

	private GameEngine startGameEngine(ToolWindow window) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("car");
		descriptor.setTitle(getTitle());
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Car game.");

		GameAdapter adapter = new OpenGLGameAdapter();

		CarGameRenderingOptions options = new CarGameRenderingOptions();
		options.renderHitShapes = true;

		GameEngine engine = GameEngine.create(descriptor, adapter);
		engine.step(GameEngineStep.INIT, new CarGameInitStep());
		engine.step(GameEngineStep.MODIFY, new CarGameModifyingStep());
		engine.step(GameEngineStep.RENDER, new CarGameRenderingStep(options));

		CarGameWindowWrapper wrapper = new CarGameWindowWrapper(window, this);
		engine.startIn(wrapper);

		return engine;
	}

	private void createComponents() {
		registerComponent(this);

		CarGameModel model = new CarGameModel();
		registerComponent(CarGameModel.class, model);

		CarGameView view = new CarGameView();
		view.setModel(model);
		registerComponent(CarGameView.class, view);
	}


	@Override
	public void finishWork() {
		logger.info("Start finalizing.");
		super.finishWork();

		for(GameWindowListener listener : listeners)
			listener.onFinishingWork();

		logger.info("Finalizing done.");
	}

	public void registerGameWindowListener(GameWindowListener listener) {
		listeners.add(listener);
	}

	private static final Logger logger = LoggerFactory.createLogger(CarGameToolImpl.class);
}
