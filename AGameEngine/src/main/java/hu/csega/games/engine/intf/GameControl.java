package hu.csega.games.engine.intf;

/**
 * Checking of key states.
 */
public interface GameControl {

	boolean isUpOn();
	boolean isDownOn();
	boolean isLeftOn();
	boolean isRightOn();
	boolean isControlOn();
	boolean isAltOn();
	boolean isShiftOn();

	void registerKeyListener(GameKeyListener listener);
	void registerMouseListener(GameMouseListener listener);
}
