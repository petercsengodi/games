package hu.csega.alpoc.frame;

import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import hu.csega.alpoc.game.processes.DirectedProcessHandler;

public class AlpocFrame extends JFrame implements WindowListener {

	private static final long serialVersionUID = 1L;

	private AlpocCanvas canvas;
	private DirectedProcessHandler processHandler;
	private Thread processHandlerThread;
	
	public AlpocFrame() {
		super("A little piece of crap");
		
		canvas = new AlpocCanvas();
		getContentPane().add(canvas);
		
		this.addWindowListener(this);
	}
	
	public static void start() throws Exception {
		alpoc = new AlpocFrame();
		alpoc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		alpoc.pack();
		alpoc.setVisible(true);
		
		alpoc.processHandler = new DirectedProcessHandler();
		alpoc.processHandler.initialize();
		alpoc.processHandlerThread = new Thread() {
			public void run() {
				try {
					for(;;) {
						alpoc.processHandler.run();
						Thread.sleep(1);
					}
				} catch(InterruptedException ex) {
					// exiting the thread
				}
			}
		};
		
		alpoc.processHandlerThread.start();
	}
	
	public static void redrawWorld() {
		Graphics g = alpoc.canvas.getGraphics();
		alpoc.canvas.paint(g);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
 			try {
				processHandlerThread.join(100);
				Thread.yield();
			} catch (InterruptedException ex) {
			}
 			
 			try {
				Thread.sleep(900);
			} catch (InterruptedException ex) {
			}
 			
 			System.out.flush();
			processHandler.logMeasurement();
		} catch(RuntimeException ex) {
			// do not throw error, or the frame won't close
			ex.printStackTrace();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	private static AlpocFrame alpoc;
}
