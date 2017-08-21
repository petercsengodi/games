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

	GameObjectLocation boxLocation1 = new GameObjectLocation();
	GameObjectLocation boxLocation2 = new GameObjectLocation();
	GameObjectLocation boxLocation3 = new GameObjectLocation();
	GameObjectLocation boxLocation4 = new GameObjectLocation();

	public SuperstitionSerializableModel() {
		player.z = -500f;

		groundLocation.position.set(0f, 0f, 0f);
		groundLocation.rotation.set(0f, 0f, 0f);

		testFTMLocation.position.set(0f, 0f, 0f);
		testFTMLocation.rotation.set(0f, 0f, 0f);

		figureFTMLocation.position.set(20f, 0f, 0f);
		figureFTMLocation.rotation.set(0f, 0f, 0f);

		faceFTMLocation.position.set(100f, 100f, 100f);
		faceFTMLocation.rotation.set((float)(Math.PI / 2.0), 0f, 0f);

		boxLocation1.position.set(-120f, 10f, -20f);
		boxLocation1.rotation.set(0f, 0f, 0f);

		boxLocation2.position.set(-130f, -10f, -20f);
		boxLocation2.rotation.set(0f, 0f, 0f);

		boxLocation3.position.set(-140f, -30f, -20f);
		boxLocation3.rotation.set(0f, 0f, 0f);

		boxLocation4.position.set(-150f, -50f, -20f);
		boxLocation4.rotation.set(0f, 0f, 0f);
	}

	private static final long serialVersionUID = 1L;
}
