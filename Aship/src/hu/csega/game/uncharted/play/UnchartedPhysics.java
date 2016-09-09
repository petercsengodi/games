package hu.csega.game.uncharted.play;

import hu.csega.game.engine.GameControl;
import hu.csega.game.engine.GameKeyListener;
import hu.csega.game.engine.GamePhysics;

public class UnchartedPhysics implements GamePhysics, GameKeyListener {

	public void setGameControl(GameControl control) {
		this.control = control;
		this.control.registerKeyListener(this);
	}

	@Override
	public void hit(char key) {
		System.out.println("hit: " + key);
	}

	@Override
	public void call(long nanoTimeNow, long nanoTimeLastTime) {
		if(control == null)
			return;

		double diff = (nanoTimeNow - nanoTimeLastTime) / 1_000_000_000.0;

		if(control.isUpOn())
			y -= velocity * diff;
		if(control.isDownOn())
			y += velocity * diff;

		if(control.isLeftOn())
			x -= velocity * diff;
		if(control.isRightOn())
			x += velocity * diff;

		if(x > 799)
			x = 799;
		if(x < 0)
			x = 0;
		if(y < 0)
			y = 0;
		if(y > 599)
			y = 599;
	}

	double velocity = 10; // pixel / sec

	double x;
	double y;

	private GameControl control;
}
