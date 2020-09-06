package hu.csega.editors.common.dndview;

import java.awt.Dimension;

import javax.swing.JFrame;

public class DragAndDropTestFrame extends JFrame {

	public DragAndDropTestFrame(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DragAndDropView view = new DragAndDropView();
		this.getContentPane().add(view);
		view.accept(this);

		view.setPreferredSize(new Dimension(1200, 600));
		this.pack();
	}

	public static void main(String[] args) {
		DragAndDropTestFrame parent = new DragAndDropTestFrame("Drag & Drop Test Frame");
		parent.setVisible(true);
	}

	private static final long serialVersionUID = 1L;
}
