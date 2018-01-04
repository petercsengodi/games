package hu.csega.game.asteroid.engine;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import hu.csega.game.asteroid.AsteroidGameToolImpl;
import hu.csega.games.adapters.opengl.OpenGLCanvas;
import hu.csega.games.engine.impl.GameControlImpl;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.games.engine.intf.GameWindowListener;
import hu.csega.toolshed.framework.ToolWindow;

public class AsteroidGameWindowWrapper implements GameWindow, KeyListener {

	private ToolWindow toolWindow;
	private AsteroidGameToolImpl gameTool;
	private GameControlImpl control; // FIXME this is not nice

	public AsteroidGameWindowWrapper(ToolWindow toolWindow, AsteroidGameToolImpl gameTool, GameControlImpl control) {
		this.toolWindow = toolWindow;
		this.gameTool = gameTool;
		this.control = control;
		this.toolWindow.getAwtWindow().addKeyListener(this);
	}

	@Override
	public void register(GameWindowListener listener) {
		gameTool.registerGameWindowListener(listener);
	}

	@Override
	public void add(GameCanvas canvas) {
		Component component = null;

		if(canvas instanceof OpenGLCanvas)
			component = ((OpenGLCanvas)canvas).getRealCanvas();

		if(component != null) {
			toolWindow.addComponent(component);
			component.addKeyListener(this);
		}
	}

	@Override
	public void setFullScreen(boolean fullScreen) {
		this.toolWindow.setFullScreen(fullScreen);
	}

	@Override
	public void repaintEverything() {
	}

	@Override
	public void showWindow() {
	}

	@Override
	public void closeWindow() {
		toolWindow.getAwtWindow().setVisible(false);
	}

	@Override
	public void closeApplication() {
		toolWindow.getAwtWindow().setVisible(false);
		System.exit(0);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char keyChar = e.getKeyChar();
		control.hit(keyChar);

		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT)
			control.setLeftIsOn(true);
		if(keyCode == KeyEvent.VK_RIGHT)
			control.setRightIsOn(true);
		if(keyCode == KeyEvent.VK_UP)
			control.setUpIsOn(true);
		if(keyCode == KeyEvent.VK_DOWN)
			control.setDownIsOn(true);
		if(keyCode == KeyEvent.VK_CONTROL)
			control.setControlIsOn(true);
		if(keyCode == KeyEvent.VK_ALT)
			control.setAltIsOn(true);
		if(keyCode == KeyEvent.VK_SHIFT)
			control.setShiftIsOn(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT)
			control.setLeftIsOn(false);
		if(keyCode == KeyEvent.VK_RIGHT)
			control.setRightIsOn(false);
		if(keyCode == KeyEvent.VK_UP)
			control.setUpIsOn(false);
		if(keyCode == KeyEvent.VK_DOWN)
			control.setDownIsOn(false);
		if(keyCode == KeyEvent.VK_CONTROL)
			control.setControlIsOn(false);
		if(keyCode == KeyEvent.VK_ALT)
			control.setAltIsOn(false);
		if(keyCode == KeyEvent.VK_SHIFT)
			control.setShiftIsOn(false);
	}

}
