package hu.csega.game.asteroid;

import java.util.ArrayList;
import java.util.List;

import hu.csega.game.asteroid.engine.AsteroidGameField;
import hu.csega.game.asteroid.engine.AsteroidGamePhysics;
import hu.csega.game.asteroid.engine.AsteroidGameRendering;
import hu.csega.game.asteroid.engine.AsteroidGameRenderingOptions;
import hu.csega.game.asteroid.engine.AsteroidGameWindowWrapper;
import hu.csega.game.asteroid.model.AsteroidGameModel;
import hu.csega.game.asteroid.view.AsteroidGameView;
import hu.csega.games.adapters.opengl.OpenGLGameAdapter;
import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameDescriptor;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameImplementation;
import hu.csega.games.engine.GameWindowListener;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.toolshed.framework.Tool;
import hu.csega.toolshed.framework.ToolWindow;
import hu.csega.toolshed.framework.impl.AbstractTool;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

@Tool(name = "Rush!")
public class AsteroidGameToolImpl extends AbstractTool implements AsteroidGameTool, GameImplementation {

	private List<GameWindowListener> listeners = new ArrayList<>();

	@Override
	public String getTitle() {
		return "Asteroid Game";
	}

	@Override
	public void initialize(ToolWindow window) {
		logger.info("Tool initialization started.");

		createComponents();

		GameEngine engine = startGameEngine(window);

		GameModelStore store = engine.getStore();
		loadModelsIntoStore(store, (AsteroidGameRendering)engine.getRendering());

		logger.info("Tool initialization finished.");
	}

	private GameEngine startGameEngine(ToolWindow window) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("asteroid");
		descriptor.setTitle(getTitle());
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Asteroid game.");

		GameAdapter adapter = new OpenGLGameAdapter();

		AsteroidGameRenderingOptions options = new AsteroidGameRenderingOptions();
		options.renderHitShapes = true;

		GameImplementation implementation = new AsteroidGameToolImpl();
		AsteroidGamePhysics physics = new AsteroidGamePhysics();
		AsteroidGameRendering rendering = new AsteroidGameRendering(options);

		AsteroidGameField universe = new AsteroidGameField();
		universe.init();
		physics.universe = universe;
		rendering.universe = universe;

		AsteroidGameWindowWrapper wrapper = new AsteroidGameWindowWrapper(window, this);
		GameEngine engine = GameEngine.create(descriptor, adapter, implementation, physics, rendering);
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

	private void loadModelsIntoStore(GameModelStore store, AsteroidGameRendering rendering) {
		store.loadTexture("res/example/texture.png");
		GameObjectHandler model = store.buildModel(new GameModelBuilder());
		rendering.setModel(model);
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
