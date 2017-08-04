package hu.csega.games.adapters.opengl.models;

import java.util.List;

import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectVertex;

public class OpenGLModelBuilder {

	private OpenGLModelStoreImpl store;
	private GameObjectHandler textureReference;
	private GameModelBuilder builder;

	public OpenGLModelBuilder(GameModelBuilder builder, OpenGLModelStoreImpl store) {
		textureReference = builder.getTextureHandler(); // "res/example/texture.png"
		this.builder = builder;
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
		List<GameObjectVertex> vertices = builder.getVertices();
		float[] ret = new float[vertices.size() * 8];

		int counter = 0;
		for(GameObjectVertex vertex : vertices) {
			ret[counter++] = vertex.position.x;
			ret[counter++] = vertex.position.y;
			ret[counter++] = vertex.position.z;
			ret[counter++] = vertex.normal.x;
			ret[counter++] = vertex.normal.y;
			ret[counter++] = vertex.normal.z;
			ret[counter++] = vertex.tex.x;
			ret[counter++] = vertex.tex.y;
		}

		return ret;
	}

	public short[] indexData(int i) {
		List<Integer> indices = builder.getIndices();
		short[] ret = new short[indices.size()];

		int counter = 0;
		for(Integer index : indices) {
			ret[counter++] = (short)index.intValue();
		}

		return ret;
	}

	public OpenGLTextureContainer textureContainer(int i) {
		OpenGLTextureContainer texture = store.resolveTexture(textureReference);
		return texture;
	}

	public int indexLength(int i) {
		return builder.getIndices().size();
	}
}
