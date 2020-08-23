package hu.csega.pixel.editor.scene;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import hu.csega.pixel.PixelLibrary;

public class SceneEditor extends JFrame {

	public SceneEditor(String filename, int maximumNumberOfSheets) {
		super("Pixel Editor 32x32");

		this.maximumNumberOfSheets = maximumNumberOfSheets;
		this.filename = filename;

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		canvas = new SceneCanvas(this);
		contentPane.add(canvas, BorderLayout.NORTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}

	public void startEditor() {
		File f = new File(filename);
		if(f.exists()) {
			library = PixelLibrary.load(f);
		} else {
			library = new PixelLibrary(maximumNumberOfSheets);
		}

		setVisible(true);
	}

	public PixelLibrary getLibrary() {
		return library;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public List<SceneSprite> getSceneSprites() {
		return sceneSprites;
	}

	public void addSceneSprite(int index, int x, int y, int pixelSize) {
		SceneSprite sprite = new SceneSprite();
		sprite.setIndex(index);
		sprite.setX(x);
		sprite.setY(y);
		sprite.setPixelSize(pixelSize);
		sceneSprites.add(sprite);
	}

	public List<SceneText> getSceneTexts() {
		return sceneTexts;
	}

	public void addSceneText(int x, int y, Color color, String... text) {
		SceneText st = new SceneText();
		st.setX(x);
		st.setY(y);
		st.setColor(color);
		st.setText(text);
		sceneTexts.add(st);
	}

	private final String filename;
	private final int maximumNumberOfSheets;

	private SceneCanvas canvas;
	private PixelLibrary library;
	private Image backgroundImage;
	private List<SceneSprite> sceneSprites = new ArrayList<>();
	private List<SceneText> sceneTexts = new ArrayList<>();

	private static final long serialVersionUID = 1L;
}
