package hu.csega.games.adapters.opengl;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.engine.GameEngine;

public class OpenGLEventListener implements GLEventListener {

	private GameEngine engine;
	private OpenGLGraphics graphics;
	private OpenGLModelStoreImpl store;

	public OpenGLEventListener(GameEngine engine, OpenGLGraphics graphics) {
		this.engine = engine;
		this.graphics = graphics;
		this.store = (OpenGLModelStoreImpl) engine.getStore();
	}

	@Override
	public void reshape(GLAutoDrawable glAutodrawable, int x, int y, int width, int height) {
		store.setupScreen(glAutodrawable, width, height);
	}

	@Override
	public void init(GLAutoDrawable glAutodrawable) {
		store.initializeModels(glAutodrawable);
	}

	@Override
	public void dispose(GLAutoDrawable glAutodrawable) {
		store.disposeUnderlyingObjects(glAutodrawable);
	}

	@Override
	public void display(GLAutoDrawable glAutodrawable) {
		if (store.needsInitialization())
			store.initializeModels(glAutodrawable);

		graphics.setStore(store);
		graphics.setAutoDrawable(glAutodrawable, glAutodrawable.getSurfaceWidth(), glAutodrawable.getSurfaceHeight());
		engine.getRendering().render(graphics);
		graphics.clean();
	}

}
