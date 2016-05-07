package hu.csega.alpoc.game.physics;

import hu.csega.alpoc.frame.AlpocControll;
import hu.csega.alpoc.game.processes.EngineProcess;
import hu.csega.alpoc.terrain.Camera;

public class PhysicsProcess extends EngineProcess {

	@Override
	protected void processIt() {
		checkMovement();
	}


	public void checkMovement() {
		changed = false;

		if(AlpocControll.leftIsOn) {
			C.alfa += 1;
			changed = true;
		}

		if(AlpocControll.rightIsOn) {
			C.alfa -= 1;
			changed = true;
		}

		if(C.alfa < -10000) {
			C.alfa += Math.PI * 100;
		}

		if(C.alfa > 10000) {
			C.alfa -= Math.PI * 100;
		}

		if(changed)
			C.recalculateMatrix();
	}

	private static boolean changed = false;
	public static final Camera C = Camera.CAMERA;
}
