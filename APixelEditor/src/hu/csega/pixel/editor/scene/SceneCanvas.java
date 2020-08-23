package hu.csega.pixel.editor.scene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.JPanel;

import hu.csega.pixel.Pixel;
import hu.csega.pixel.PixelLibrary;
import hu.csega.pixel.PixelSheet;

public class SceneCanvas extends JPanel {

	public static final Color BACKGROUND_COLOR = Color.DARK_GRAY;
	public static final int CANVAS_WIDTH = 1024;
	public static final int CANVAS_HEIGHT = 768;

	private SceneEditor sceneEditor;

	public SceneCanvas(SceneEditor pixelEditor) {
		this.sceneEditor = pixelEditor;
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
	}

	@Override
	public void paint(Graphics g) {
		// 1. Render black canvas
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// 2. Paint background image
		Image backgroundImage = sceneEditor.getBackgroundImage();
		g.drawImage(backgroundImage, 0, 0, null);

		// 3. Paint sprites
		PixelLibrary library = sceneEditor.getLibrary();
		for(SceneSprite sprite : sceneEditor.getSceneSprites()) {
			PixelSheet currentSheet = library.get(sprite.getIndex());
			int x = sprite.getX();
			int y = sprite.getY();
			int pixelSize = sprite.getPixelSize();

			for(int xx = 0; xx < PixelSheet.WIDTH; xx++) {
				for(int yy = 0; yy < PixelSheet.HEIGHT; yy++) {
					Pixel pixel = currentSheet.pixels[xx][yy];

					if(pixel.alpha >= 255) {
						Color color = pixel.convertToColor();
						g.setColor(color);

						g.fillRect(x + xx * pixelSize, y + yy * pixelSize, pixelSize, pixelSize);

					}

				}
			}
		}


		// 4. Paint texts
		g.setFont(new Font("Arial", Font.PLAIN, 12));

		List<SceneText> sceneTexts = sceneEditor.getSceneTexts();
		for(SceneText st : sceneTexts) {
			g.setColor(st.getColor());
			int x = st.getX();
			int y = st.getY();
			for(String t : st.getText()) {
				g.drawString(t, x, y);
				y += 15;
			}
		}
	}

	private static final long serialVersionUID = 1L;
}
