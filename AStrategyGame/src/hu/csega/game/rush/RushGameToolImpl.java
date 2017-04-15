package hu.csega.game.rush;

import hu.csega.toolshed.framework.Tool;
import hu.csega.toolshed.framework.ToolWindow;
import hu.csega.toolshed.framework.impl.AbstractTool;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

@Tool(name = "Rush!")
public class RushGameToolImpl extends AbstractTool implements RushGameTool {

	@Override
	public String getTitle() {
		return "Rush!";
	}

	@Override
	public void initialize(ToolWindow window) {
		logger.info("Tool initialization started.");

		logger.info("Tool initialization finished.");
	}

	private Logger logger = LoggerFactory.createLogger(RushGameToolImpl.class);

}
