package hu.csega.editors.anm.layer1.swing.components.rotator;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class AnimatorRotatorTestFrame extends JFrame {

	public AnimatorRotatorTestFrame(String title) {
		super(title);
	}

	public static void main(String[] args) {
		rotator = new AnimatorRotatorComponent();

		AnimatorRotatorTestFrame frame = new AnimatorRotatorTestFrame("Testing The Rotator Component");
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new GridLayout(1,  1));
		contentPane.add(rotator);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static AnimatorRotatorComponent rotator;

	private static final long serialVersionUID = 1L;
}
