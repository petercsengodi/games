package hu.csega.games.vbg.swing;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;

public class VBGFrame extends JFrame {

	private VBGCanvas canvas;

	public static final int DEFAULT_WINDOW_WIDTH = 800;
	public static final int DEFAULT_WINDOW_HEIGHT = 600;

	public VBGFrame() {
		super("Társasjátékok");

		canvas = new VBGCanvas(this);

		Container contentPane = this.getContentPane();
		contentPane.add(canvas);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
	}

	public VBGCanvas getCanvas() {
		return canvas;
	}

	private static final long serialVersionUID = 1L;
}
