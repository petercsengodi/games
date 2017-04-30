package hu.csega.games.adapters.swing;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import hu.csega.games.engine.GameCanvas;
import hu.csega.games.engine.GameEngine;
import hu.csega.games.engine.GameWindow;
import hu.csega.games.engine.GameWindowListener;
import hu.csega.games.engine.g2d.GameControlImpl;

public class SwingFrame extends JFrame implements GameWindow, WindowListener, KeyListener {

	private GameEngine engine;
	private GameControlImpl control;
	private List<GameWindowListener> listeners = new ArrayList<>();

	public SwingFrame(GameEngine engine) {
		super(engine.getDescriptor().getTitle());
		this.engine = engine;
		this.control = (GameControlImpl)this.engine.getControl();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(this);
		addKeyListener(this);
	}

	@Override
	public void register(GameWindowListener listener) {
		listeners.add(listener);
	}

	@Override
	public void add(GameCanvas canvas) {
		Component component = null;

		if(canvas instanceof SwingCanvas)
			component = ((SwingCanvas)canvas).getRealCanvas();

		if(component != null)
			getContentPane().add(component);
	}

	@Override
	public void showWindow() {
		pack();
		setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		for(GameWindowListener listener : listeners)
			listener.onFinishingWork();
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

	@Override
	public void closeWindow() {
	}

	private static final long serialVersionUID = 1L;
}
