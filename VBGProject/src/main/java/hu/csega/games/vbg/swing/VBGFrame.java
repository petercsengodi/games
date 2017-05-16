package hu.csega.games.vbg.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import hu.csega.games.vbg.main.VBGThread;

public class VBGFrame extends JFrame implements WindowListener {

	private VBGCanvas canvas;
	private VBGThread thread;

	private boolean programFinished = false;

	public static final int DEFAULT_WINDOW_WIDTH = 800;
	public static final int DEFAULT_WINDOW_HEIGHT = 600;

	public VBGFrame() {
		super("Játékok");

		thread = new VBGThread();

		canvas = new VBGCanvas(this);

		Container contentPane = this.getContentPane();
		contentPane.add(canvas);

		this.addKeyListener(canvas);

		this.addWindowListener(this);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
	}

	public VBGCanvas getCanvas() {
		return canvas;
	}

	public void startThread() {
		thread.start();
	}

	public boolean programFinished() {
		return programFinished;
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		programFinished = true;

		try {
			thread.join();
		} catch (InterruptedException ex) {
		}

		System.exit(0);
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

	private static final long serialVersionUID = 1L;
}
