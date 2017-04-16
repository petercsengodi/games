package hu.csega.game.rush;

import gl3.helloTexture.HelloTexture;
import hu.csega.game.rush.engine.RushGamePhysics;
import hu.csega.game.rush.engine.RushGameRendering;
import hu.csega.game.rush.engine.RushGameRenderingOptions;
import hu.csega.game.rush.engine.RushGameField;
import hu.csega.game.rush.model.RushGameModel;
import hu.csega.game.rush.view.RushGameView;
import hu.csega.games.adapters.opengl.OpenGLGameAdapter;
import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameDescriptor;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameImplementation;
import hu.csega.games.engine.env.Environment;
import hu.csega.toolshed.framework.Tool;
import hu.csega.toolshed.framework.ToolCanvas;
import hu.csega.toolshed.framework.ToolWindow;
import hu.csega.toolshed.framework.impl.AbstractTool;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;
import hu.csega.units.UnitStore;

@Tool(name = "Rush!")
public class RushGameToolImpl extends AbstractTool implements RushGameTool, GameImplementation {

	@Override
	public String getTitle() {
		return "Rush!";
	}

	@Override
	public void initialize(ToolWindow window) {
		logger.info("Tool initialization started.");

		createComponents();

		window.addComponent(getComponent(ToolCanvas.class));

//		Environment env = UnitStore.instance(Environment.class);
//
//		example = new HelloTexture();
//		example.setEnvironment(env);
//		example.run();

		startGameEngine();

		logger.info("Tool initialization finished.");
	}

	private void startGameEngine() {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("uncharted");
		descriptor.setTitle("Uncharted");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Plain \"shoot'em all\" game with randomly generated maps.");

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

		GameEngine.start(descriptor, adapter, implementation, physics, rendering);
	}

	private void createComponents() {
		registerComponent(this);

		RushGameModel model = new RushGameModel();
		registerComponent(RushGameModel.class, model);

		RushGameView view = new RushGameView();
		view.setModel(model);
		registerComponent(RushGameView.class, view);

		ToolCanvas canvas = UnitStore.instance(ToolCanvas.class);
		canvas.setToolView(view);
		registerComponent(ToolCanvas.class, canvas);
	}

	@Override
	public void finishWork() {
		logger.info("Start finalizing.");
		super.finishWork();
		example.dispose();
		logger.info("Finalizing done.");
	}

	private HelloTexture example;

	private static final Logger logger = LoggerFactory.createLogger(RushGameToolImpl.class);
}
