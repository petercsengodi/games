package hu.csega.editors.common.dndview;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DragAndDropTestFrame extends JFrame {

	private DragAndDropView view;

	public DragAndDropTestFrame(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		view = new DragAndDropView();
		this.getContentPane().add(view);
		view.accept(this);

		this.addKeyListener(view);

		view.setPreferredSize(new Dimension(1200, 600));
		this.pack();
	}

	public DragAndDropView getView() {
		return view;
	}

	public static void main(String[] args) {
		BufferedImage image = DragAndDropImageLibrary.resolve("res/editors/rotation.png");
		System.out.println("Image loaded: " + (image != null));

		DragAndDropTestFrame parent = new DragAndDropTestFrame("Drag & Drop Test Frame");
		parent.setVisible(true);

		DragAndDropView view = parent.getView();
		view.addModelObject(new DADVTestLine());
		view.addModelObject(new DADVTestTriangle());
	}

	private static final long serialVersionUID = 1L;
}
