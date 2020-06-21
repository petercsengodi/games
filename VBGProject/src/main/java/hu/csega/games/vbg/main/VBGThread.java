package hu.csega.games.vbg.main;

import hu.csega.games.vbg.VirtualBoredGames;
import hu.csega.games.vbg.swing.VBGCanvas;
import hu.csega.games.vbg.swing.VBGFrame;

public class VBGThread extends Thread {

	@Override
	public void run() {
		try {

			while(true) {

				VBGFrame frame = VirtualBoredGames.getFrame();
				if(frame.programFinished()) {
					System.out.println("Exiting thread.");
					return; // exiting program
				}

				long start = System.currentTimeMillis();

				VBGCanvas canvas = VirtualBoredGames.getCanvas();
				if(canvas.needsRepaint()) {
					canvas.repaint();
				}

				long end = System.currentTimeMillis();

				long wait = Math.max(1, 10 - (end - start));
				sleep(wait);

			}

		} catch(InterruptedException ex) {
			System.out.println("Thread interrupted.");
		}
	}

}
