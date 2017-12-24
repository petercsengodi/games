package hu.csega.game.car.model;

import java.io.Serializable;

import hu.csega.games.engine.g3d.GameObjectLocation;

public class CarGameState implements Serializable {

	public GameObjectLocation carLocation = new GameObjectLocation();
	public GameObjectLocation groundLocation = new GameObjectLocation();
	public CarGameMap map = new CarGameMap();

	private static final long serialVersionUID = 1L;
}
