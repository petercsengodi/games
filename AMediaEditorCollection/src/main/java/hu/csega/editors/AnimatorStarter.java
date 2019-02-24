package hu.csega.editors;

import hu.csega.editors.anm.AnimatorConnector;
import hu.csega.games.common.ApplicationStarter;
import hu.csega.games.common.Connector;
import hu.csega.games.library.TextureLibrary;
import hu.csega.games.library.util.FileUtil;
import hu.csega.toolshed.logging.Level;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

/**
 * Responsible for running the Animator tool.
 * A single instance in the JVM, accessible by any components, and
 * links all the components together.
 * However, not static methods are used, as writing a testable code is prio 1!
 */
public class AnimatorStarter {

	public static final String DEFAULT_TEXTURE_FILE = "res/textures/ship2.jpg";

	private static final Level LOGGING_LEVEL = Level.TRACE;
	private static Logger logger;

	public static FileUtil FILES;
	public static TextureLibrary TEXTURES;

	public static void main(String[] args) {
		LoggerFactory.setDefaultLevel(LOGGING_LEVEL);
		logger = LoggerFactory.createLogger(AnimatorStarter.class);
		logger.info("Starting tool.");

		FILES = new FileUtil("AMediaEditorCollection");
		TEXTURES = new TextureLibrary(FILES);

		Connector connector = new AnimatorConnector();
		ApplicationStarter starter = new ApplicationStarter(connector);
		starter.start(args);
	}

}