package hu.csega.games.adapters.opengl;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.engine.env.GameEngineException;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLEventListener implements GLEventListener {

	private GameEngine engine;
	private OpenGLGraphics graphics;
	private OpenGLModelStoreImpl store;

	private boolean disabled = false;

	public OpenGLEventListener(GameEngine engine, OpenGLGraphics graphics) {
		this.engine = engine;
		this.graphics = graphics;
		this.store = (OpenGLModelStoreImpl) engine.getStore();
	}

	@Override
	public void reshape(GLAutoDrawable glAutodrawable, int x, int y, int width, int height) {
		if(disabled)
			return;

		try {
			store.setupScreen(glAutodrawable, width, height);
		} catch(GameEngineException ex) {
			handleError(glAutodrawable, ex);
		}
	}

	@Override
	public void init(GLAutoDrawable glAutodrawable) {
		if(disabled)
			return;

		try {
			store.initializeModels(glAutodrawable);
		} catch(GameEngineException ex) {
			handleError(glAutodrawable, ex);
		}
	}

	@Override
	public void dispose(GLAutoDrawable glAutodrawable) {
		if(disabled)
			return;

		try {
			store.disposeUnderlyingObjects(glAutodrawable);
		} catch(GameEngineException ex) {
			handleError(glAutodrawable, ex);
		}
	}

	@Override
	public void display(GLAutoDrawable glAutodrawable) {
		if(disabled)
			return;

		try {
			if (store.needsInitialization())
				store.initializeModels(glAutodrawable);

			graphics.setStore(store);
			graphics.setAutoDrawable(glAutodrawable, glAutodrawable.getSurfaceWidth(), glAutodrawable.getSurfaceHeight());
			graphics.startFrame();

			engine.runStep(GameEngineStep.RENDER, graphics);

			graphics.endFrame();
			graphics.clean();
		} catch(GameEngineException ex) {
			handleError(glAutodrawable, ex);
		}
	}

	private void handleError(GLAutoDrawable glAutodrawable, GameEngineException ex) {
		disabled = true;
		logger.error("Error during display!", ex);
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLEventListener.class);
}
