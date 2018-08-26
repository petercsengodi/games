package hu.csega.game.asteroid.model;

import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.toolshed.framework.ToolModel;

public class AsteroidGameModel implements ToolModel {

	private GameObjectHandler shipModel;
	private AsteroidGameState gameState;

	private GameObjectHandler asteroid1Model;
	private GameObjectHandler asteroid2Model;
	private GameObjectHandler asteroid3Model;
	private GameObjectHandler shot1Model;

	public GameObjectHandler getShipModel() {
		return shipModel;
	}

	public void setShipModel(GameObjectHandler ship) {
		this.shipModel = ship;
	}

	public AsteroidGameState getGameState() {
		return gameState;
	}

	public void setGameState(AsteroidGameState gameState) {
		this.gameState = gameState;
	}

	public GameObjectHandler getAsteroid1Model() {
		return asteroid1Model;
	}

	public void setAsteroid1Model(GameObjectHandler asteroid1Model) {
		this.asteroid1Model = asteroid1Model;
	}

	public GameObjectHandler getAsteroid2Model() {
		return asteroid2Model;
	}

	public void setAsteroid2Model(GameObjectHandler asteroid2Model) {
		this.asteroid2Model = asteroid2Model;
	}

	public GameObjectHandler getAsteroid3Model() {
		return asteroid3Model;
	}

	public void setAsteroid3Model(GameObjectHandler asteroid3Model) {
		this.asteroid3Model = asteroid3Model;
	}

	public GameObjectHandler getShot1Model() {
		return shot1Model;
	}

	public void setShot1Model(GameObjectHandler shot1Model) {
		this.shot1Model = shot1Model;
	}
}
