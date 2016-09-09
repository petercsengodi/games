package hu.csega.game.adapters.swing;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import hu.csega.game.engine.GameEngine;


public class SwingFrame extends JFrame implements WindowListener, KeyListener {

	public void start(GameEngine engine, SwingCanvas canvas) {
		thread.setGamePhysics(engine.getPhysics());
		thread.setSwingCanvas(canvas);
		thread.start();
	}

	public SwingControl getControl() {
		return swingControl;
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		thread.interrupt();
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

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char keyChar = e.getKeyChar();
		swingControl.hit(keyChar);

		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT)
			swingControl.leftIsOn = true;
		if(keyCode == KeyEvent.VK_RIGHT)
			swingControl.rightIsOn = true;
		if(keyCode == KeyEvent.VK_UP)
			swingControl.upIsOn = true;
		if(keyCode == KeyEvent.VK_DOWN)
			swingControl.downIsOn = true;
		if(keyCode == KeyEvent.VK_CONTROL)
			swingControl.controlIsOn = true;
		if(keyCode == KeyEvent.VK_ALT)
			swingControl.altIsOn = true;
		if(keyCode == KeyEvent.VK_SHIFT)
			swingControl.shiftIsOn = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT)
			swingControl.leftIsOn = false;
		if(keyCode == KeyEvent.VK_RIGHT)
			swingControl.rightIsOn = false;
		if(keyCode == KeyEvent.VK_UP)
			swingControl.upIsOn = false;
		if(keyCode == KeyEvent.VK_DOWN)
			swingControl.downIsOn = false;
		if(keyCode == KeyEvent.VK_CONTROL)
			swingControl.controlIsOn = false;
		if(keyCode == KeyEvent.VK_ALT)
			swingControl.altIsOn = false;
		if(keyCode == KeyEvent.VK_SHIFT)
			swingControl.shiftIsOn = false;
	}

	SwingFrame(String title) {
		super(title);
		addKeyListener(this);
		addWindowListener(this);
	}

	SwingControl swingControl = new SwingControl();
	SwingThread thread = new SwingThread();

	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
}
