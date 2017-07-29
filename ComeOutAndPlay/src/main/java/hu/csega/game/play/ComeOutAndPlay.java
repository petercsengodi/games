package hu.csega.game.play;

import hu.csega.toolshed.framework.ITool;
import hu.csega.toolshed.framework.ToolCallback;
import hu.csega.toolshed.framework.ToolMessage;
import hu.csega.toolshed.framework.ToolWindow;
import hu.csega.toolshed.framework.impl.ToolMessageImpl;
import hu.csega.toolshed.logging.Level;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;
import hu.csega.units.UnitStore;

public class ComeOutAndPlay {

	private static final Level LOGGING_LEVEL = Level.TRACE;

	public static void main(String[] args) {
		LoggerFactory.setDefaultLevel(LOGGING_LEVEL);
		Logger logger = LoggerFactory.createLogger(ComeOutAndPlay.class);
		logger.info("Starting play editor.");

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
		ITool tool = UnitStore.instance(ComeOutAndPlayTool.class);
		tool.setMessage(msg);
		toolFrame.embed(tool);
		toolFrame.setFullScreen(true);
		toolFrame.start();
	}

}
