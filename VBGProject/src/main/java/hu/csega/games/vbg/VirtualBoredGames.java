package hu.csega.games.vbg;

import hu.csega.games.vbg.main.VBGAllGames;
import hu.csega.games.vbg.swing.VBGCanvas;
import hu.csega.games.vbg.swing.VBGFrame;
import hu.csega.games.vbg.util.GeometricUtil;

public class VirtualBoredGames {

	private static VBGFrame frame;
	private static VBGCanvas canvas;

	public static VBGFrame getFrame() {
		return frame;
	}

	public static VBGCanvas getCanvas() {
		return canvas;
	}

	public static void main(String[] args) {
		frame = new VBGFrame();
		canvas = frame.getCanvas();
		canvas.setGame(VBGAllGames.SELECTOR);
		frame.setVisible(true);

		// I don't know, why, but it seems that because of the dynamic classloader
		// some classes may only be accessed if the main class references it
		GeometricUtil.distance(0, 0, 1, 1);
	}
}
