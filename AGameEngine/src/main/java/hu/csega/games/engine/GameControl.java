package hu.csega.games.engine;

import hu.csega.games.engine.impl.GameKeyListener;

public interface GameControl {

	boolean isUpOn();
	boolean isDownOn();
	boolean isLeftOn();
	boolean isRightOn();
	boolean isControlOn();
	boolean isAltOn();
	boolean isShiftOn();

	void registerKeyListener(GameKeyListener listener);
}
