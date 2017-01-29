package hu.csega.klongun.swing;

public class KlonGunControl {

	public boolean isUpOn() {
		return upIsOn;
	}

	public boolean isDownOn() {
		return downIsOn;
	}

	public boolean isLeftOn() {
		return leftIsOn;
	}

	public boolean isRightOn() {
		return rightIsOn;
	}

	public boolean isControlOn() {
		return controlIsOn;
	}

	public boolean isAltOn() {
		return altIsOn;
	}

	public boolean isShiftOn() {
		return shiftIsOn;
	}

	public boolean isEscapeOn() {
		return escapeIsOn;
	}

	boolean upIsOn = false;
	boolean downIsOn = false;
	boolean leftIsOn = false;
	boolean rightIsOn = false;
	boolean controlIsOn = false;
	boolean altIsOn = false;
	boolean shiftIsOn = false;
	boolean escapeIsOn = false;

}
