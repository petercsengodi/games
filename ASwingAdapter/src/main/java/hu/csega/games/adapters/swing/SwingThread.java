package hu.csega.games.adapters.swing;

import hu.csega.games.engine.GamePhysics;

public class SwingThread extends Thread {

	private GamePhysics physics;
	private SwingCanvas canvas;

	private long nanoTimeLastTime = System.nanoTime();
	private long nanoTimeNow = System.nanoTime();

	public void setGamePhysics(GamePhysics physics) {
		this.physics = physics;
	}

	public void setSwingCanvas(SwingCanvas canvas) {
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
