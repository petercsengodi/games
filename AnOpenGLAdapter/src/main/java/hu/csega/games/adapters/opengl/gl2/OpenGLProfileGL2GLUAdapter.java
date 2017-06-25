package hu.csega.games.adapters.opengl.gl2;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import hu.csega.games.adapters.opengl.OpenGLProfileAdapter;
import hu.csega.games.adapters.opengl.models.OpenGLModelContainer;
import hu.csega.games.adapters.opengl.models.OpenGLModelStoreImpl;
import hu.csega.games.adapters.opengl.models.OpenGLTextureContainer;
import hu.csega.games.adapters.opengl.utils.BufferUtils;
import hu.csega.games.adapters.opengl.utils.OpenGLErrorUtil;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLProfileGL2GLUAdapter implements OpenGLProfileAdapter {

	@Override
	public void viewPort(GLAutoDrawable glAutoDrawable, int width, int height) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		gl2.glViewport(0, 0, width, height);

		OpenGLErrorUtil.checkError(gl2, "viewPort");
	}

	@Override
	public void initializeProgram(GLAutoDrawable glAutoDrawable, String shadersRoot) {
		// Nothing to do currently
	}

	@Override
	public void disposeProgram(GLAutoDrawable glAutoDrawable) {
		// Nothing to do currently
	}

	@Override
	public void startFrame(GLAutoDrawable glAutoDrawable) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		GLU glu = GLU.createGLU(gl2);

		gl2.glEnable(GL2.GL_DEPTH_TEST);
		gl2.glEnable(GL2.GL_TEXTURE_2D);
		// gl2.glEnable(GL2.GL_NORMALIZE);

		gl2.glEnable(GL2.GL_NORMALIZE);
		gl2.glEnable(GL2.GL_AUTO_NORMAL);
		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glEnable(GL2.GL_LIGHT0);

		float[] ambientLight = { 0.5f, 0.5f, 0.5f, 0f };  // strong RED ambient
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);

		float[] diffuseLight = { 1f, 2f, 1f, 0f };  // multicolor diffuse
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(45.0f, 1, 0.1f, 100.0f);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		//		glu.gluLookAt(
		//				0,  0, 10,
		//				0,  0,  0,
		//				0,  1,  1
		//				);

		glu.gluLookAt(
				0,  0, 10,
				0,  0,  0,
				0,  1,  0
				);

		//		glu.gluLookAt(
		//				0,  0, -10,
		//				0,  0,   0,
		//				0,  1,   0
		//				);

		// gl2.glPushMatrix();

		OpenGLErrorUtil.checkError(gl2, "modelViewMatrix");

		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl2.glMatrixMode(GL.GL_TEXTURE);

		OpenGLErrorUtil.checkError(gl2, "startFrame");
	}

	@Override
	public void endFrame(GLAutoDrawable glAutoDrawable) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();

		// gl2.glPopMatrix();
		// OpenGLErrorUtil.checkError(gl2, "pop");

		gl2.glFlush();

		OpenGLErrorUtil.checkError(gl2, "endFrame");
	}

	@Override
	public int getModelToClipMatrixUL() {
		return -1;
	}

	@Override
	public void loadTexture(GLAutoDrawable glAutoDrawable, String filename, OpenGLTextureContainer container) {
		try {
			Texture texture = TextureIO.newTexture(new File(filename), true);
			container.setTexture(texture);
		} catch (IOException ex) {
			throw new RuntimeException("Exception occurred while loading texture!", ex);
		}
	}

	@Override
	public void disposeTexture(GLAutoDrawable glAutoDrawable, OpenGLTextureContainer container) {
		GL2 gl2 = glAutoDrawable.getGL().getGL2();
		Texture texture = container.getTexture();
		texture.destroy(gl2);
		OpenGLErrorUtil.checkError(gl2, "disposeTexture");
	}

	@Override
	public void loadModel(GLAutoDrawable glAutoDrawable, String filename, OpenGLModelContainer model) {

		try {

			GL2 gl2 = glAutoDrawable.getGL().getGL2();
			createVertexBuffers(gl2, model);
			createIndexBuffers(gl2, model);
			createVertexArrays(gl2, model);

		} catch (Exception ex) {
			logger.error("Exception in model initialization: " + filename, ex);
		}
	}

	private void createVertexBuffers(GL2 gl2, OpenGLModelContainer model) {
		gl2.glGenBuffers(model.getNumberOfVertexBuffers(), model.getOpenGLHandlers(), model.getOffsetOfVertexBuffers());
		for(int i = 0; i < model.getNumberOfVertexBuffers(); i++) {
			gl2.glBindBuffer(GL2.GL_ARRAY_BUFFER, model.getOpenGLHandlers()[model.getOffsetOfVertexBuffers() + i]);

			float[] vertexData = model.builder().vertexData(i);
			int size = vertexData.length * Float.BYTES;

			FloatBuffer vertexBuffer = GLBuffers.newDirectFloatBuffer(vertexData);
			// vertexBuffer.rewind();
			gl2.glBufferData(GL2.GL_ARRAY_BUFFER, size, vertexBuffer, GL2.GL_STATIC_DRAW);
			BufferUtils.destroyDirectBuffer(vertexBuffer);

			gl2.glBindBuffer(GL2.GL_ARRAY_BUFFER, 0);
		}

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.createVertexBuffers");
	}

	private void createIndexBuffers(GL2 gl2, OpenGLModelContainer model) {
		gl2.glGenBuffers(model.getNumberOfIndexBuffers(), model.getOpenGLHandlers(), model.getOffsetOfIndexBuffers());
		for(int i = 0; i < model.getNumberOfIndexBuffers(); i++) {
			gl2.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, model.getOpenGLHandlers()[model.getOffsetOfIndexBuffers() + i]);

			short[] indexData = model.builder().indexData(i);
			int size = indexData.length * Short.BYTES;

			ShortBuffer indexBuffer = GLBuffers.newDirectShortBuffer(indexData);
			gl2.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, size, indexBuffer, GL2.GL_STATIC_DRAW);
			BufferUtils.destroyDirectBuffer(indexBuffer);

			gl2.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, 0);
		}

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.createIndexBuffers");
	}

	private void createVertexArrays(GL2 gl2, OpenGLModelContainer model) {
		int stride = (3 + 3 + 2) * Float.BYTES;

		gl2.glGenVertexArrays(model.getNumberOfVertexArrays(), model.getOpenGLHandlers(), model.getOffsetOfVertexArrays());
		for(int i = 0; i < model.getNumberOfVertexArrays(); i++) {
			gl2.glBindVertexArray(model.getOpenGLHandlers()[model.getOffsetOfVertexArrays() + i]);
			gl2.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, model.getOpenGLHandlers()[model.getOffsetOfIndexBuffers() + i]);
			gl2.glBindBuffer(GL2.GL_ARRAY_BUFFER, model.getOpenGLHandlers()[model.getOffsetOfVertexBuffers() + i]);
			gl2.glBindVertexArray(0);
		}

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.createVertexArrays");
	}

	@Override
	public void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, OpenGLModelStoreImpl store) {
		int stride = 3 + 3 + 2;

		GL2 gl2 = glAutoDrawable.getGL().getGL2();

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.draw init");

		for(int i = 0; i < model.getNumberOfVertexArrays(); i++) {
			Texture texture = model.builder().textureContainer(i).getTexture();
			texture.enable(gl2);
			texture.bind(gl2);

			OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.draw texture init" + i);

			gl2.glBindVertexArray(model.getOpenGLHandlers()[model.getOffsetOfVertexArrays() + i]);
			gl2.glInterleavedArrays(GL2.GL_T2F_N3F_V3F, 0, 0);
			OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.draw bind vertices 2 " + i);

			int indexLength = model.builder().indexLength(i);
			gl2.glDrawElements(GL2.GL_TRIANGLE_STRIP, indexLength, GL2.GL_UNSIGNED_SHORT, 0);

			OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.draw draw element" + i);

			gl2.glBindVertexArray(0);
			texture.disable(gl2);

			OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.draw dispose" + i);
		}

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.draw finish");
	}

	@Override
	public void disposeModel(GLAutoDrawable glAutodrawable, OpenGLModelContainer model) {
		GL2 gl2 = glAutodrawable.getGL().getGL2();

		gl2.glDeleteVertexArrays(model.getNumberOfVertexArrays(), model.getOpenGLHandlers(), model.getOffsetOfVertexArrays());
		gl2.glDeleteBuffers(model.getNumberOfIndexBuffers(), model.getOpenGLHandlers(), model.getOffsetOfIndexBuffers());
		gl2.glDeleteBuffers(model.getNumberOfVertexArrays(), model.getOpenGLHandlers(), model.getOffsetOfVertexBuffers());

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.dispose");
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLProfileGL2GLUAdapter.class);
}
