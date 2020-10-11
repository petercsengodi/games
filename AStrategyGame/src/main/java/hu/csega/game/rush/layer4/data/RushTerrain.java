package hu.csega.game.rush.layer4.data;

import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;

public class RushTerrain extends RushGameObject {

	public RushTerrain(GameObjectHandler obj, double x, double y, double z) {
		this.obj = obj;
		this.placement = new GameObjectPlacement();
		this.placement.move((float)x, (float)y, (float)z);
	}

	public GameObjectHandler getGameObjectHandler() {
		return obj;
	}

	public GameObjectPlacement getPlacement() {
		return placement;
	}

	private GameObjectHandler obj;
	private GameObjectPlacement placement;
}
