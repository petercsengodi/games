package hu.csega.games.adapters.opengl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import com.jogamp.opengl.awt.GLCanvas;

import hu.csega.games.engine.GameEngine;

public class OpenGLFrame extends JFrame implements WindowListener, KeyListener {

	public void start(GameEngine engine, GLCanvas canvas) {
		thread.setGamePhysics(engine.getPhysics());
		thread.setGLCanvas(canvas);
		thread.start();
	}

	public OpenGLControl getControl() {
		return openGLControl;
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
		openGLControl.hit(keyChar);

		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT)
			openGLControl.leftIsOn = true;
		if(keyCode == KeyEvent.VK_RIGHT)
			openGLControl.rightIsOn = true;
		if(keyCode == KeyEvent.VK_UP)
			openGLControl.upIsOn = true;
		if(keyCode == KeyEvent.VK_DOWN)
			openGLControl.downIsOn = true;
		if(keyCode == KeyEvent.VK_CONTROL)
			openGLControl.controlIsOn = true;
		if(keyCode == KeyEvent.VK_ALT)
			openGLControl.altIsOn = true;
		if(keyCode == KeyEvent.VK_SHIFT)
			openGLControl.shiftIsOn = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT)
			openGLControl.leftIsOn = false;
		if(keyCode == KeyEvent.VK_RIGHT)
			openGLControl.rightIsOn = false;
		if(keyCode == KeyEvent.VK_UP)
			openGLControl.upIsOn = false;
		if(keyCode == KeyEvent.VK_DOWN)
			openGLControl.downIsOn = false;
		if(keyCode == KeyEvent.VK_CONTROL)
			openGLControl.controlIsOn = false;
		if(keyCode == KeyEvent.VK_ALT)
			openGLControl.altIsOn = false;
		if(keyCode == KeyEvent.VK_SHIFT)
			openGLControl.shiftIsOn = false;
	}

	OpenGLFrame(String title) {
		super(title);
		addKeyListener(this);
		addWindowListener(this);
	}

	OpenGLControl openGLControl = new OpenGLControl();
	OpenGLThread thread = new OpenGLThread();

	private static final long serialVersionUID = 1L;
}
