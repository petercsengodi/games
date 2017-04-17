package hu.csega.games.adapters.opengl.models;

import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameObjectHandler;

public class OpenGLModelBuilder {

	public OpenGLModelBuilder(GameModelBuilder builder, OpenGLModelStoreImpl store) {
		textureReference = store.loadTexture("res/example/texture.png");
		this.store = store;
	}

	public int numberOfVertexBuffers() {
		return 1;
	}

	public int numberOfIndexBuffers() {
		return 1;
	}

	public int numberOfVertexArrays() {
		return 1;
	}

	public float[] vertexData(int i) {
		return vertexData;
	}

	public short[] indexData(int i) {
		return indexData;
	}

	public int textureIndex(int i) {
		OpenGLTextureContainer texture = store.resolveTexture(textureReference);
		return texture.getTextureHandler();
	}

	public int indexLength(int i) {
		return indexData.length;
	}

	private OpenGLModelStoreImpl store;
	private GameObjectHandler textureReference;

    private float[] vertexData = new float[] {
        -0.5f, -0.5f, 0f, 0f,
        -0.5f, +0.5f, 0f, 1f,
        +0.5f, +0.5f, 1f, 1f,
        +0.5f, -0.5f, 1f, 0f
    };

    private short[] indexData = new short[] {
        0, 1, 3,
        1, 2, 3
    };
}
