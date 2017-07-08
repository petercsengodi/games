package hu.csega.games.pixel.fighter.engine;

import hu.csega.games.engine.g2d.GameObject;

public class PixelFighterModel {

	private GameObject player = new GameObject();

	public void setPlayer(GameObject player) {
		this.player = player;
	}

	public GameObject getPlayer() {
		return player;
	}
}
