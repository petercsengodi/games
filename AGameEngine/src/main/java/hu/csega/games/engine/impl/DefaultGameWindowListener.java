package hu.csega.games.engine.impl;

import hu.csega.games.engine.GameThread;
import hu.csega.games.engine.GameWindowListener;

public class DefaultGameWindowListener implements GameWindowListener {

	private GameThread thread;

	public DefaultGameWindowListener(GameThread thread) {
		this.thread = thread;
	}

	@Override
	public void onFinishingWork() {
		thread.interrupt();
	}

}
