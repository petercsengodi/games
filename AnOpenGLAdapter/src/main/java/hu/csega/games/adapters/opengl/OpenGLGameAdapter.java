package hu.csega.games.adapters.opengl;

import java.awt.Dimension;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import hu.csega.games.adapters.opengl.gl2.OpenGLProfileGL2Adapter;
import hu.csega.games.adapters.opengl.gl3.OpenGLProfileGL3Adapter;
import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameCanvas;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameThread;
import hu.csega.games.engine.GameWindow;
import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLGameAdapter implements GameAdapter {

	private OpenGLProfileAdapter openGLProfileAdapter = null;

	@Override
	public GameWindow createWindow(GameEngine engine) {
		OpenGLFrame frame = new OpenGLFrame(engine);
		return frame;
	}

	@Override
	public GameCanvas createCanvas(final GameEngine engine) {
		GLProfile glProfile;
		try {
			glProfile = GLProfile.get(GLProfile.GL3);
			openGLProfileAdapter = new OpenGLProfileGL3Adapter();
		} catch(Exception ex1) {
			logger.warning("Couldn't get GL3! (" + ex1.getMessage() + ')');

			try {
				glProfile = GLProfile.get(GLProfile.GL2);
				openGLProfileAdapter = new OpenGLProfileGL2Adapter();
			} catch(Exception ex2) {
				logger.error("Couldn't get GL2 either! (" + ex2.getMessage() + ')');
				throw new RuntimeException("Couldn't get GLProfile!");
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
	public GameThread createThread(GameEngine engine) {
		return new OpenGLThread(engine.getPhysics(), engine.getCanvas());
	}

	@Override
	public GameModelStore createStore(GameEngine engine) {
		return new OpenGLModelStoreImpl();
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLGameAdapter.class);
}
