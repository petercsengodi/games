package hu.csega.pixel.editor.scene;

import java.awt.Color;

public class SceneText {

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

	public String[] getText() {
		return text;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setText(String[] text) {
		this.text = text;
	}

	public void setText(String text) {
		this.text = new String[] { text };
	}

	private int x;
	private int y;
	private Color color;
	private String[] text;

}
