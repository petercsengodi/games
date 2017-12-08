package hu.csega.games.engine.g3d;

import java.io.Serializable;

public class GameObjectLocation implements Serializable {

	public GameObjectPosition position = new GameObjectPosition();
	public GameObjectRotation rotation = new GameObjectRotation();

	public void copyValuesFrom(GameObjectLocation other) {
		position.copyValuesFrom(other.position);
		rotation.copyValuesFrom(other.rotation);
	}

	private static final long serialVersionUID = 1L;
}
