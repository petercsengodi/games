package hu.csega.game.rush.layer1.presentation;

import java.util.ArrayList;
import java.util.List;

import hu.csega.game.rush.layer4.data.RushGameModel;
import hu.csega.games.adapters.opengl.OpenGLGameAdapter;
import hu.csega.games.engine.GameEngineCallback;
import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameModelStore;
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

@Tool(name = "Rush!")
public class RushGameToolImpl extends AbstractTool implements RushGameTool {

	private List<GameWindowListener> listeners = new ArrayList<>();

	@Override
	public String getTitle() {
		return "Rush!";
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
		descriptor.setId("rush");
		descriptor.setTitle(getTitle());
		descriptor.setVersion("v00.00.0002");
		descriptor.setDescription("Real-time strategy game.");

		GameAdapter adapter = new OpenGLGameAdapter();

		RushGameRenderingOptions options = new RushGameRenderingOptions();
		options.renderHitShapes = true;

		GameEngine engine = GameEngine.create(descriptor, adapter);

		engine.step(GameEngineStep.INIT, new GameEngineCallback() {

			@Override
			public Object call(GameEngineFacade facade) {
				GameModelStore store = facade.store();

				RushGameModel model = new RushGameModel();
				model.init(store);

				facade.setModel(model);
				return facade;
			}
		});

		engine.step(GameEngineStep.RENDER, new RushRenderStep());

		RushGameWindowWrapper wrapper = new RushGameWindowWrapper(window, this, engine.getFacade());
		engine.startIn(wrapper, wrapper.getContainer());
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
