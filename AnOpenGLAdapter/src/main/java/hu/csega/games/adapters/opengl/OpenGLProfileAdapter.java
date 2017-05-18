package hu.csega.games.adapters.opengl;

import com.jogamp.opengl.GLAutoDrawable;

import hu.csega.games.adapters.opengl.models.OpenGLModelContainer;
import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;

public interface OpenGLProfileAdapter {

	void viewPort(GLAutoDrawable glAutoDrawable, int width, int height);

	void initializeProgram(GLAutoDrawable glAutoDrawable, String shadersRoot);

	void disposeProgram(GLAutoDrawable glAutoDrawable);

	void startFrame(GLAutoDrawable glAutoDrawable);

	void endFrame(GLAutoDrawable glAutoDrawable);

	int getModelToClipMatrixUL();

	void loadTexture(GLAutoDrawable glAutodrawable, String filename, int[] generatedTextureNames);

	void disposeTexture(GLAutoDrawable glAutoDrawable, int[] generatedTextureNames);

	void loadModel(GLAutoDrawable glAutoDrawable, String filename, OpenGLModelContainer model);

	void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, OpenGLModelStoreImpl store);

	void disposeModel(GLAutoDrawable glAutodrawable, OpenGLModelContainer model);

}
