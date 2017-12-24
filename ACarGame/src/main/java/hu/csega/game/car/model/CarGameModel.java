package hu.csega.game.car.model;

import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.toolshed.framework.ToolModel;

public class CarGameModel implements ToolModel {

	private GameObjectHandler carModel;
	private GameObjectHandler carMapModel;
	private CarGameState gameState;

	public GameObjectHandler getCarModel() {
		return carModel;
	}

	public void setCarModel(GameObjectHandler ship) {
		this.carModel = ship;
	}

	public GameObjectHandler getCarMapModel() {
		return carMapModel;
	}

	public void setCarMapModel(GameObjectHandler carMapModel) {
		this.carMapModel = carMapModel;
	}

	public CarGameState getGameState() {
		return gameState;
	}

	public void setGameState(CarGameState gameState) {
		this.gameState = gameState;
	}

}
