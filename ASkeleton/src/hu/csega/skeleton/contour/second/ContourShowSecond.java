package hu.csega.skeleton.contour.second;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ContourShowSecond extends JFrame {

	public static void main(String[] args) throws Exception {
		ContourShowSecond window = new ContourShowSecond();
		window.setVisible(true);
	}

	public ContourShowSecond() {
		super("Contour Show First");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ContourSecondCanvas canvas = new ContourSecondCanvas();
		canvas.setPreferredSize(new Dimension(1000, 600));
		getContentPane().add(canvas);

		pack();
	}

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

}
