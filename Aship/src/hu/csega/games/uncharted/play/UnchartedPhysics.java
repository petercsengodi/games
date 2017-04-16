package hu.csega.games.uncharted.play;

import hu.csega.games.engine.GameControl;
import hu.csega.games.engine.GamePhysics;
import hu.csega.games.engine.impl.GameKeyListener;
import hu.csega.games.engine.impl.GameObject;
import hu.csega.games.uncharted.objects.UnchartedPlayer;
import hu.csega.games.uncharted.objects.UnchartedPlayerBullet;

public class UnchartedPhysics implements GamePhysics, GameKeyListener {

	public void setGameControl(GameControl control) {
		this.control = control;
		this.control.registerKeyListener(this);
	}

	@Override
	public void hit(char key) {
	}

	@Override
	public void call(long nanoTimeNow, long nanoTimeLastTime) {
		if(control == null)
			return;

		double delta = (nanoTimeNow - nanoTimeLastTime) / 1_000_000_000.0;

		UnchartedPlayer player = universe.player;

		if(control.isUpOn() && !control.isDownOn())
			player.movementAcceleration.y = -verticalForce;
		else if(control.isDownOn() && !control.isUpOn())
			player.movementAcceleration.y = verticalForce;
		else
			player.movementAcceleration.y = 0;

		if(control.isLeftOn() && !control.isRightOn())
			player.movementAcceleration.x = -horizontalForce;
		else if(control.isRightOn() && !control.isLeftOn())
			player.movementAcceleration.x = horizontalForce;
		else
			player.movementAcceleration.x = 0;

		player.move(delta);
		player.checkConstraints();

		for(GameObject obj : universe.playerBullets) {
			obj.move(delta);
			obj.checkConstraints();
		}

		if(player.timeBeforeShoot > 0.0) {
			player.timeBeforeShoot -= delta;
		}

		if(player.timeBeforeShoot <= 0.0 && control.isControlOn()) {
			UnchartedPlayerBullet bullet = new UnchartedPlayerBullet(universe);
			bullet.movementPosition.x = player.movementPosition.x + player.outerBox.maxX;
			bullet.movementPosition.y = player.movementPosition.y + (player.outerBox.minY + player.outerBox.maxY) / 2;
			bullet.movementSpeed.x = 400;
			universe.playerBullets.add(bullet);
			player.timeBeforeShoot = 0.15;
		}
	}

	public UnchartedUniverse universe;

	private double verticalForce = 200;
	private double horizontalForce = 200;

	private GameControl control;
}
