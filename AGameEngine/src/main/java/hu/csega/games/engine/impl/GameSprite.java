package hu.csega.games.engine.impl;

import java.awt.image.BufferedImage;

public class GameSprite {

	public GameSprite(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	private BufferedImage image;
}
