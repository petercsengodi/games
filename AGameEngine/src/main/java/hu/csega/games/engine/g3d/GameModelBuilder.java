package hu.csega.games.engine.g3d;

import java.util.ArrayList;
import java.util.List;

public class GameModelBuilder {

	private GameObjectHandler textureHandler;
	private List<GameObjectVertex> vertices = new ArrayList<>();
	private List<Integer> indices = new ArrayList<>();

	public GameObjectHandler getTextureHandler() {
		return textureHandler;
	}

	public void setTextureHandler(GameObjectHandler textureHandler) {
		this.textureHandler = textureHandler;
	}

	public List<GameObjectVertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<GameObjectVertex> vertices) {
		this.vertices = vertices;
	}

	public List<Integer> getIndices() {
		return indices;
	}

	public void setIndices(List<Integer> indices) {
		this.indices = indices;
	}

}
