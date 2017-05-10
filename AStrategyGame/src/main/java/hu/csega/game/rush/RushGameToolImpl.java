package hu.csega.game.rush;

import java.util.ArrayList;
import java.util.List;

import hu.csega.game.rush.engine.RushGameField;
import hu.csega.game.rush.engine.RushGamePhysics;
import hu.csega.game.rush.engine.RushGameRendering;
import hu.csega.game.rush.engine.RushGameRenderingOptions;
import hu.csega.game.rush.engine.RushGameWindowWrapper;
import hu.csega.game.rush.model.RushGameModel;
import hu.csega.game.rush.view.RushGameView;
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
public class RushGameToolImpl extends AbstractTool implements RushGameTool, GameImplementation {

	private List<GameWindowListener> listeners = new ArrayList<>();

	@Override
	public String getTitle() {
		return "Rush!";
	}

	@Override
	public void initialize(ToolWindow window) {
		logger.info("Tool initialization started.");

		createComponents();

		GameEngine engine = startGameEngine(window);

		GameModelStore store = engine.getStore();
		loadModelsIntoStore(store, (RushGameRendering)engine.getRendering());

		logger.info("Tool initialization finished.");
	}

	private GameEngine startGameEngine(ToolWindow window) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("rush");
		descriptor.setTitle(getTitle());
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Real-time strategy game.");

		GameAdapter adapter = new OpenGLGameAdapter();

		RushGameRenderingOptions options = new RushGameRenderingOptions();
		options.renderHitShapes = true;

		GameImplementation implementation = new RushGameToolImpl();
		RushGamePhysics physics = new RushGamePhysics();
		RushGameRendering rendering = new RushGameRendering(options);

		RushGameField universe = new RushGameField();
		universe.init();
		physics.universe = universe;
		rendering.universe = universe;

		RushGameWindowWrapper wrapper = new RushGameWindowWrapper(window, this);
		GameEngine engine = GameEngine.create(descriptor, adapter, implementation, physics, rendering);
		engine.startIn(wrapper);

		return engine;
	}

	private void createComponents() {
		registerComponent(this);

		RushGameModel model = new RushGameModel();
		registerComponent(RushGameModel.class, model);

		RushGameView view = new RushGameView();
		view.setModel(model);
		registerComponent(RushGameView.class, view);
	}

	private void loadModelsIntoStore(GameModelStore store, RushGameRendering rendering) {
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

	private static final Logger logger = LoggerFactory.createLogger(RushGameToolImpl.class);
}
