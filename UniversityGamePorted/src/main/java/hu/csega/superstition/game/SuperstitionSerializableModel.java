package hu.csega.superstition.game;

import java.io.Serializable;

import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectLocation;

public class SuperstitionSerializableModel implements Serializable {

	SuperstitionPlayer player = new SuperstitionPlayer();

	GameObjectHandler ground;
	GameObjectLocation groundLocation = new GameObjectLocation();

	public SuperstitionSerializableModel() {
		groundLocation.position.set(0f, 0f, 0f);
		groundLocation.forward.set(0f, 0f, 1f);
		groundLocation.up.set(0f, 1f, 0f);
	}

	private static final long serialVersionUID = 1L;
}
