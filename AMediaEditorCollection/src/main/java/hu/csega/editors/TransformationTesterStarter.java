package hu.csega.editors;

import hu.csega.editors.transformations.layer1.presentation.opengl.TransformationTesterConnector;
import hu.csega.games.common.ApplicationStarter;
import hu.csega.games.common.Connector;
import hu.csega.games.library.TextureLibrary;
import hu.csega.games.library.util.FileUtil;
import hu.csega.toolshed.logging.Level;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class TransformationTesterStarter {

	private static final Level LOGGING_LEVEL = Level.TRACE;
	private static Logger logger;

	public static FileUtil FILES;
	public static TextureLibrary TEXTURES;

	public static void main(String[] args) {
		LoggerFactory.setDefaultLevel(LOGGING_LEVEL);
		logger = LoggerFactory.createLogger(TransformationTesterStarter.class);
		logger.info("Starting translation tester.");

		FILES = new FileUtil("AMediaEditorCollection");
		TEXTURES = new TextureLibrary(FILES);

		Connector connector = new TransformationTesterConnector();
		ApplicationStarter starter = new ApplicationStarter(connector);
		starter.start(args);
	}

}