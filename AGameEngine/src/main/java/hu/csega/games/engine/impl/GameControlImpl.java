package hu.csega.games.engine.impl;

import java.util.ArrayList;
import java.util.List;

import hu.csega.games.engine.GameEngineFacade;
import hu.csega.games.engine.intf.GameControl;
import hu.csega.games.engine.intf.GameKeyListener;
import hu.csega.games.engine.intf.GameMouseListener;

public class GameControlImpl implements GameControl {

	boolean upIsOn = false;
	boolean downIsOn = false;
	boolean leftIsOn = false;
	boolean rightIsOn = false;
	boolean controlIsOn = false;
	boolean altIsOn = false;
	boolean shiftIsOn = false;

	private List<GameKeyListener> keyListeners = new ArrayList<>();
	private List<GameMouseListener> mouseListeners = new ArrayList<>();

	private GameEngine engine;

	public GameControlImpl(GameEngine engine) {
		this.engine = engine;
	}

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
		keyListeners.add(listener);
	}

	@Override
	public void registerMouseListener(GameMouseListener listener) {
		mouseListeners.add(listener);
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
		GameEngineFacade facade = engine.getFacade();
		for(GameKeyListener listener : keyListeners) {
			listener.hit(facade, key);
		}
	}

	public void pressed(int x, int y, boolean lefMouseButton, boolean rightMouseButton) {
		GameEngineFacade facade = engine.getFacade();
		for(GameMouseListener listener : mouseListeners) {
			listener.pressed(facade, x, y, lefMouseButton, rightMouseButton);
		}
	}

	public void released(int x, int y, boolean lefMouseButton, boolean rightMouseButton) {
		GameEngineFacade facade = engine.getFacade();
		for(GameMouseListener listener : mouseListeners) {
			listener.released(facade, x, y, lefMouseButton, rightMouseButton);
		}
	}

	public void clicked(int x, int y, boolean lefMouseButton, boolean rightMouseButton) {
		GameEngineFacade facade = engine.getFacade();
		for(GameMouseListener listener : mouseListeners) {
			listener.clicked(facade, x, y, lefMouseButton, rightMouseButton);
		}
	}

	public void moved(int deltaX, int deltaY, boolean lefMouseButtonDown, boolean rightMouseButtonDown) {
		GameEngineFacade facade = engine.getFacade();
		for(GameMouseListener listener : mouseListeners) {
			listener.moved(facade, deltaX, deltaY, lefMouseButtonDown, rightMouseButtonDown);
		}
	}

}
