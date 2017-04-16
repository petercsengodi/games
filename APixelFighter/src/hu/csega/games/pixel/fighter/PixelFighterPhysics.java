package hu.csega.games.pixel.fighter;

import hu.csega.games.engine.GameControl;
import hu.csega.games.engine.GamePhysics;
import hu.csega.games.engine.impl.GameObject;

public class PixelFighterPhysics implements GamePhysics {

	@Override
	public void call(long nanoTimeNow, long nanoTimeLastTime) {

	}

	@Override
	public void setGameControl(GameControl control) {
		this.control = control;
	}

	public void setPlayer(GameObject player) {
		this.player = player;
	}

	public GameObject getPlayer() {
		return player;
	}

	public boolean punch() {
		return control != null && control.isControlOn();
	}

	private GameObject player;
	private GameControl control;
}
