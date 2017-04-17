package hu.csega.games.adapters.opengl;

import java.awt.Dimension;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.engine.GameAdapter;
import hu.csega.games.engine.GameCanvas;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameThread;
import hu.csega.games.engine.GameWindow;
import hu.csega.games.engine.g3d.GameModelStore;

public class OpenGLGameAdapter implements GameAdapter {

	@Override
	public GameWindow createWindow(GameEngine engine) {
		OpenGLFrame frame = new OpenGLFrame(engine);
		return frame;
	}

	@Override
	public GameCanvas createCanvas(final GameEngine engine) {
        // GLProfile glProfile = GLProfile.getDefault();
        GLProfile glProfile = GLProfile.get(GLProfile.GL3);
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

}
