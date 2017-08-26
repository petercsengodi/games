package hu.csega.games.adapters.opengl;

import java.awt.Dimension;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import hu.csega.games.adapters.opengl.gl2.OpenGLProfileGL2TriangleAdapter;
import hu.csega.games.adapters.opengl.gl3.OpenGLProfileGL3Adapter;
import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameAdapter;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLGameAdapter implements GameAdapter {

	private static final boolean FORCE_GL2_GLU = false;

	private OpenGLProfileAdapter openGLProfileAdapter = null;

	@Override
	public GameWindow createWindow(GameEngine engine) {
		OpenGLFrame frame = new OpenGLFrame(engine);
		return frame;
	}

	@Override
	public GameCanvas createCanvas(final GameEngine engine) {
		GLProfile glProfile;


		if(FORCE_GL2_GLU) {
			try {
				logger.info("Trying to acquire GL2 profile...");
				glProfile = GLProfile.get(GLProfile.GL2);
				// openGLProfileAdapter = new OpenGLProfileGL2GLUAdapter();
				openGLProfileAdapter = new OpenGLProfileGL2TriangleAdapter();
				logger.info("GL2 profile acquired, adapter: " + openGLProfileAdapter.getClass().getSimpleName());
			} catch(Exception ex2) {
				logger.error("Couldn't get GL2 for GLU! (" + ex2.getMessage() + ')');
				throw new RuntimeException("Couldn't get GLProfile!");
			}

		} else {

			try {
				logger.info("Trying to acquire GL3 profile...");
				glProfile = GLProfile.get(GLProfile.GL3);
				openGLProfileAdapter = new OpenGLProfileGL3Adapter();
				// openGLProfileAdapter = new OpenGLProfileGL3Adapter2();
				logger.info("GL3 profile acquired, adapter: " + openGLProfileAdapter.getClass().getSimpleName());
			} catch(Exception ex1) {
				logger.warning("Couldn't get GL3! (" + ex1.getMessage() + ')');

				try {
					logger.info("Trying to acquire GL2 profile...");
					glProfile = GLProfile.get(GLProfile.GL2);
					// openGLProfileAdapter = new OpenGLProfileGL2Adapter();
					openGLProfileAdapter = new OpenGLProfileGL2TriangleAdapter();
					logger.info("GL2 profile acquired, adapter: " + openGLProfileAdapter.getClass().getSimpleName());
				} catch(Exception ex2) {
					logger.error("Couldn't get GL2 either! (" + ex2.getMessage() + ')');
					throw new RuntimeException("Couldn't get GLProfile!");
				}
			}

		}

		OpenGLModelStoreImpl store = (OpenGLModelStoreImpl)engine.getStore();
		store.setAdapter(openGLProfileAdapter);

		GLCapabilities glCapabilities = new GLCapabilities(glProfile);
		OpenGLGraphics graphics = new OpenGLGraphics();
		GLEventListener eventListener = new OpenGLEventListener(engine, graphics);

		final GLCanvas glCanvas = new GLCanvas(glCapabilities);
		glCanvas.setPreferredSize(new Dimension(640, 480));
		glCanvas.addGLEventListener(eventListener);

		return new OpenGLCanvas(glCanvas);
	}

	@Override
	public GameModelStore createStore(GameEngine engine) {
		return new OpenGLModelStoreImpl();
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLGameAdapter.class);
}
