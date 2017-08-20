package hu.csega.superstition.game;

import hu.csega.games.engine.intf.GameControl;

public class SuperstitionGameModify {

	private static final double PI2 = Math.PI * 2.0;
	private static final double PLAYER_ROTATION = 0.01;
	private static final double PLAYER_FORWARD = 0.1;

	public static void modify(SuperstitionSerializableModel model, GameControl control) {

		SuperstitionPlayer player = model.player;

		if(control.isLeftOn()) {
			if(control.isShiftOn()) {
				player.x += (1.0 * Math.cos(player.movingRotation));
				player.z += (1.0 * Math.sin(player.movingRotation));
			} else {
				player.movingRotation -= PLAYER_ROTATION;
				while(player.movingRotation < PI2) {
					player.movingRotation += PI2;
				}
			}
		}

		if(control.isRightOn()) {
			if(control.isShiftOn()) {
				player.x -= (1.0 * Math.cos(player.movingRotation));
				player.z -= (1.0 * Math.sin(player.movingRotation));
			} else {
				player.movingRotation += PLAYER_ROTATION;
				while(player.movingRotation >= PI2) {
					player.movingRotation -= PI2;
				}
			}
		}

		if(control.isUpOn()) {
			player.x += (-1.0 * Math.sin(player.movingRotation));
			player.z += (1.0 * Math.cos(player.movingRotation));
		}

		if(control.isDownOn()) {
			player.x -= (-1.0 * Math.sin(player.movingRotation));
			player.z -= (1.0 * Math.cos(player.movingRotation));
		}

	}

}
