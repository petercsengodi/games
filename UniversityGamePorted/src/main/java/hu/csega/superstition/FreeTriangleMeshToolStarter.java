package hu.csega.superstition;

import hu.csega.superstition.common.ApplicationStarter;
import hu.csega.superstition.engines.connector.Connector;
import hu.csega.superstition.ftm.FreeTriangleMeshConnector;
import hu.csega.toolshed.logging.Level;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

/**
 * Responsible for running the Free Triangle Mesh tool.
 * A single instance in the JVM, accessible by any components, and
 * links all the components together.
 * However, not static methods are used, as writing a testable code is prio 1!
 */
public class FreeTriangleMeshToolStarter {

	public static final String TEXTURE_FILE = "res/textures/z_other/wood-texture.jpg";

	private static final Level LOGGING_LEVEL = Level.TRACE;
	private static Logger logger;

	public static void main(String[] args) {
		LoggerFactory.setDefaultLevel(LOGGING_LEVEL);
		logger = LoggerFactory.createLogger(FreeTriangleMeshToolStarter.class);
		logger.info("Starting tool.");

		Connector connector = new FreeTriangleMeshConnector();
		ApplicationStarter starter = new ApplicationStarter(connector);
		starter.start(args);
	}

}