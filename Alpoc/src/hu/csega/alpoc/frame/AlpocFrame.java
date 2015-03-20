package hu.csega.alpoc.frame;

import javax.swing.JFrame;

public class AlpocFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private AlpocCanvas canvas;
	
	public AlpocFrame() {
		super("A little piece of crap");
		
		canvas = new AlpocCanvas();
		getContentPane().add(canvas);
	}
	
	public static void main(String[] args) throws Exception {
		AlpocFrame alpoc = new AlpocFrame();
		alpoc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		alpoc.pack();
		alpoc.setVisible(true);
	}

}
