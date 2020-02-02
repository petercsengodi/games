package hu.csega.editors;

import hu.csega.editors.anm.AnimatorConnector;
import hu.csega.editors.anm.AnimatorModelBuilder;
import hu.csega.editors.anm.components.Component3DView;
import hu.csega.editors.anm.components.ComponentExtractPartList;
import hu.csega.editors.anm.components.ComponentOpenGLTransformer;
import hu.csega.editors.anm.components.ComponentPartListView;
import hu.csega.editors.anm.components.ComponentWireFrameTransformer;
import hu.csega.editors.anm.components.stubs.Stub3DView;
import hu.csega.editors.anm.components.stubs.StubExtractPartList;
import hu.csega.editors.anm.components.stubs.StubOpenGLTransformer;
import hu.csega.editors.anm.components.stubs.StubWireFrameTransformer;
import hu.csega.editors.anm.transformation.AnimatorExtractPartList;
import hu.csega.editors.anm.ui.AnimatorPartListView;
import hu.csega.editors.anm.ui.AnimatorUIComponents;
import hu.csega.editors.anm.view3d.Animator3DView;
import hu.csega.games.common.ApplicationStarter;
import hu.csega.games.common.Connector;
import hu.csega.games.library.TextureLibrary;
import hu.csega.games.library.util.FileUtil;
import hu.csega.games.units.UnitStore;
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


	public static void main(String[] args) {

		////////////////////////////////////////////////////////////////////////////////////////////////
		// 1. Initialize logging:

		LoggerFactory.setDefaultLevel(LOGGING_LEVEL);
		logger = LoggerFactory.createLogger(AnimatorStarter.class);
		logger.info("Starting tool.");


		////////////////////////////////////////////////////////////////////////////////////////////////
		// 2. Register components and providers:

		FileUtil files = new FileUtil("AMediaEditorCollection");
		UnitStore.registerInstance(FileUtil.class, files);
		UnitStore.registerInstance(TextureLibrary.class, new TextureLibrary(files));

		Connector connector = new AnimatorConnector();
		UnitStore.registerInstance(Connector.class, connector);

		UnitStore.registerDefaultImplementation(AnimatorUIComponents.class, AnimatorUIComponents.class);
		UnitStore.registerDefaultImplementation(ComponentPartListView.class, AnimatorPartListView.class);
		UnitStore.registerDefaultImplementation(ComponentOpenGLTransformer.class, AnimatorModelBuilder.class);
		UnitStore.registerDefaultImplementation(ComponentExtractPartList.class, AnimatorExtractPartList.class);
		UnitStore.registerDefaultImplementation(Component3DView.class, Animator3DView.class);

		////////////////////////////////////////////////////////////////////////////////////////////////
		// 3. Register test instances if needed:

		UnitStore.registerInstance(ComponentWireFrameTransformer.class, new StubWireFrameTransformer());
		UnitStore.registerInstance(ComponentOpenGLTransformer.class, new StubOpenGLTransformer());
		UnitStore.registerInstance(ComponentExtractPartList.class, new StubExtractPartList());
		UnitStore.registerInstance(Component3DView.class, new Stub3DView());

		////////////////////////////////////////////////////////////////////////////////////////////////
		// 4. Starting application:

		ApplicationStarter starter = new ApplicationStarter(connector);
		starter.start(args);
	}

}