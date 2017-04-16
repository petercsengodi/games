package hu.csega.game.rush;

import java.awt.Component;

import hu.csega.game.rush.model.RushGameModel;
import hu.csega.game.rush.view.RushGameView;
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

		window.getContentPane().add((Component)getComponent(ToolCanvas.class));

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

	private Logger logger = LoggerFactory.createLogger(RushGameToolImpl.class);
}
