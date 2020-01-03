package hu.csega.game.asteroid.model;

import java.io.Serializable;

import hu.csega.games.engine.g3d.GameObjectPlacement;

public class AsteroidGameState implements Serializable {

	public GameObjectPlacement shipPlacement = new GameObjectPlacement();

	public AsteroidGameState() {
		shipPlacement.scale.x = 0.1f;
		shipPlacement.scale.y = 0.1f;
		shipPlacement.scale.z = 0.1f;
	}

	private static final long serialVersionUID = 1L;
}
