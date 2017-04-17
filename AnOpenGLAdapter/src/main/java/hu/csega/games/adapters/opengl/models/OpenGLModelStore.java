package hu.csega.games.adapters.opengl.models;

import com.jogamp.opengl.GLAutoDrawable;

public interface OpenGLModelStore {

	/**
	 * Called, when the all the models need to be rebuilt.
	 */
	void reset(GLAutoDrawable glAutodrawable);

	/**
	 * Call when the canvas size changed, and need to re-set screen.
	 */
	void setupScreen(GLAutoDrawable glAutodrawable, int width, int height);


}
