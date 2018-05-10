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
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.g3d.GameObjectPosition;
import hu.csega.games.engine.g3d.GameObjectRotation;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLProfileGL2GLUAdapter implements OpenGLProfileAdapter {

	private static final float RAD = (float)(180.0 / Math.PI);

	private GL2 gl2 = null;
	private GLU glu = null;

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
		gl2 = glAutoDrawable.getGL().getGL2();
		glu = GLU.createGLU(gl2);

		gl2.glEnable(GL2.GL_DEPTH_TEST);
		gl2.glEnable(GL2.GL_TEXTURE_2D);

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

		// gl2.glPushMatrix();

		OpenGLErrorUtil.checkError(gl2, "modelViewMatrix");

		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		gl2.glMatrixMode(GL.GL_TEXTURE);

		OpenGLErrorUtil.checkError(gl2, "startFrame");
	}

	@Override
	public void endFrame(GLAutoDrawable glAutoDrawable) {
		// gl2.glPopMatrix();
		// OpenGLErrorUtil.checkError(gl2, "pop");

		gl2.glFlush();

		OpenGLErrorUtil.checkError(gl2, "endFrame");

		gl2 = null;
		glu = null;
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
		gl2.glGenBuffers(model.getNumberOfShapes(), model.getOpenGLHandlers(), model.getOffsetOfVertexBuffers());
		for(int i = 0; i < model.getNumberOfShapes(); i++) {
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
		gl2.glGenBuffers(model.getNumberOfShapes(), model.getOpenGLHandlers(), model.getOffsetOfIndexBuffers());
		for(int i = 0; i < model.getNumberOfShapes(); i++) {
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

		gl2.glGenVertexArrays(model.getNumberOfShapes(), model.getOpenGLHandlers(), model.getOffsetOfVertexArrays());
		for(int i = 0; i < model.getNumberOfShapes(); i++) {
			gl2.glBindVertexArray(model.getOpenGLHandlers()[model.getOffsetOfVertexArrays() + i]);
			gl2.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, model.getOpenGLHandlers()[model.getOffsetOfIndexBuffers() + i]);
			gl2.glBindBuffer(GL2.GL_ARRAY_BUFFER, model.getOpenGLHandlers()[model.getOffsetOfVertexBuffers() + i]);
			gl2.glBindVertexArray(0);
		}

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.createVertexArrays");
	}

	@Override
	public void drawModel(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, GameObjectLocation location, OpenGLModelStoreImpl store) {
		gl2.glPushMatrix();

		GameObjectPosition lp = location.position;
		gl2.glTranslatef(lp.x, lp.y, lp.z);
		gl2.glRotatef(location.rotation.z * RAD, 0f, 0f, 1f);
		gl2.glRotatef(location.rotation.y * RAD, 1f, 0f, 0f);
		gl2.glRotatef(location.rotation.x * RAD, 0f, 1f, 0f);

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.draw init");

		for(int i = 0; i < model.getNumberOfShapes(); i++) {
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

		gl2.glPopMatrix();

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.draw finish");
	}

	@Override
	public void disposeModel(GLAutoDrawable glAutodrawable, OpenGLModelContainer model) {
		GL2 gl2 = glAutodrawable.getGL().getGL2();

		gl2.glDeleteVertexArrays(model.getNumberOfShapes(), model.getOpenGLHandlers(), model.getOffsetOfVertexArrays());
		gl2.glDeleteBuffers(model.getNumberOfShapes(), model.getOpenGLHandlers(), model.getOffsetOfIndexBuffers());
		gl2.glDeleteBuffers(model.getNumberOfShapes(), model.getOpenGLHandlers(), model.getOffsetOfVertexBuffers());

		OpenGLErrorUtil.checkError(gl2, "OpenGLModelContainer.dispose");
	}

	@Override
	public void loadAnimation(GLAutoDrawable glAutoDrawable, String filename, OpenGLModelContainer model) {
	}

	@Override
	public void drawAnimation(GLAutoDrawable glAutoDrawable, OpenGLModelContainer model, OpenGLModelStoreImpl store) {
	}

	@Override
	public void disposeAnimation(GLAutoDrawable glAutodrawable, OpenGLModelContainer model) {
	}

	@Override
	public void placeCamera(GLAutoDrawable glAutodrawable, GameObjectLocation cameraSettings) {
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		OpenGLErrorUtil.checkError(gl2, "modelViewMatrix");

		GameObjectPosition p = cameraSettings.position;
		GameObjectRotation r = cameraSettings.rotation;

		double f0x = 0f;
		double f0y = 0f;
		double f0z = 1f;

		double f1x = f0x * Math.cos(r.z) - f0y * Math.sin(r.z);
		double f1y = f0x * Math.sin(r.z) + f0y * Math.cos(r.z);
		double f1z = f0z;

		double f2x = f1x * Math.cos(r.y) - f1z * Math.sin(r.y);
		double f2y = f1y;
		double f2z = f1x * Math.sin(r.y) + f1z * Math.cos(r.y);

		double f3x = f2x;
		double f3y = f2y * Math.cos(r.x) - f2z * Math.sin(r.x);
		double f3z = f2y * Math.sin(r.x) + f2z * Math.cos(r.x);

		float fx = (float)f3x + p.x;
		float fy = (float)f3y + p.y;
		float fz = (float)f3z + p.z;

		double u0x = 0f;
		double u0y = 1f;
		double u0z = 0f;

		double u1x = u0x * Math.cos(r.z) - u0y * Math.sin(r.z);
		double u1y = u0x * Math.sin(r.z) + u0y * Math.cos(r.z);
		double u1z = u0z;

		double u2x = u1x * Math.cos(r.y) - u1z * Math.sin(r.y);
		double u2y = u1y;
		double u2z = u1x * Math.sin(r.y) + u1z * Math.cos(r.y);

		double u3x = u2x;
		double u3y = u2y * Math.cos(r.x) - u2z * Math.sin(r.x);
		double u3z = u2y * Math.sin(r.x) + u2z * Math.cos(r.x);

		float ux = (float)u3x;
		float uy = (float)u3y;
		float uz = (float)u3z;

		glu.gluLookAt(
				p.x, p.y, p.z,
				fx, fy, fz,
				ux, uy, uz
				);
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLProfileGL2GLUAdapter.class);
}
