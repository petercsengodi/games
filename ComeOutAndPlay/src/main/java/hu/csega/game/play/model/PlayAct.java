package hu.csega.game.play.model;

import java.io.Serializable;

public class PlayAct implements Serializable {

	private int x;
	private int y;
	private int direction;
	private String dialog;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public String getDialog() {
		return dialog;
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
	}

	private static final long serialVersionUID = 1L;

}
