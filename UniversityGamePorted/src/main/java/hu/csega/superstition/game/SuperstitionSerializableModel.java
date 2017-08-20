package hu.csega.superstition.game;

import java.io.Serializable;

import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectLocation;

public class SuperstitionSerializableModel implements Serializable {

	SuperstitionPlayer player = new SuperstitionPlayer();

	GameObjectHandler ground;
	GameObjectLocation groundLocation = new GameObjectLocation();
	GameObjectLocation testFTMLocation = new GameObjectLocation();
	GameObjectLocation figureFTMLocation = new GameObjectLocation();

	public SuperstitionSerializableModel() {
		groundLocation.position.set(0f, 0f, 0f);
		groundLocation.forward.set(0f, 0f, 1f);
		groundLocation.up.set(0f, 1f, 0f);

		testFTMLocation.position.set(0f, 0f, 0f);
		testFTMLocation.forward.set(0f, 0f, 1f);
		testFTMLocation.up.set(0f, 1f, 0f);

		figureFTMLocation.position.set(20f, 0f, 0f);
		figureFTMLocation.forward.set(20f, 0f, 1f);
		figureFTMLocation.up.set(0f, 1f, 0f);
	}

	private static final long serialVersionUID = 1L;
}
