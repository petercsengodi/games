package hu.csega.games.engine.g3d;

import java.io.Serializable;

public class GameObjectLocation implements Serializable {

	public GameObjectPosition position = new GameObjectPosition();
	public GameObjectRotation rotation = new GameObjectRotation();
	public GameObjectScale scale = new GameObjectScale(1f, 1f, 1f);

	public void copyValuesFrom(GameObjectLocation other) {
		position.copyValuesFrom(other.position);
		rotation.copyValuesFrom(other.rotation);
		scale.copyValuesFrom(other.scale);
	}

	private static final long serialVersionUID = 1L;
}
