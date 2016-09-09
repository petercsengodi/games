package hu.csega.game.engine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class GameSpriteLibrary {

	public static GameSprite load(Class<?> fileClass, String fileName) {
		Key key = new Key(fileClass, fileName);
		GameSprite sprite = map.get(key);

		if(sprite == null) {

			BufferedImage bufferedImage;

			try {
				bufferedImage = ImageIO.read(fileClass.getResource(fileName));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}

			sprite = new GameSprite(bufferedImage);
			map.put(key, sprite);
		}

		return sprite;
	}

	private static class Key {

		public Key(Class<?> fileClass, String fileName) {
			this.fileClass = fileClass;
			this.fileName = fileName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((fileClass == null) ? 0 : fileClass.hashCode());
			result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (fileClass == null) {
				if (other.fileClass != null)
					return false;
			} else if (!fileClass.equals(other.fileClass))
				return false;
			if (fileName == null) {
				if (other.fileName != null)
					return false;
			} else if (!fileName.equals(other.fileName))
				return false;
			return true;
		}

		Class<?> fileClass;
		String fileName;
	}

	private static Map<Key, GameSprite> map = new HashMap<>();
}
