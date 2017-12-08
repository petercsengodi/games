package hu.csega.game.asteroid.model;

import java.io.Serializable;

import hu.csega.games.engine.g3d.GameObjectLocation;

public class AsteroidGameState implements Serializable {

	public GameObjectLocation shipLocation = new GameObjectLocation();

	private static final long serialVersionUID = 1L;
}
