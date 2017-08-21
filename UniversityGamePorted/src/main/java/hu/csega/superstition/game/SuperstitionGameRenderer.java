package hu.csega.superstition.game;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectLocation;
import hu.csega.games.engine.intf.GameGraphics;

public class SuperstitionGameRenderer {

	public void renderGame(GameEngineFacade facade, SuperstitionSerializableModel universe, SuperstitionGameElements elements) {
		GameGraphics g = facade.graphics();

		SuperstitionPlayer player = universe.player;

		GameObjectLocation cameraLocation = new GameObjectLocation();
		cameraLocation.position.x = (float)player.x;
		cameraLocation.position.y = (float)player.y;
		cameraLocation.position.z = (float)player.z;
		cameraLocation.rotation.x = (float)(player.movingRotation + player.sightHorizontalRotation);
		cameraLocation.rotation.y = (float)(player.sightVerticalRotation);
		g.placeCamera(cameraLocation);

		g.drawModel(elements.groundHandler, universe.groundLocation);
		g.drawModel(elements.testFTMModel, universe.testFTMLocation);
		g.drawModel(elements.figureFTMModel, universe.figureFTMLocation);
		g.drawModel(elements.faceFTMModel, universe.faceFTMLocation);

		g.drawModel(elements.boxModel, universe.boxLocation1);
		g.drawModel(elements.boxModel, universe.boxLocation2);
		g.drawModel(elements.boxModel, universe.boxLocation3);
		g.drawModel(elements.boxModel, universe.boxLocation4);
	}

}
