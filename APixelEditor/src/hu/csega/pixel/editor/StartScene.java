package hu.csega.pixel.editor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import hu.csega.pixel.editor.scene.SceneEditor;

public class StartScene {

	public static final String PIX_FILE = "tmp.p32";
	public static final int MAXIMUM_NUMBER_OF_SHEETS = 100;

	public static void main(String[] args) throws Exception {

		// 1. Open canvas
		SceneEditor editor = new SceneEditor(PIX_FILE, MAXIMUM_NUMBER_OF_SHEETS);

		// 2. Load background image
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("graphics/backgrounds/background1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		editor.setBackgroundImage(img);

		// 3. Add sprites
		editor.addSceneSprite(1, 510, 350, 2);
		editor.addSceneSprite(3, 575, 350, 2);

		// 4. Text
		editor.addSceneText(20, 20, Color.white, "Hello World!", "This is a test.");

		// 5. Show me what you've got
		editor.startEditor();
	}

}
