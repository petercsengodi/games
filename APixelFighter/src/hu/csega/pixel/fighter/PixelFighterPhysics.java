package hu.csega.pixel.fighter;

import hu.csega.game.engine.GameControl;
import hu.csega.game.engine.GameObject;
import hu.csega.game.engine.GamePhysics;

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
