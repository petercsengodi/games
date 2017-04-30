package hu.csega.brp.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BRPEditor extends JFrame {

	private JPanel board;
	private BRPCanvas canvas;

	public BRPEditor() {
		super("Pixel Editor");

		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());

		canvas = new BRPCanvas(this);
		contentPane.add(canvas, BorderLayout.WEST);

		board = new JPanel();
		board.setLayout(new FlowLayout());
		contentPane.add(board, BorderLayout.EAST);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}

	private static final long serialVersionUID = 1L;
}
