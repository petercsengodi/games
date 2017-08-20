package hu.csega.games.adapters.opengl;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import hu.csega.games.engine.impl.GameControlImpl;
import hu.csega.games.engine.impl.GameEngine;
import hu.csega.games.engine.intf.GameCanvas;
import hu.csega.games.engine.intf.GameWindow;
import hu.csega.games.engine.intf.GameWindowListener;

public class OpenGLFrame extends JFrame implements GameWindow, WindowListener, KeyListener,
		MouseListener, MouseMotionListener {

	private GameEngine engine;
	private GameControlImpl control;
	private List<GameWindowListener> listeners = new ArrayList<>();

	private boolean leftMouseButtonDown = false;
	private boolean rightMouseButtonDown = false;
	private int lastMouseX = 0;
	private int lastMouseY = 0;
	private boolean mouseInitialized = false;

	public OpenGLFrame(GameEngine engine) {
		super(engine.getDescriptor().getTitle());
		this.engine = engine;
		this.control = (GameControlImpl)this.engine.getControl();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(this);
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	@Override
	public void register(GameWindowListener listener) {
		listeners.add(listener);
	}

	@Override
	public void add(GameCanvas canvas) {
		Component component = null;

		if(canvas instanceof OpenGLCanvas)
			component = ((OpenGLCanvas)canvas).getRealCanvas();

		if(component != null) {
			getContentPane().add(component);
			component.addKeyListener(this);
		}
	}

	@Override
	public void setFullScreen(boolean fullScreen) {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	@Override
	public void showWindow() {
		pack();
		setVisible(true);
	}

	@Override
	public void closeWindow() {
		setVisible(false);
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
	public void repaintEverything() {
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		initMouseIfNecessary(e);

		int x = e.getXOnScreen();
		int y = e.getYOnScreen();
		int deltaX = x - lastMouseX;
		int deltaY = y - lastMouseY;

		control.moved(deltaX, deltaY, leftMouseButtonDown, rightMouseButtonDown);

		lastMouseX = x;
		lastMouseY = y;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		initMouseIfNecessary(e);

		int x = e.getXOnScreen();
		int y = e.getYOnScreen();
		int deltaX = x - lastMouseX;
		int deltaY = y - lastMouseY;

		control.moved(deltaX, deltaY, leftMouseButtonDown, rightMouseButtonDown);

		lastMouseX = x;
		lastMouseY = y;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		initMouseIfNecessary(e);

		int x = e.getX();
		int y = e.getY();

		if(e.getButton() == 1) {
			control.clicked(x, y, true, false);
		}

		if(e.getButton() == 3) {
			control.clicked(x, y, false, true);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		initMouseIfNecessary(e);

		int x = e.getX();
		int y = e.getY();

		if(e.getButton() == 1) {
			leftMouseButtonDown = true;
			control.pressed(x, y, true, false);
		}

		if(e.getButton() == 3) {
			rightMouseButtonDown = true;
			control.pressed(x, y, false, true);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		initMouseIfNecessary(e);

		int x = e.getX();
		int y = e.getY();

		if(e.getButton() == 1) {
			leftMouseButtonDown = false;
			control.released(x, y, true, false);
		}

		if(e.getButton() == 3) {
			rightMouseButtonDown = false;
			control.released(x, y, false, true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private void initMouseIfNecessary(MouseEvent e) {
		if(!mouseInitialized) {
			mouseInitialized = true;
			lastMouseX = e.getXOnScreen();
			lastMouseY = e.getYOnScreen();
		}
	}

	private static final long serialVersionUID = 1L;
}
