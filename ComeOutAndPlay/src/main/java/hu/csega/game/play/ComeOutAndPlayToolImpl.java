package hu.csega.game.play;

import java.util.ArrayList;
import java.util.List;

import hu.csega.game.play.model.PlayModel;
import hu.csega.games.adapters.swing.SwingGameAdapter;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameDescriptor;
import hu.csega.games.engine.intf.GameWindowListener;
import hu.csega.toolshed.framework.Tool;
import hu.csega.toolshed.framework.ToolWindow;
import hu.csega.toolshed.framework.impl.AbstractTool;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

@Tool(name = "Come Out And Play")
public class ComeOutAndPlayToolImpl extends AbstractTool implements ComeOutAndPlayTool {

	private List<GameWindowListener> listeners = new ArrayList<>();

	@Override
	public String getTitle() {
		return "Come Out And Play";
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
		descriptor.setId("play");
		descriptor.setTitle(getTitle());
		descriptor.setVersion("v00.00.0001");
		descriptor.setDescription("Play editor.");

		GameAdapter adapter = new SwingGameAdapter();

		GameEngine engine = GameEngine.create(descriptor, adapter);
		// engine.step(GameEngineStep.INIT, new AsteroidGameInitStep());
		// engine.step(GameEngineStep.RENDER, new AsteroidGameRenderingStep(options));

		engine.startInNewWindow();
		return engine;
	}

	private void createComponents() {
		registerComponent(this);

		PlayModel model = new PlayModel();
		registerComponent(PlayModel.class, model);
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

	private static final Logger logger = LoggerFactory.createLogger(ComeOutAndPlayToolImpl.class);
}
