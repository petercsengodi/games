package hu.csega.games.adapters.opengl.models;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.util.GLBuffers;

import gl3.helloTexture.Semantic;
import hu.csega.games.adapters.opengl.BufferUtils;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public abstract class OpenGLModelContainer implements OpenGLObjectContainer {

	private String filename;
	private boolean initialized = false;
	private int[] openGLHandlers;
	private OpenGLModelStoreImpl store;

	private int numberOfHandlers;

	private int numberOfVertexBuffers;
	private int numberOfIndexBuffers;
	private int numberOfVertexArrays;

	private int offsetOfVertexBuffers;
	private int offsetOfIndexBuffers;
	private int offsetOfVertexArrays;

	public OpenGLModelContainer(String filename, OpenGLModelStoreImpl store) {
		this.filename = filename;
		this.store = store;
	}

	@Override
	public String filename() {
		return filename;
	}

	@Override
	public boolean isInitialized() {
		return initialized;
	}

	@Override
	public void initialize(GLAutoDrawable glAutodrawable) {
		logger.trace("Initialing model: " + filename);

		OpenGLModelBuilder model = builder();

		if(openGLHandlers == null) {
			numberOfVertexBuffers = model.numberOfVertexBuffers();
			numberOfIndexBuffers = model.numberOfIndexBuffers();
			numberOfVertexArrays = model.numberOfVertexArrays();

			offsetOfVertexBuffers = 0;
			offsetOfIndexBuffers = numberOfVertexBuffers;
			offsetOfVertexArrays = numberOfVertexBuffers + numberOfIndexBuffers;

			numberOfHandlers = numberOfVertexBuffers + numberOfIndexBuffers + numberOfVertexArrays;
			openGLHandlers = new int[numberOfHandlers];
		}

		try {

			GL3 gl3 = glAutodrawable.getGL().getGL3();
			createVertexBuffers(gl3);
			createIndexBuffers(gl3);
			createVertexArrays(gl3);

		} catch (Exception ex) {
			logger.error("Exception in model initialization: " + filename, ex);
		}

		initialized = true;
		logger.trace("Initialized model: " + filename);
	}

	@Override
	public void dispose(GLAutoDrawable glAutodrawable) {
		logger.trace("Disposing model: " + filename);
		GL3 gl3 = glAutodrawable.getGL().getGL3();

		gl3.glDeleteVertexArrays(numberOfVertexArrays, openGLHandlers, offsetOfVertexArrays);
		gl3.glDeleteBuffers(numberOfIndexBuffers, openGLHandlers, offsetOfIndexBuffers);
		gl3.glDeleteBuffers(numberOfVertexArrays, openGLHandlers, offsetOfVertexBuffers);

		initialized = false;
		logger.trace("Disposed model: " + filename);
	}

	public void draw(GLAutoDrawable glAutodrawable) {
	    float[] zRotation = new float[16];
		zRotation = FloatUtil.makeRotationEuler(zRotation, 0, 0, 0, 0 /* diff */);

		GL3 gl3 = glAutodrawable.getGL().getGL3();

		for(int i = 0; i < numberOfVertexArrays; i++) {
			gl3.glBindVertexArray(openGLHandlers[offsetOfVertexArrays + i]);

			int textureIndex = builder().textureIndex(i);
			int indexLength = builder().indexLength(i);

	        gl3.glUniformMatrix4fv(store.modelToClipMatrix(), 1, false, zRotation, 0);
	        gl3.glBindSampler(OpenGLSampler.DIFFUSE, store.samplerIndex());
			gl3.glActiveTexture(GL3.GL_TEXTURE0 + OpenGLSampler.DIFFUSE);
			gl3.glBindTexture(GL3.GL_TEXTURE_2D, textureIndex);

			gl3.glDrawElements(GL3.GL_TRIANGLES, indexLength, GL3.GL_UNSIGNED_SHORT, 0);

			gl3.glBindTexture(GL3.GL_TEXTURE_2D, 0);
			gl3.glBindVertexArray(0);
		}
	}

	protected abstract OpenGLModelBuilder builder();

	private void createVertexBuffers(GL3 gl3) {
		gl3.glGenBuffers(numberOfVertexBuffers, openGLHandlers, offsetOfVertexBuffers);
		for(int i = 0; i < numberOfVertexBuffers; i++) {
			gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, openGLHandlers[offsetOfVertexBuffers + i]);

			float[] vertexData = builder().vertexData(i);
			int size = vertexData.length * Float.BYTES;

			FloatBuffer vertexBuffer = GLBuffers.newDirectFloatBuffer(vertexData);
			gl3.glBufferData(GL3.GL_ARRAY_BUFFER, size, vertexBuffer, GL3.GL_STATIC_DRAW);
			BufferUtils.destroyDirectBuffer(vertexBuffer);

			gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
		}

		OpenGLErrorUtil.checkError(gl3, "createVertexBuffers");
	}

	private void createIndexBuffers(GL3 gl3) {
		gl3.glGenBuffers(numberOfIndexBuffers, openGLHandlers, offsetOfIndexBuffers);
		for(int i = 0; i < numberOfIndexBuffers; i++) {
			gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, openGLHandlers[offsetOfIndexBuffers + i]);

			short[] indexData = builder().indexData(i);
			int size = indexData.length * Short.BYTES;

			ShortBuffer indexBuffer = GLBuffers.newDirectShortBuffer(indexData);
			gl3.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, size, indexBuffer, GL3.GL_STATIC_DRAW);
			BufferUtils.destroyDirectBuffer(indexBuffer);

			gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, 0);
		}

		OpenGLErrorUtil.checkError(gl3, "createIndexBuffers");
	}

	private void createVertexArrays(GL3 gl3) {
		int stride = (2 + 2) * Float.BYTES;

		gl3.glGenVertexArrays(numberOfVertexArrays, openGLHandlers, offsetOfVertexArrays);
		for(int i = 0; i < numberOfVertexArrays; i++) {
			gl3.glBindVertexArray(openGLHandlers[offsetOfVertexArrays + i]);
			gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, openGLHandlers[offsetOfIndexBuffers + i]);

			gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, openGLHandlers[offsetOfVertexBuffers + i]);

			// TODO csega: remove outer reference to Attr
			gl3.glEnableVertexAttribArray(Semantic.Attr.POSITION);
			gl3.glVertexAttribPointer(Semantic.Attr.POSITION, 2, GL3.GL_FLOAT,
					false, stride, 0 * Float.BYTES);

			gl3.glEnableVertexAttribArray(Semantic.Attr.TEXCOORD);
			gl3.glVertexAttribPointer(Semantic.Attr.TEXCOORD, 2, GL3.GL_FLOAT,
					false, stride, 2 * Float.BYTES);

			gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);

			gl3.glBindVertexArray(0);
		}

		OpenGLErrorUtil.checkError(gl3, "createVertexArrays");
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLModelContainer.class);
}
