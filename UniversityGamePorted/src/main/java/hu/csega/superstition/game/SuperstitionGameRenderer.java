package hu.csega.superstition.game;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.g3d.GameObjectPlacement;
import hu.csega.games.engine.intf.GameGraphics;

public class SuperstitionGameRenderer {

	private Matrix4f rotation = new Matrix4f();
	private Vector4f target = new Vector4f();
	private Vector4f up = new Vector4f();

	public void renderGame(GameEngineFacade facade, SuperstitionSerializableModel universe, SuperstitionGameElements elements) {
		GameGraphics g = facade.graphics();

		SuperstitionPlayer player = universe.player;

		GameObjectPlacement cameraLocation = new GameObjectPlacement();
		cameraLocation.position.x = (float)player.x;
		cameraLocation.position.y = (float)player.y;
		cameraLocation.position.z = (float)player.z;

		rotation.identity();
		rotation.rotateAffineXYZ(0f,
				-(float)(player.movingRotation + player.sightHorizontalRotation),
				0f);
		rotation.rotateAffineXYZ((float)(player.sightVerticalRotation),
				0f,
				0f);

		up.set(0f, 1f, 0f, 1f);
		rotation.transform(up);
		cameraLocation.up.set(up.x, up.y, up.z);

		target.set(0f, 0f, 1f, 1f);
		rotation.transform(target);
		cameraLocation.target.set(target.x + cameraLocation.position.x, target.y + cameraLocation.position.y,
				target.z + cameraLocation.position.z);

		g.placeCamera(cameraLocation);

		g.drawModel(elements.groundHandler, universe.groundPlacement);

		g.drawModel(elements.boxModel, universe.boxPlacement4);
	}

}
