package hu.csega.games.rotary.objects;

import hu.csega.games.engine.GameField;
import hu.csega.games.engine.g2d.GameHitCircle;

public class RotaryPlayer extends RotaryObject {

	public RotaryPlayer(GameField gameField) {
		super(gameField);

		hitShapes.add(new GameHitCircle(0, 0, 50));
		outerBox.minX = -50;
		outerBox.minY = -50;
		outerBox.maxX = 50;
		outerBox.maxY = 50;
	}

	public void adjustGravitationalPull(double delta) {
		if(turning) {
			if(gravitationalPullToReach < gravitationalPull) {
				gravitationalPull -= delta * GRAVITATIONAL_PULL_TURNING;
				if(gravitationalPull < gravitationalPullToReach) {
					gravitationalPull = gravitationalPullToReach;
					turning = false;
				}
			} else if(gravitationalPullToReach > gravitationalPull) {
				gravitationalPull += delta * GRAVITATIONAL_PULL_TURNING;
				if(gravitationalPull > gravitationalPullToReach) {
					gravitationalPull = gravitationalPullToReach;
					turning = false;
				}
			}
		}

		if(!turning) {
			while(gravitationalPull < -FULL_CIRCLE)
				gravitationalPull += FULL_CIRCLE;
			while(gravitationalPull > FULL_CIRCLE)
				gravitationalPull -= FULL_CIRCLE;
		}
	}

	@Override
	public void checkConstraints() {

		// ??? collision detection?
	}



	public double gravitationalPull = 0.0; // angle
	public double gravitationalPullToReach = 0.0;
	public boolean turning = false;

	private static final double FULL_CIRCLE = Math.PI * 2;
	private static final double GRAVITATIONAL_PULL_TURNING = 3;
}
