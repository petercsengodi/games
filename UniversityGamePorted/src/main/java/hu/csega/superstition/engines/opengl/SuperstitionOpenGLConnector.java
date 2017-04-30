package hu.csega.superstition.engines.opengl;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.adapters.opengl.OpenGLGameAdapter;
import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameCanvas;
import hu.csega.games.engine.GameDescriptor;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameImplementation;
import hu.csega.games.engine.GameWindow;
import hu.csega.games.engine.GameWindowListener;
import hu.csega.games.engine.env.Environment;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.superstition.engines.connector.Connector;
import hu.csega.toolshed.framework.ToolWindow;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class SuperstitionOpenGLConnector implements Connector, GameImplementation, GameWindow {

	private List<GameWindowListener> listeners = new ArrayList<>();

	@Override
	public void run(Environment env) {
		logger.info(className() + " start run()");

		GameEngine engine = startGameEngine();

		GameModelStore store = engine.getStore();
		loadModelsIntoStore(store, (SuperstitionGameRendering)engine.getRendering());

		logger.info(className() + " end run()");
	}

	@Override
	public void register(GameWindowListener listener) {
		listeners.add(listener);
	}

	@Override
	public void add(GameCanvas canvas) {
	}

	@Override
	public void showWindow() {
	}

	@Override
	public void closeWindow() {
		for(GameWindowListener listener: listeners) {
			listener.onFinishingWork();
		}
	}

	@Override
	public void dispose() {
		logger.info(className() + " start dispose()");

		logger.info(className() + " end dispose()");
	}

	private GameEngine startGameEngine() {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("rush");
		descriptor.setTitle("Superstition – ported game seed from the university");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("My work from the university severly changed and ported to Java/OpenGL.");

		GameAdapter adapter = new OpenGLGameAdapter();

		SuperstitionGameRenderingOptions options = new SuperstitionGameRenderingOptions();
		options.renderHitShapes = true;

		GameImplementation implementation = this;
		SuperstitionGamePhysics physics = new SuperstitionGamePhysics();
		SuperstitionGameRendering rendering = new SuperstitionGameRendering(options);

		SuperstitionGameField universe = new SuperstitionGameField();
		universe.init();
		physics.universe = universe;
		rendering.universe = universe;

		GameEngine engine = GameEngine.create(descriptor, adapter, implementation, physics, rendering);
		engine.startInNewWindow();

		return engine;
	}

	private void loadModelsIntoStore(GameModelStore store, SuperstitionGameRendering rendering) {
		store.loadTexture("res/example/texture.png");
		GameObjectHandler model = store.buildModel(new GameModelBuilder());
		rendering.setModel(model);
	}

	private String className() {
		return getClass().getSimpleName();
	}

	private static final Logger logger = LoggerFactory.createLogger(SuperstitionOpenGLConnector.class);
}
