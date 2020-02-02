package hu.csega.games.adapters.opengl.models;

import com.jogamp.opengl.GLAutoDrawable;

import hu.csega.games.adapters.opengl.OpenGLProfileAdapter;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.g3d.GameTransformation;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public abstract class OpenGLModelContainer implements OpenGLObjectContainer {

	private String filename;
	private boolean initialized = false;
	private int[] openGLHandlers;
	private OpenGLModelStoreImpl store;
	private OpenGLProfileAdapter adapter;

	private int numberOfHandlers;

	private int numberOfShapes;

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
			numberOfShapes = model.numberOfShapes();

			offsetOfVertexBuffers = 0 * numberOfShapes;
			offsetOfIndexBuffers = 1 * numberOfShapes;
			offsetOfVertexArrays = 1 * numberOfShapes;

			numberOfHandlers = 3 * numberOfShapes;
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

	public void draw(GLAutoDrawable glAutoDrawable, GameObjectPlacement placement) {
		adapter.drawModel(glAutoDrawable, this, placement, store);
	}

	public void draw(GLAutoDrawable glAutoDrawable, GameTransformation transformation) {
		adapter.drawModel(glAutoDrawable, this, transformation, store);
	}

	public abstract OpenGLModelBuilder builder();

	public int[] getOpenGLHandlers() {
		return openGLHandlers;
	}

	public int getNumberOfHandlers() {
		return numberOfHandlers;
	}

	public int getNumberOfShapes() {
		return numberOfShapes;
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
