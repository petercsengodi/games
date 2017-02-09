package hu.csega.klongun.sprites;

import hu.csega.klongun.screen.Picture;

public class Rotation {

	public void setPicture(Picture pictureToRotate) {
		this.pictureToRotate = pictureToRotate;
		this.middleX = pictureToRotate.getMidX();
		this.middleY = pictureToRotate.getMidY();
	}

	public void setRotation(double alfa) {
		rotationMap = RotationMapLibrary.get(alfa);
	}

	public int get(int x, int y) {
		int vx = rotationMap.xOf(x, y) + middleX;
		int vy = rotationMap.yOf(x, y) + middleY;

		// checking will be done by Picture
		int v = pictureToRotate.get(vx, vy);
		return (v < 0 ? 255 : v);
	}

	private int middleX;
	private int middleY;
	private Picture pictureToRotate;
	private RotationMap rotationMap;
}
