package hu.csega.skeleton.contour.first;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ContourShowFirst extends JFrame {

	public static void main(String[] args) throws Exception {
		ContourShowFirst window = new ContourShowFirst();
		window.setVisible(true);
	}

	public ContourShowFirst() {
		super("Contour Show First");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ContourFirstCanvas canvas = new ContourFirstCanvas();
		canvas.setPreferredSize(new Dimension(1000, 600));
		getContentPane().add(canvas);

		pack();
	}

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

}
