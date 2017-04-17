package hu.csega.games.adapters.opengl.models;

import static com.jogamp.opengl.GL.GL_INVALID_ENUM;
import static com.jogamp.opengl.GL.GL_INVALID_FRAMEBUFFER_OPERATION;
import static com.jogamp.opengl.GL.GL_INVALID_OPERATION;
import static com.jogamp.opengl.GL.GL_INVALID_VALUE;
import static com.jogamp.opengl.GL.GL_NO_ERROR;
import static com.jogamp.opengl.GL.GL_OUT_OF_MEMORY;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.GLBuffers;

import gl3.helloTexture.Semantic;
import hu.csega.games.adapters.opengl.BufferUtils;
import hu.csega.games.engine.env.GameEngineException;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public abstract class OpenGLModelContainer implements OpenGLObjectContainer {

	private String filename;
	private boolean initialized = false;
	private int numberOfHandlers;
	private int offsetOfVertices;
	private int offsetOfIndices;
	private int offsetOfVertexArrays;
	private int[] openGLHandlers;

	public OpenGLModelContainer(String filename) {
		this.filename = filename;
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

		if(openGLHandlers == null) {
			numberOfHandlers = 0;
			offsetOfVertices = numberOfHandlers;
			numberOfHandlers += numberOfVertices();
			offsetOfIndices = numberOfHandlers;
			numberOfHandlers += numberOfIndices();
			offsetOfVertexArrays = numberOfHandlers;
			numberOfHandlers += numberOfVertexArrays();
			openGLHandlers = new int[numberOfHandlers];
		}

		try {

		} catch (Exception ex) {
			logger.error("Exception in texture initialization: " + filename, ex);
		}

		initialized = true;
		logger.trace("Initialized model: " + filename);
	}

	@Override
	public void dispose(GLAutoDrawable glAutodrawable) {
		logger.trace("Disposing model: " + filename);
		GL3 gl3 = glAutodrawable.getGL().getGL3();

		gl3.glDeleteVertexArrays(numberOfVertexArrays(), openGLHandlers, offsetOfVertexArrays);
		gl3.glDeleteBuffers(numberOfIndices(), openGLHandlers, offsetOfIndices);
		gl3.glDeleteBuffers(numberOfVertices(), openGLHandlers, offsetOfVertices);

		initialized = false;
		logger.trace("Disposed model: " + filename);
	}

	protected abstract int numberOfVertices();
	protected abstract int numberOfIndices();
	protected abstract int numberOfVertexArrays();

	private void initVbo(GL3 gl3) {

		gl3.glGenBuffers(1, objects, Semantic.Object.VBO);
		gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, objects[Semantic.Object.VBO]);
		{
			FloatBuffer vertexBuffer = GLBuffers.newDirectFloatBuffer(vertexData);
			int size = vertexData.length * Float.BYTES;
			gl3.glBufferData(GL3.GL_ARRAY_BUFFER, size, vertexBuffer, GL3.GL_STATIC_DRAW);
			/**
			 * Since vertexBuffer is a direct buffer, this means it is outside
			 * the Garbage Collector job and it is up to us to remove it.
			 */
			BufferUtils.destroyDirectBuffer(vertexBuffer);
		}
		gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);

		checkError(gl3, "initVbo");
	}

	private void initIbo(GL3 gl3) {

		gl3.glGenBuffers(1, objects, Semantic.Object.IBO);
		gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, objects[Semantic.Object.IBO]);
		{
			ShortBuffer indexBuffer = GLBuffers.newDirectShortBuffer(indexData);
			int size = indexData.length * Short.BYTES;
			gl3.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, size, indexBuffer, GL3.GL_STATIC_DRAW);
			BufferUtils.destroyDirectBuffer(indexBuffer);
		}
		gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, 0);

		checkError(gl3, "initIbo");
	}

	private void initVao(GL3 gl3) {
		/**
		 * Let's create the VAO and save in it all the attributes properties.
		 */
		gl3.glGenVertexArrays(1, objects, Semantic.Object.VAO);
		gl3.glBindVertexArray(objects[Semantic.Object.VAO]);
		{
			/**
			 * Ibo is part of the VAO, so we need to bind it and leave it bound.
			 */
			gl3.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, objects[Semantic.Object.IBO]);
			{
				/**
				 * VBO is not part of VAO, we need it to bind it only when we
				 * call glEnableVertexAttribArray and glVertexAttribPointer, so
				 * that VAO knows which VBO the attributes refer to, then we can
				 * unbind it.
				 */
				gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, objects[Semantic.Object.VBO]);
				{
					int stride = (2 + 2) * Float.BYTES;
					/**
					 * We draw in 2D on the xy plane, so we need just two
					 * coordinates for the position, it will be padded to vec4
					 * as (x, y, 0, 1) in the vertex shader.
					 */
					gl3.glEnableVertexAttribArray(Semantic.Attr.POSITION);
					gl3.glVertexAttribPointer(Semantic.Attr.POSITION, 2, GL3.GL_FLOAT,
							false, stride, 0 * Float.BYTES);
					/**
					 * 2D Texture coordinates.
					 */
					gl3.glEnableVertexAttribArray(Semantic.Attr.TEXCOORD);
					gl3.glVertexAttribPointer(Semantic.Attr.TEXCOORD, 2, GL3.GL_FLOAT,
							false, stride, 2 * Float.BYTES);
				}
				gl3.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
			}
		}
		gl3.glBindVertexArray(0);

		OpenGLErrorUtil.checkError(gl3, "initVao");
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLModelContainer.class);
}
