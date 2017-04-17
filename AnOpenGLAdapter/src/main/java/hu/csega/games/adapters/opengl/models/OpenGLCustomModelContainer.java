package hu.csega.games.adapters.opengl.models;

public class OpenGLCustomModelContainer extends OpenGLModelContainer {

	public OpenGLCustomModelContainer(String filename, OpenGLModelBuilder builder) {
		super(filename);
		this.builder = builder;
	}

	@Override
	protected OpenGLModelBuilder builder() {
		return builder;
	}

	private OpenGLModelBuilder builder;
}
