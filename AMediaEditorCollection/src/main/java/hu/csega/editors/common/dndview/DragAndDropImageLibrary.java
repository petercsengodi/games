package hu.csega.editors.common.dndview;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class DragAndDropImageLibrary {

	public static synchronized BufferedImage resolve(String filename) {
		if(filename == null || filename.isEmpty())
			return null;

		BufferedImage image = images.get(filename);
		if(image == null) {
			image = load(filename);
			images.put(filename, image);
		}

		return image;
	}

	private static BufferedImage load(String filename) {
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(filename));
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

		return bufferedImage;
	}

	private static Map<String, BufferedImage> images = new HashMap<>();
}
