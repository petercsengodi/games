package hu.csega.game.adapters.swing;

import java.util.ArrayList;
import java.util.List;

import hu.csega.game.engine.GameControl;
import hu.csega.game.engine.GameKeyListener;

public class SwingControl implements GameControl {

	@Override
	public boolean isUpOn() {
		return upIsOn;
	}

	@Override
	public boolean isDownOn() {
		return downIsOn;
	}

	@Override
	public boolean isLeftOn() {
		return leftIsOn;
	}

	@Override
	public boolean isRightOn() {
		return rightIsOn;
	}

	@Override
	public boolean isControlOn() {
		return controlIsOn;
	}

	@Override
	public boolean isAltOn() {
		return altIsOn;
	}

	@Override
	public boolean isShiftOn() {
		return shiftIsOn;
	}

	@Override
	public void registerKeyListener(GameKeyListener listener) {
		listeners.add(listener);
	}

	void hit(char key) {
		for(GameKeyListener listener : listeners)
			listener.hit(key);
	}

	boolean upIsOn = false;
	boolean downIsOn = false;
	boolean leftIsOn = false;
	boolean rightIsOn = false;
	boolean controlIsOn = false;
	boolean altIsOn = false;
	boolean shiftIsOn = false;

	private List<GameKeyListener> listeners = new ArrayList<>();

}
