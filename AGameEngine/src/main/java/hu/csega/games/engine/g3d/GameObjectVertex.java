package hu.csega.games.engine.g3d;

import java.io.Serializable;

public class GameObjectVertex implements Serializable {

	public GameObjectPosition position;
	public GameObjectDirection normal;
	public GameTexturePosition tex;

	public GameObjectVertex() {
		this.position = new GameObjectPosition();
		this.normal = new GameObjectDirection();
		this.tex = new GameTexturePosition();
	}

	public GameObjectVertex(GameObjectPosition p, GameObjectDirection d, GameTexturePosition tex) {
		this.position = p;
		this.normal = d;
		this.tex = tex;
	}

	public GameObjectVertex(float f, float g, float h, float i, float j, float k, float l, float m) {
		this.position = new GameObjectPosition(f, g, h);
		this.normal = new GameObjectDirection(i, j, k);
		this.tex = new GameTexturePosition(l, m);
	}

	public void copyValuesFrom(GameObjectVertex other) {
		position.copyValuesFrom(other.position);
		normal.copyValuesFrom(other.normal);
		tex.copyValuesFrom(other.tex);
	}

	private static final long serialVersionUID = 1L;
}
