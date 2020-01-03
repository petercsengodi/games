package hu.csega.superstition.game;

import java.io.Serializable;

import hu.csega.games.engine.g3d.GameObjectHandler;
import hu.csega.games.engine.g3d.GameObjectPlacement;

public class SuperstitionSerializableModel implements Serializable {

	SuperstitionPlayer player = new SuperstitionPlayer();
	boolean sliding = true;

	GameObjectHandler ground;
	GameObjectPlacement groundPlacement = new GameObjectPlacement();
	GameObjectPlacement testFTMPlacement = new GameObjectPlacement();
	GameObjectPlacement figureFTMPlacement = new GameObjectPlacement();
	GameObjectPlacement faceFTMPlacement = new GameObjectPlacement();

	GameObjectPlacement boxPlacement1 = new GameObjectPlacement();
	GameObjectPlacement boxPlacement2 = new GameObjectPlacement();
	GameObjectPlacement boxPlacement3 = new GameObjectPlacement();
	GameObjectPlacement boxPlacement4 = new GameObjectPlacement();

	public SuperstitionSerializableModel() {
		player.z = -500f;

		groundPlacement.position.set(0f, 0f, 0f);

		testFTMPlacement.position.set(0f, 0f, 0f);

		figureFTMPlacement.position.set(20f, 0f, 0f);

		faceFTMPlacement.position.set(100f, 100f, 100f);

		boxPlacement1.position.set(-120f, 10f, -20f);

		boxPlacement2.position.set(-130f, -10f, -20f);

		boxPlacement3.position.set(-140f, -30f, -20f);

		boxPlacement4.position.set(-150f, -50f, -20f);
		boxPlacement4.target.set(-150f, -50f, -10f);
	}

	private static final long serialVersionUID = 1L;
}
