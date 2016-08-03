package hu.csega.skeleton.show;

import java.awt.Dimension;

import javax.swing.JFrame;

import hu.csega.skeleton.body.SkeletonBodyParts;
import hu.csega.skeleton.parts.UsedPart;

public class ShowSkeleton extends JFrame {

	public static void main(String[] args) throws Exception {
		ShowSkeleton window = new ShowSkeleton();
		window.setVisible(true);
	}

	public ShowSkeleton() {
		super("Show Skeleton");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		UsedPart skeleton = SkeletonBodyParts.createNewSkeleton();
		SkeletonCanvas canvas = new SkeletonCanvas(skeleton);
		canvas.setPreferredSize(new Dimension(1000, 600));
		getContentPane().add(canvas);

		pack();
	}

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

}
