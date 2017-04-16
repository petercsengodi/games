package hu.csega.games.adapters.opengl;

import java.awt.Component;
import java.awt.event.KeyEvent;
import com.jogamp.opengl.awt.GLCanvas;

import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameWindowListener;

public class OpenGLControlAttacher implements GameWindowListener {

	private OpenGLControl openGLControl = new OpenGLControl();
	private OpenGLThread thread = new OpenGLThread();

	public void start(GameEngine engine, Component keyTarget, GLCanvas canvas) {
		thread.setGamePhysics(engine.getPhysics());
		thread.setGLCanvas(canvas);
		thread.start();
	}

	@Override
	public void onFinishingWork() {
		thread.interrupt();
	}

	public OpenGLControl getControl() {
		return openGLControl;
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
}
