package hu.csega.games.adapters.opengl.models;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.texture.Texture;

import hu.csega.games.adapters.opengl.OpenGLProfileAdapter;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class OpenGLTextureContainer implements OpenGLObjectContainer {

	private String filename;
	private OpenGLProfileAdapter adapter;
	private boolean initialized = false;
	private int[] generatedTextureNames = new int[1]; // GL2/GL3 example
	private Texture texture; // GL2+GLU example

	public OpenGLTextureContainer(OpenGLProfileAdapter adapter, String filename) {
		this.filename = filename;
		this.adapter = adapter;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public void setTextureHandler(int handler) {
		generatedTextureNames[0] = handler;
	}

	public int[] getTextureHandlerArray() {
		return generatedTextureNames;
	}

	public int getTextureHandler() {
		if(initialized)
			return generatedTextureNames[0];
		return 0;
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
	public void initialize(GLAutoDrawable glAutoDrawable) {
		logger.trace("Initializing texture: " + filename);

		adapter.loadTexture(glAutoDrawable, filename, this);

		initialized = true;
		logger.trace("Initialized texture: " + filename);
	}

	@Override
	public void dispose(GLAutoDrawable glAutoDrawable) {
		logger.trace("Disposing texture: " + filename);

		adapter.disposeTexture(glAutoDrawable, this);

		initialized = false;
		logger.trace("Disposed texture: " + filename);
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLTextureContainer.class);
}
