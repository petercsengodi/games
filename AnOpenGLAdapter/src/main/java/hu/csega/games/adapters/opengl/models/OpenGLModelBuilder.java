package hu.csega.games.adapters.opengl.models;

import java.util.List;

import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectVertex;

public class OpenGLModelBuilder {

	private OpenGLModelStoreImpl store;
	private GameObjectHandler textureReference;
	private GameModelBuilder builder;

	private int[] numberOfVertices;
	private int[] numberOfIndices;

	public OpenGLModelBuilder(GameModelBuilder builder, OpenGLModelStoreImpl store) {
		textureReference = builder.getTextureHandler(); // "res/example/texture.png"
		this.builder = builder;
		this.store = store;

		int vertexIndex = 0;
		this.numberOfVertices = new int[1];
		this.numberOfVertices[vertexIndex] = builder.getVertices().size();

		int indexIndex = 0;
		this.numberOfIndices = new int[1];
		this.numberOfIndices[indexIndex] = builder.getIndices().size();
	}

	public int numberOfShapes() {
		return 1;
	}

	public int numberOfVertices(int i) {
		return numberOfVertices[i];
	}

	public int numberOfIndices(int i) {
		return numberOfIndices[i];
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
