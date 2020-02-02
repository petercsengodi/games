package hu.csega.games.adapters.opengl.models;

import java.util.List;

import hu.csega.games.engine.ftm.GameMesh;
import hu.csega.games.engine.ftm.GameTriangle;
import hu.csega.games.engine.ftm.GameVertex;
import hu.csega.games.engine.g3d.GameModelBuilder;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectVertex;

public class OpenGLModelBuilder {

	private OpenGLModelStoreImpl store;
	private GameObjectHandler textureReference;
	private GameModelBuilder builder;
	private GameMesh mesh;

	private int[] numberOfVertices;
	private int[] numberOfIndices;

	public OpenGLModelBuilder(GameModelBuilder builder, OpenGLModelStoreImpl store) {
		this.textureReference = builder.getTextureHandler(); // "res/example/texture.png"
		this.builder = builder;
		this.store = store;

		int vertexIndex = 0;
		this.numberOfVertices = new int[1];
		this.numberOfVertices[vertexIndex] = builder.getVertices().size();

		int indexIndex = 0;
		this.numberOfIndices = new int[1];
		this.numberOfIndices[indexIndex] = builder.getIndices().size();
	}

	public OpenGLModelBuilder(GameMesh mesh, OpenGLModelStoreImpl store) {
		this.textureReference = store.loadTexture(mesh.getTexture());
		this.store = store;
		this.mesh = mesh;

		int vertexIndex = 0;
		this.numberOfVertices = new int[1];
		this.numberOfVertices[vertexIndex] = mesh.getVertices().size();

		int indexIndex = 0;
		this.numberOfIndices = new int[1];
		this.numberOfIndices[indexIndex] = mesh.getTriangles().size() * 3;
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
		float[] ret = null;

		if(builder != null) {
			List<GameObjectVertex> vertices = builder.getVertices();
			ret = new float[vertices.size() * 8];

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
		}

		if(mesh != null) {
			List<GameVertex> vertices = mesh.getVertices();
			ret = new float[vertices.size() * 8];

			int counter = 0;
			for(GameVertex vertex : vertices) {
				ret[counter++] = vertex.getPX();
				ret[counter++] = vertex.getPY();
				ret[counter++] = vertex.getPZ();
				ret[counter++] = vertex.getNX();
				ret[counter++] = vertex.getNY();
				ret[counter++] = vertex.getNZ();
				ret[counter++] = vertex.getTX();
				ret[counter++] = vertex.getTY();
			}
		}

		return ret;
	}

	public short[] indexData(int i) {
		short[] ret = null;

		if(builder != null) {
			List<Integer> indices = builder.getIndices();
			ret = new short[indices.size()];

			int counter = 0;
			for(Integer index : indices) {
				ret[counter++] = (short)index.intValue();
			}
		}

		if(mesh != null) {
			List<GameTriangle> triangles = mesh.getTriangles();
			ret = new short[triangles.size() * 3];

			int counter = 0;
			for(GameTriangle triangle : triangles) {
				ret[counter++] = (short)triangle.getV1();
				ret[counter++] = (short)triangle.getV2();
				ret[counter++] = (short)triangle.getV3();
			}
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
