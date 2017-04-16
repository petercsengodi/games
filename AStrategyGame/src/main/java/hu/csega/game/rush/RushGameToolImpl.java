package hu.csega.game.rush;

import hu.csega.game.rush.model.RushGameModel;
import hu.csega.game.rush.view.RushGameView;
import hu.csega.games.adapters.opengl.HelloTexture;
import hu.csega.games.engine.env.Environment;
import hu.csega.toolshed.framework.Tool;
import hu.csega.toolshed.framework.ToolCanvas;
import hu.csega.toolshed.framework.ToolWindow;
import hu.csega.toolshed.framework.impl.AbstractTool;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;
import hu.csega.units.UnitStore;

@Tool(name = "Rush!")
public class RushGameToolImpl extends AbstractTool implements RushGameTool {

	@Override
	public String getTitle() {
		return "Rush!";
	}

	@Override
	public void initialize(ToolWindow window) {
		logger.info("Tool initialization started.");

		createComponents();

		window.addComponent(getComponent(ToolCanvas.class));

		Environment env = UnitStore.instance(Environment.class);

		example = new HelloTexture();
		example.setEnvironment(env);
		example.run();

		logger.info("Tool initialization finished.");
	}

	public void createComponents() {
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
