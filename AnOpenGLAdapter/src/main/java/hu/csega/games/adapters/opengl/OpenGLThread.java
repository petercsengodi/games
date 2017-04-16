package hu.csega.games.adapters.opengl;

import com.jogamp.opengl.awt.GLCanvas;

import hu.csega.games.engine.GamePhysics;

public class OpenGLThread extends Thread {

	private GamePhysics physics;
	private GLCanvas canvas;

	private long nanoTimeLastTime = System.nanoTime();
	private long nanoTimeNow = System.nanoTime();

	public void setGamePhysics(GamePhysics physics) {
		this.physics = physics;
	}

	public void setGLCanvas(GLCanvas canvas) {
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
