package hu.csega.game.asteroid;

import java.util.ArrayList;
import java.util.List;

import hu.csega.game.asteroid.engine.AsteroidGameInitStep;
import hu.csega.game.asteroid.engine.AsteroidGameModifyingStep;
import hu.csega.game.asteroid.engine.AsteroidGameRenderingOptions;
import hu.csega.game.asteroid.engine.AsteroidGameRenderingStep;
import hu.csega.game.asteroid.engine.AsteroidGameWindowWrapper;
import hu.csega.game.asteroid.model.AsteroidGameModel;
import hu.csega.game.asteroid.view.AsteroidGameView;
import hu.csega.games.adapters.opengl.OpenGLGameAdapter;
import hu.csega.games.engine.impl.GameControlImpl;
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

@Tool(name = "Asteroid Game")
public class AsteroidGameToolImpl extends AbstractTool implements AsteroidGameTool {

	private List<GameWindowListener> listeners = new ArrayList<>();

	@Override
	public String getTitle() {
		return "Asteroid Game";
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
		descriptor.setId("asteroid");
		descriptor.setTitle(getTitle());
		descriptor.setVersion("v00.00.0003");
		descriptor.setDescription("Asteroid game.");

		GameAdapter adapter = new OpenGLGameAdapter();

		AsteroidGameRenderingOptions options = new AsteroidGameRenderingOptions();
		options.renderHitShapes = true;

		GameEngine engine = GameEngine.create(descriptor, adapter);
		engine.step(GameEngineStep.INIT, new AsteroidGameInitStep());
		engine.step(GameEngineStep.MODIFY, new AsteroidGameModifyingStep());
		engine.step(GameEngineStep.RENDER, new AsteroidGameRenderingStep(options));

		GameControlImpl control = (GameControlImpl)engine.getControl(); // FIXME this is not nice
		AsteroidGameWindowWrapper wrapper = new AsteroidGameWindowWrapper(window, this, control);
		engine.startIn(wrapper);

		return engine;
	}

	private void createComponents() {
		registerComponent(this);

		AsteroidGameModel model = new AsteroidGameModel();
		registerComponent(AsteroidGameModel.class, model);

		AsteroidGameView view = new AsteroidGameView();
		view.setModel(model);
		registerComponent(AsteroidGameView.class, view);
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

	private static final Logger logger = LoggerFactory.createLogger(AsteroidGameToolImpl.class);
}
