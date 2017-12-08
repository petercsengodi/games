package hu.csega.game.asteroid.model;

import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.toolshed.framework.ToolModel;

public class AsteroidGameModel implements ToolModel {

	private GameObjectHandler shipModel;
	private AsteroidGameState gameState;

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

}
