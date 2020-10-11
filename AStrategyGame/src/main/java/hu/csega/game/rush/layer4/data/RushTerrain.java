package hu.csega.game.rush.layer4.data;

import hu.csega.games.engine.g3d.GameHitBox;
import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;

public class RushTerrain extends RushGameObject {

	public RushTerrain(GameObjectHandler obj, GameHitBox hitBox, double x, double y, double z) {
		float fx = (float)x;
		float fy = (float)y;
		float fz = (float)z;

		this.obj = obj;
		this.placement = new GameObjectPlacement();
		this.placement.move(fx, fy, fz);
		this.hitBox = hitBox;
	}

	public GameObjectHandler getGameObjectHandler() {
		return obj;
	}

	public GameObjectPlacement getPlacement() {
		return placement;
	}

	public GameHitBox getHitBox() {
		return hitBox;
	}

	private GameObjectHandler obj;
	private GameObjectPlacement placement;
	private GameHitBox hitBox;
}
