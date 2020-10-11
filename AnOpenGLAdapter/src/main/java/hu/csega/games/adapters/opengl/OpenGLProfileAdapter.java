package hu.csega.games.adapters.opengl;

import com.jogamp.opengl.GLAutoDrawable;

import hu.csega.games.adapters.opengl.models.OpenGLModelContainer;
import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.adapters.opengl.models.OpenGLTextureContainer;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameSelectionLine;
import hu.csega.games.engine.g3d.GameTransformation;

public interface OpenGLProfileAdapter {

	void viewPort(GLAutoDrawable glAutoDrawable, int width, int height);

	void initializeProgram(GLAutoDrawable glAutoDrawable, String shadersRoot);

	void disposeProgram(GLAutoDrawable glAutoDrawable);

	void startFrame(GLAutoDrawable glAutoDrawable);

	void endFrame(GLAutoDrawable glAutoDrawable);

	int getModelToClipMatrixUL();

	void loadTexture(GLAutoDrawable glAutodrawable, String filename, OpenGLTextureContainer container);

	void disposeTexture(GLAutoDrawable glAutoDrawable, OpenGLTextureContainer container);

	void loadModel(GLAutoDrawable glAutoDrawable, String filename, OpenGLModelContainer model);

	void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, GameObjectPlacement placement, OpenGLModelStoreImpl store);

	void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, GameTransformation transformation, OpenGLModelStoreImpl store);

	void disposeModel(GLAutoDrawable glAutodrawable, OpenGLModelContainer model);

	void loadAnimation(GLAutoDrawable glAutoDrawable, String filename, OpenGLModelContainer model);

	void drawAnimation(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, OpenGLModelStoreImpl store);

	void disposeAnimation(GLAutoDrawable glAutodrawable, OpenGLModelContainer model);

	void placeCamera(GLAutoDrawable glAutodrawable, GameObjectPlacement cameraPlacement);

	void setBaseMatricesAndViewPort(GameSelectionLine selectionLine);

}
