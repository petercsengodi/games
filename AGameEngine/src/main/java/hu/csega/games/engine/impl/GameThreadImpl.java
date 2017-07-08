package hu.csega.games.engine.impl;

import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameEngineStep;
import hu.csega.games.engine.intf.GameThread;
import hu.csega.games.engine.intf.GameTimer;
import hu.csega.toolshed.logging.Logger;
import hu.csega.toolshed.logging.LoggerFactory;

public class GameThreadImpl extends Thread implements GameThread, GameTimer {

	private GameEngine engine;
	private GameCanvas canvas;

	private long nanoTimeOnLastCall = System.nanoTime();
	private long nanoTimeNow = System.nanoTime();

	public GameThreadImpl(GameEngine engine) {
		this.engine = engine;
		this.canvas = engine.getCanvas();
	}

	@Override
	public void run() {
		try {

			while(true) {
				nanoTimeNow = System.nanoTime();

				if(engine.isRebuildRequested()) {
					logger.info("Running build step.");
					engine.runStep(GameEngineStep.BUILD, this);
					nanoTimeNow = System.nanoTime();
				} else {
					engine.runStep(GameEngineStep.MODIFY, this);
				}

				nanoTimeOnLastCall = nanoTimeNow;
				canvas.repaint();
				Thread.sleep(10);
			}

		} catch(InterruptedException ex) {
			return;
		}
	}

	@Override
	public long getNanoTimeOnLastCall() {
		return nanoTimeOnLastCall;
	}

	@Override
	public long getNanoTimeNow() {
		return nanoTimeNow;
	}

	@Override
	public double elapsedTime() {
		long elapsedTimeInNanos = nanoTimeNow - nanoTimeOnLastCall;
		double elapsedTime = elapsedTimeInNanos / 1000000000.0;
		return elapsedTime;
	}

	private static final Logger logger = LoggerFactory.createLogger(GameThreadImpl.class);
}
