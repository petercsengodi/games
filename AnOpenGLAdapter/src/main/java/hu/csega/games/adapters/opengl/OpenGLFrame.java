package hu.csega.games.adapters.opengl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
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

	private boolean CENTER_MOUSE = true;

	private GameEngine engine;
	private GameControlImpl control;
	private List<GameWindowListener> listeners = new ArrayList<>();

	private boolean leftMouseButtonDown = false;
	private boolean rightMouseButtonDown = false;
	private int lastMouseX = 0;
	private int lastMouseY = 0;
	private boolean mouseInitialized = false;

	private Robot robot;
	private boolean centerMouse;

	public OpenGLFrame(GameEngine engine) {
		super(engine.getDescriptor().getTitle());
		this.engine = engine;
		this.control = (GameControlImpl)this.engine.getControl();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(this);
		this.addKeyListener(this);
		// this.addMouseListener(this);
		// this.addMouseMotionListener(this);
		CENTER_MOUSE = engine.getDescriptor().isMouseCentered();
	}

	@Override
	public void register(GameWindowListener listener) {
		listeners.add(listener);
	}

	@Override
	public void add(GameCanvas canvas, Container container) {
		Component component = null;

		if(canvas instanceof OpenGLCanvas)
			component = ((OpenGLCanvas)canvas).getRealCanvas();

		if(component != null) {
			container.add(component);
			component.addKeyListener(this);
			component.addMouseListener(this);
			component.addMouseMotionListener(this);
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
	public void closeApplication() {
		// setVisible(false);
		for(GameWindowListener listener : listeners)
			listener.onFinishingWork();
		System.exit(0);
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

		int x, y;

		if(centerMouse) {
			x = e.getXOnScreen();
			y = e.getYOnScreen();
		} else {
			x = e.getX();
			y = e.getY();
		}

		int deltaX = x - lastMouseX;
		int deltaY = y - lastMouseY;

		if(deltaX == 0 && deltaY == 0)
			return;

		control.moved(deltaX, deltaY, leftMouseButtonDown, rightMouseButtonDown);

		if(centerMouse) {
			putMouseToCenter();
		} else {
			lastMouseX = x;
			lastMouseY = y;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		initMouseIfNecessary(e);

		int x, y;

		if(centerMouse) {
			x = e.getXOnScreen();
			y = e.getYOnScreen();
		} else {
			x = e.getX();
			y = e.getY();
		}

		int deltaX = x - lastMouseX;
		int deltaY = y - lastMouseY;

		if(deltaX == 0 && deltaY == 0)
			return;

		control.moved(deltaX, deltaY, leftMouseButtonDown, rightMouseButtonDown);

		if(centerMouse) {
			putMouseToCenter();
		} else {
			lastMouseX = x;
			lastMouseY = y;
		}
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


			if(CENTER_MOUSE) {
				try {
					robot = new Robot();
					centerMouse = true;

					setCursor(getToolkit().createCustomCursor(
							new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
							"null"));

					putMouseToCenter();
				} catch(Exception ex) {
					ex.printStackTrace();
					centerMouse = false;
				}
			} else {
				centerMouse = false;
			}

			if(!centerMouse) {
				lastMouseX = e.getXOnScreen();
				lastMouseY = e.getYOnScreen();
			}
		}
	}

	private void putMouseToCenter() {
		lastMouseX = this.getLocationOnScreen().x + getWidth() / 2;
		lastMouseY = this.getLocationOnScreen().y + getHeight() / 2;
		robot.mouseMove(lastMouseX, lastMouseY);
	}

	private static final long serialVersionUID = 1L;
}
