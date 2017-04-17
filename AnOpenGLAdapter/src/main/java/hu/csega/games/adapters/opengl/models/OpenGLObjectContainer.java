package hu.csega.games.adapters.opengl.models;

import com.jogamp.opengl.GLAutoDrawable;

public interface OpenGLObjectContainer {

	String filename();
	boolean isInitialized();
	void initialize(GLAutoDrawable glAutodrawable);
	void dispose(GLAutoDrawable glAutodrawable);

}
