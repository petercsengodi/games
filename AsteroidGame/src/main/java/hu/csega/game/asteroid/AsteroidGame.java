package hu.csega.game.asteroid;

import hu.csega.toolshed.framework.ITool;
import hu.csega.toolshed.framework.ToolCallback;
import hu.csega.toolshed.framework.ToolMessage;
import hu.csega.toolshed.framework.ToolWindow;
import hu.csega.toolshed.framework.impl.ToolMessageImpl;
import hu.csega.toolshed.logging.Level;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;
import hu.csega.units.UnitStore;

public class AsteroidGame {

	private static final Level LOGGING_LEVEL = Level.TRACE;

	public static void main(String[] args) {
		LoggerFactory.setDefaultLevel(LOGGING_LEVEL);
		Logger logger = LoggerFactory.createLogger(AsteroidGame.class);
		logger.info("Starting game.");

		startFrame(logger);
	}

	public static void startFrame(final Logger logger) {
		ToolMessage msg = new ToolMessageImpl("", new ToolCallback() {
			@Override
			public void call(ToolMessage message) {
				if(message.isResponseProvided()) {
					logger.info("Response: " + message.getResponse());
				}
			}
		});

		ToolWindow toolFrame = UnitStore.instance(ToolWindow.class);
		ITool tool = UnitStore.instance(AsteroidGameTool.class);
		tool.setMessage(msg);
		toolFrame.embed(tool);
		toolFrame.start();
	}

}
