package hu.csega.superstition.game;

import java.io.Serializable;

import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectLocation;

public class SuperstitionSerializableModel implements Serializable {

	SuperstitionPlayer player = new SuperstitionPlayer();
	boolean sliding = true;

	GameObjectHandler ground;
	GameObjectLocation groundLocation = new GameObjectLocation();
	GameObjectLocation testFTMLocation = new GameObjectLocation();
	GameObjectLocation figureFTMLocation = new GameObjectLocation();
	GameObjectLocation faceFTMLocation = new GameObjectLocation();

	public SuperstitionSerializableModel() {
		groundLocation.position.set(0f, 0f, 0f);
		groundLocation.rotation.set(0f, 0f, 0f);

		testFTMLocation.position.set(0f, 0f, 0f);
		testFTMLocation.rotation.set(0f, 0f, 0f);

		figureFTMLocation.position.set(20f, 0f, 0f);
		figureFTMLocation.rotation.set(0f, 0f, 0f);

		faceFTMLocation.position.set(100f, 0f, 100f);
		faceFTMLocation.rotation.set((float)(Math.PI / 2.0), 0f, 0f);
	}

	private static final long serialVersionUID = 1L;
}
