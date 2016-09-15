package hu.csega.skeleton.contour.third;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ContourShowThird extends JFrame {

	public static void main(String[] args) throws Exception {
		ContourShowThird window = new ContourShowThird();
		window.setVisible(true);
	}

	public ContourShowThird() {
		super("Contour Show First");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ContourThirdCanvas canvas = new ContourThirdCanvas();
		canvas.setPreferredSize(new Dimension(1000, 600));
		getContentPane().add(canvas);

		pack();
	}

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

}
