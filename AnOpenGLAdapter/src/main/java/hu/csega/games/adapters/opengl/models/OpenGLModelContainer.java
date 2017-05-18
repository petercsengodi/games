package hu.csega.games.adapters.opengl.models;

import com.jogamp.opengl.GLAutoDrawable;

import hu.csega.games.adapters.opengl.OpenGLProfileAdapter;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public abstract class OpenGLModelContainer implements OpenGLObjectContainer {

	private String filename;
	private boolean initialized = false;
	private int[] openGLHandlers;
	private OpenGLModelStoreImpl store;
	private OpenGLProfileAdapter adapter;

	private int numberOfHandlers;

	private int numberOfVertexBuffers;
	private int numberOfIndexBuffers;
	private int numberOfVertexArrays;

	private int offsetOfVertexBuffers;
	private int offsetOfIndexBuffers;
	private int offsetOfVertexArrays;

	public OpenGLModelContainer(String filename, OpenGLModelStoreImpl store, OpenGLProfileAdapter adapter) {
		this.filename = filename;
		this.store = store;
		this.adapter = adapter;
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

		adapter.loadModel(glAutoDrawable, filename, this);

		initialized = true;
		logger.trace("Initialized model: " + filename);
	}

	@Override
	public void dispose(GLAutoDrawable glAutodrawable) {
		logger.trace("Disposing model: " + filename);

		adapter.disposeModel(glAutodrawable, this);

		initialized = false;
		logger.trace("Disposed model: " + filename);
	}

	public void draw(GLAutoDrawable glAutoDrawable) {
		adapter.drawModel(glAutoDrawable, this, store);
	}

	public abstract OpenGLModelBuilder builder();



	public int[] getOpenGLHandlers() {
		return openGLHandlers;
	}

	public int getNumberOfHandlers() {
		return numberOfHandlers;
	}

	public int getNumberOfVertexBuffers() {
		return numberOfVertexBuffers;
	}

	public int getNumberOfIndexBuffers() {
		return numberOfIndexBuffers;
	}

	public int getNumberOfVertexArrays() {
		return numberOfVertexArrays;
	}

	public int getOffsetOfVertexBuffers() {
		return offsetOfVertexBuffers;
	}

	public int getOffsetOfIndexBuffers() {
		return offsetOfIndexBuffers;
	}

	public int getOffsetOfVertexArrays() {
		return offsetOfVertexArrays;
	}

	private static final Logger logger = LoggerFactory.createLogger(OpenGLModelContainer.class);
}
