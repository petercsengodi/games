package hu.csega.superstition.game;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.intf.GameGraphics;

public class SuperstitionGameRenderer {

	public void renderGame(GameEngineFacade facade, SuperstitionSerializableModel universe, SuperstitionGamePlayElements elements) {
		GameGraphics g = facade.graphics();

		SuperstitionPlayer player = universe.player;

		GameObjectLocation cameraLocation = new GameObjectLocation();
		cameraLocation.position.x = (float)player.x;
		cameraLocation.position.y = (float)player.y;
		cameraLocation.position.z = (float)player.z;
		cameraLocation.forward.x = (float)(1.0 * Math.cos(player.movingRotation + player.sightHorizontalRotation));
		cameraLocation.forward.y = 0;
		cameraLocation.forward.z = (float)(1.0 * Math.sin(player.movingRotation + player.sightHorizontalRotation));
		cameraLocation.up.x = (float)(1.0 * Math.sin(player.sightVerticalRotation) * Math.cos(player.movingRotation + player.sightHorizontalRotation));
		cameraLocation.up.y = (float)(1.0 * Math.cos(player.sightVerticalRotation));
		cameraLocation.up.z = (float)(1.0 * Math.sin(player.sightVerticalRotation) * Math.sin(player.movingRotation + player.sightHorizontalRotation));
		g.placeCamera(cameraLocation);

		g.drawModel(elements.groundHandler, universe.groundLocation);
	}

}
