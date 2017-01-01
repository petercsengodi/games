package hu.csega.game.rotary.play;

import hu.csega.game.engine.GameControl;
import hu.csega.game.engine.GameKeyListener;
import hu.csega.game.engine.GameObject;
import hu.csega.game.engine.GamePhysics;
import hu.csega.game.rotary.objects.RotaryPlayer;

public class RotaryPhysics implements GamePhysics, GameKeyListener {

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

		RotaryPlayer player = universe.player;

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
	}

	public RotaryUniverse universe;

	private double verticalForce = 200;
	private double horizontalForce = 200;

	private GameControl control;
}
