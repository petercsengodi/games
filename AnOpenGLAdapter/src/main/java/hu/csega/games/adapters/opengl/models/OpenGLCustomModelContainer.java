package hu.csega.games.adapters.opengl.models;

import hu.csega.games.adapters.opengl.OpenGLProfileAdapter;

public class OpenGLCustomModelContainer extends OpenGLModelContainer {

	public OpenGLCustomModelContainer(String filename, OpenGLModelStoreImpl store, OpenGLProfileAdapter adapter, OpenGLModelBuilder builder) {
		super(filename, store, adapter);
		this.builder = builder;
	}

	@Override
	public OpenGLModelBuilder builder() {
		return builder;
	}

	private OpenGLModelBuilder builder;
}
