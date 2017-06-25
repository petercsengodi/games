package hu.csega.game.boulderdash;

import java.util.ArrayList;
import java.util.List;

import hu.csega.game.rush.engine.BoulderDashPhysics;
import hu.csega.game.rush.engine.BoulderDashRendering;
import hu.csega.game.rush.engine.BoulderDashRenderingOptions;
import hu.csega.game.rush.model.BoulderDashModel;
import hu.csega.game.rush.view.BoulderDashView;
import hu.csega.games.adapters.swing.SwingGameAdapter;
import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameDescriptor;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameImplementation;
import hu.csega.games.engine.GameWindowListener;
import hu.csega.games.engine.g2d.GameControlImpl;
import hu.csega.toolshed.framework.Tool;
import hu.csega.toolshed.framework.ToolWindow;
import hu.csega.toolshed.framework.impl.AbstractTool;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

@Tool(name = "Boulder Dash Clone")
public class BoulderDashToolImpl extends AbstractTool implements BoulderDashTool, GameImplementation {

	private List<GameWindowListener> listeners = new ArrayList<>();

	@Override
	public String getTitle() {
		return "Boulder Dash Clone";
	}

	@Override
	public void initialize(ToolWindow window) {
		logger.info("Tool initialization started.");

		createComponents();

		GameEngine engine = startGameEngine(window);

		logger.info("Tool initialization finished.");
	}

	private GameEngine startGameEngine(ToolWindow window) {

		GameDescriptor descriptor = new GameDescriptor();
		descriptor.setId("boulderDashClone");
		descriptor.setTitle("Boulder Dash Clone");
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Hobby re-make of an old C64/C16 game.");

		GameAdapter adapter = new SwingGameAdapter();

		BoulderDashRenderingOptions renderingOptions = new BoulderDashRenderingOptions();
		renderingOptions.renderHitShapes = true;

		BoulderDashPhysics physics = new BoulderDashPhysics();
		BoulderDashRendering rendering = new BoulderDashRendering(renderingOptions);

		physics.setGameControl(new GameControlImpl());

		GameEngine engine = GameEngine.create(descriptor, adapter, this, physics, rendering);
		engine.startInNewWindow();

		return engine;
	}

	private void createComponents() {
		registerComponent(this);

		BoulderDashModel model = new BoulderDashModel();
		registerComponent(BoulderDashModel.class, model);

		BoulderDashView view = new BoulderDashView();
		view.setModel(model);
		registerComponent(BoulderDashView.class, view);
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

	private static final Logger logger = LoggerFactory.createLogger(BoulderDashToolImpl.class);
}
