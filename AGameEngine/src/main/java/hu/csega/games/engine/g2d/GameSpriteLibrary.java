package hu.csega.games.engine.g2d;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class GameSpriteLibrary {

	public static GameSprite load(String fileName) {
		GameSprite sprite = map.get(fileName);

		if(sprite == null) {

			BufferedImage bufferedImage;

			try {
				bufferedImage = ImageIO.read(new File(fileName));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}

			sprite = new GameSprite(bufferedImage);
			map.put(fileName, sprite);
		}

		return sprite;
	}

	private static Map<String, GameSprite> map = new HashMap<>();
}
