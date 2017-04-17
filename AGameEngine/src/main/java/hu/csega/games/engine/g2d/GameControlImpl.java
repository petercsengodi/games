package hu.csega.games.engine.g2d;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.engine.GameControl;
import hu.csega.games.engine.GameKeyListener;

public class GameControlImpl implements GameControl {

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

	public void setUpIsOn(boolean upIsOn) {
		this.upIsOn = upIsOn;
	}

	public void setDownIsOn(boolean downIsOn) {
		this.downIsOn = downIsOn;
	}

	public void setLeftIsOn(boolean leftIsOn) {
		this.leftIsOn = leftIsOn;
	}

	public void setRightIsOn(boolean rightIsOn) {
		this.rightIsOn = rightIsOn;
	}

	public void setControlIsOn(boolean controlIsOn) {
		this.controlIsOn = controlIsOn;
	}

	public void setAltIsOn(boolean altIsOn) {
		this.altIsOn = altIsOn;
	}

	public void setShiftIsOn(boolean shiftIsOn) {
		this.shiftIsOn = shiftIsOn;
	}

	public void hit(char key) {
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
