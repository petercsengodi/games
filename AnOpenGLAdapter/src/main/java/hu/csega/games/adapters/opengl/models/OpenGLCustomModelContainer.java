package hu.csega.games.adapters.opengl.models;

public class OpenGLCustomModelContainer extends OpenGLModelContainer {

	public OpenGLCustomModelContainer(String filename, OpenGLModelStoreImpl store, OpenGLModelBuilder builder) {
		super(filename, store);
		this.builder = builder;
	}

	@Override
	protected OpenGLModelBuilder builder() {
		return builder;
	}

	private OpenGLModelBuilder builder;
}
