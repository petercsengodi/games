package hu.csega.games.adapters.opengl.models;

import com.jogamp.opengl.GLAutoDrawable;

import hu.csega.games.engine.g3d.GameModelStore;
import hu.csega.games.engine.g3d.GameObjectHandler;

public interface OpenGLModelStore extends GameModelStore {

	/**
	 * Called, when the all of the models need to be rebuilt.
	 */
	void reset(GLAutoDrawable glAutodrawable);

	/**
	 * @return True, if some of the models are not initialized.
	 */
	boolean needsInitialization();

	/**
	 * Initialize some models.
	 */
	void initializeModels(GLAutoDrawable glAutodrawable);

	/**
	 * Dispose OpenGL related objects.
	 */
	void disposeUnderlyingObjects(GLAutoDrawable glAutodrawable);

	/**
	 * Call when the canvas size changed, and need to re-set screen.
	 */
	void setupScreen(GLAutoDrawable glAutodrawable, int width, int height);

	@Override
	GameObjectHandler loadTexture(String filename);


}
