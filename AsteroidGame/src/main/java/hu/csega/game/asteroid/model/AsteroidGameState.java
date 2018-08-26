package hu.csega.game.asteroid.model;

import java.io.Serializable;

import hu.csega.games.engine.g3d.GameObjectLocation;

public class AsteroidGameState implements Serializable {

	public GameObjectLocation shipLocation = new GameObjectLocation();

	public AsteroidGameState() {
		shipLocation.scale.x = 0.1f;
		shipLocation.scale.y = 0.1f;
		shipLocation.scale.z = 0.1f;
	}

	private static final long serialVersionUID = 1L;
}
