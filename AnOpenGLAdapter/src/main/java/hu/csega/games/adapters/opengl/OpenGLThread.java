package hu.csega.games.adapters.opengl;

import hu.csega.games.engine.GameCanvas;
import hu.csega.games.engine.GamePhysics;
import hu.csega.games.engine.GameThread;

public class OpenGLThread extends Thread implements GameThread {

	private GamePhysics physics;
	private GameCanvas canvas;

	private long nanoTimeLastTime = System.nanoTime();
	private long nanoTimeNow = System.nanoTime();

	public OpenGLThread(GamePhysics physics, GameCanvas canvas) {
		this.physics = physics;
		this.canvas = canvas;
	}

	@Override
	public void run() {
		try {

			while(true) {
				nanoTimeNow = System.nanoTime();
				physics.call(nanoTimeNow, nanoTimeLastTime);
				nanoTimeLastTime = nanoTimeNow;
				canvas.repaint();
				Thread.sleep(10);
			}

		} catch(InterruptedException ex) {
			return;
		}
	}

}